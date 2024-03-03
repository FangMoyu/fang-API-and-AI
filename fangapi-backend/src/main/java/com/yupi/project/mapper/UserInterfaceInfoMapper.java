package com.yupi.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.fangapicommon.model.entity.InterfaceInfo;
import com.fang.fangapicommon.model.entity.User;
import com.fang.fangapicommon.model.entity.UserInterfaceInfo;


import java.util.List;

/**
 * @Entity com.yupi.project.model.entity.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




