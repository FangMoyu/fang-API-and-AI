package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fang.fangapicommon.model.entity.User;
import com.fang.fangapicommon.service.InnerUserService;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 内部方法，提供给调用者使用
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        // 校验
        if(StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 根据 accessKey 去数据库查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey" , accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
