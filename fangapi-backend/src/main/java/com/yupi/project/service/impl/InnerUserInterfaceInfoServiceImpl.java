package com.yupi.project.service.impl;

import com.fang.fangapicommon.service.InnerUserInterfaceInfoService;
import com.yupi.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 内部方法，提供给调用者使用
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 直接调用现成的方法去统计次数
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    @Override
    public boolean invokeCount(long userId, long interfaceInfoId) {
       return userInterfaceInfoService.invokeCount(userId,interfaceInfoId);
    }
}
