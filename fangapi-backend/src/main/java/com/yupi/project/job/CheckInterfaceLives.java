package com.yupi.project.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fang.fangapicommon.model.entity.InterfaceInfo;
import com.fangapi.fangapiclientsdk.Client.fangAiClient;
import com.fangapi.fangapiclientsdk.Client.fangApiClient;
import com.google.gson.Gson;
import com.yupi.project.model.enums.InterfaceInfoIdEnum;
import com.yupi.project.model.vo.LoveStoryVO;
import com.yupi.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.annotations.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 定时检查问题接口
 * 动态上下线
 */
@Component
@Slf4j
public class CheckInterfaceLives {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private fangApiClient fangApiClient;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Scheduled(cron = "0/45 * * * * ? ")
    public void CheckInterfaceInfoLives() {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        // 检查上线状态的接口是否有问题
        queryWrapper.eq("status", 1);
        List<InterfaceInfo> PreCheckOnlineInterfaceInfoList = interfaceInfoService.list(queryWrapper);
        // 如果所有接口都是下线的，那么就不执行任何逻辑
        if(CollectionUtils.isEmpty(PreCheckOnlineInterfaceInfoList)) {
            log.info("所有接口都处于下线状态");
            return;
        }
        List<Long> PreCheckOnlineInterfaceInfoIdList = PreCheckOnlineInterfaceInfoList.stream()
                .map((InterfaceInfo::getId)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(PreCheckOnlineInterfaceInfoIdList)) {
            log.info("获取接口 id 错误");
            return;
        }
        // 一一调用接口查看情况
        for (Long interfaceInfoId : PreCheckOnlineInterfaceInfoIdList) {
            // 异步化的方式来检查
            CompletableFuture.runAsync(() -> {
                boolean b = doCheck(interfaceInfoId);
                // 如果接口实际无法调用，就将其下线
                if (!b) {
                    // 将相同 id 的接口下线
                    UpdateWrapper<InterfaceInfo> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", interfaceInfoId);
                    updateWrapper.set("status", 0);
                    interfaceInfoService.update(updateWrapper);
                }
            }, threadPoolExecutor);
        }
    }

    /**
     * 接口上下线检查
     * todo 不要直接使用客户端 SDK ，而是要绕过网关直接调用请求实现
     * @param interfaceInfoId
     * @return
     */
    public boolean doCheck(Long interfaceInfoId) {
        if(interfaceInfoId.equals(InterfaceInfoIdEnum.LOVE_STORY_ID.getId())) {
            String randomLoveStory = fangApiClient.getRandomLoveStory();
            if(randomLoveStory.contains("content")) {
                return true;
            }else {
                // todo 通过消息队列实现重试
                for(int i = 0; i < 3; i++) {
                    log.info("第 {} 次重试", i);
                    randomLoveStory = fangApiClient.getRandomLoveStory();
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(randomLoveStory.contains("content")) {
                        return true;
                    }
                }
            }
        }else {
            // 如果是 ai 接口，就先返回 true
            return true;
        }
        // 如果多次尝试失败，就返回 false
        return false;
    }
}
