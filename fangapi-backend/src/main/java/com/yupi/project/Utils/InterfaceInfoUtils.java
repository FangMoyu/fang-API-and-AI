package com.yupi.project.Utils;

import cn.hutool.core.util.ObjectUtil;
import com.fang.fangapicommon.model.entity.InterfaceInfo;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.service.InterfaceInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InterfaceInfoUtils {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    public void doCheckInterfaceInfo(Integer InterfaceInfoId) {
        if(InterfaceInfoId == null || InterfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口 Id 异常");
        }
        // 检查接口是否上线
        InterfaceInfo ChartInterfaceInfo = interfaceInfoService.getById(InterfaceInfoId);
        if(ObjectUtil.hasNull(ChartInterfaceInfo) || ChartInterfaceInfo.getStatus() == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "该接口未上线");
        }
    }
}
