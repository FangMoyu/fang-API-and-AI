package com.yupi.project.controller;

import cn.hutool.core.stream.CollectorUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.fangapicommon.model.entity.InterfaceInfo;
import com.fang.fangapicommon.model.entity.User;
import com.fang.fangapicommon.model.entity.UserInterfaceInfo;
import com.fangapi.fangapiclientsdk.Client.fangApiClient;
import com.google.gson.Gson;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.DeleteRequest;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.constant.CommonConstant;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.model.dto.interfaceInfo.*;
import com.yupi.project.model.enums.InterfaceInfoStatusEnum;
import com.yupi.project.model.vo.InterfaceInfoVO;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;
    /**
     * 将调用次数前三的接口返回，用于图标展示
     * @return
     */
    @GetMapping("/listTopInvokeInterfaceInfo")
    public BaseResponse<List<InterfaceInfoVO>> listTopInterfaceInfo(int limit){
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
        // 将接口信息按照 id 分组，便于关联查询
        Map<Long, List<UserInterfaceInfo>> interfaceInfoObjMap = userInterfaceInfoList
                .stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        // 设置查询条件，根据前三的 id 去查询接口信息数据用来作为返回值
        queryWrapper.in("id", interfaceInfoObjMap.keySet());
        // 根据 id 查询接口数据库
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        // 校验查询结果是否为空
        if(CollectionUtils.isEmpty(list)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 构建接口信息 VO 列表，使用流式处理将接口信息映射到接口信息 VO 对象，并加入列表中
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            // 创建 VO 对象，用于赋值
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            // 将属性赋值给 VO 对象
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            // 将 TotalNum 赋值给 VO 对象
            List<UserInterfaceInfo> userInterfaceInfos = interfaceInfoObjMap.get(interfaceInfoVO.getId());
            interfaceInfoVO.setTotalNum(userInterfaceInfos.get(0).getTotalNum());
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOList);
    }
}
