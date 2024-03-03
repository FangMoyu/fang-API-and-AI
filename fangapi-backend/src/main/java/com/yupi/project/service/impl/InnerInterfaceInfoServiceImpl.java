package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fang.fangapicommon.model.entity.InterfaceInfo;
import com.fang.fangapicommon.service.InnerInterfaceInfoService;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 内部方法，通过 Dubbo 实现方法调用
 *
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 从数据库中查询模拟接口是否存在、以及请求方法是否匹配（还可以校验请求参数）
     * @param url
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        // 校验参数
        if(StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
