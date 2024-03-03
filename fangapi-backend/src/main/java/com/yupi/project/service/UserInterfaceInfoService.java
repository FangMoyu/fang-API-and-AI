package com.yupi.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.fangapicommon.model.entity.UserInterfaceInfo;

/**
 *
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

    /**
     * 用户调用接口次数统计方法
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean invokeCount(long userId, long interfaceInfoId);
}
