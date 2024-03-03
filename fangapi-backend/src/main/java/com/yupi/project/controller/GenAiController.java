package com.yupi.project.controller;

import cn.hutool.core.util.ObjectUtil;
import com.fang.fangapicommon.model.entity.InterfaceInfo;
import com.fang.fangapicommon.model.entity.User;
import com.fangapi.fangapiclientsdk.Client.fangAiClient;
import com.fangapi.fangapiclientsdk.Model.dto.chart.GenChartByAiRequest;
import com.fangapi.fangapiclientsdk.Model.dto.copywriting.GenCopyWritingRequest;
import com.yupi.project.Utils.InterfaceInfoUtils;
import com.yupi.project.Utils.MultipartFileToFile;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/GenAi")
@Slf4j
public class GenAiController {

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private InterfaceInfoUtils interfaceInfoUtils;

    private static final int GEN_CHART_INTERFACE_INFO_ID = 45;
    private static final int GEN_COPY_WRITING_INTERFACE_INFO_ID = 46;
    /**
     * ai 图表生成
     * @param genChartByAiRequest
     * @param request
     * @return
     */
    @PostMapping("/invokeAi")
    public BaseResponse<String> invokeAiGenChartInterface(
            @RequestPart("file") MultipartFile multipartFile, GenChartByAiRequest genChartByAiRequest, HttpServletRequest request) throws Exception {
        // 参数校验
        if (genChartByAiRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = genChartByAiRequest.getName();
        if(StringUtils.isBlank(name)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图表名称不能为空");
        }
        String chartType = genChartByAiRequest.getChartType();
        if(StringUtils.isBlank(chartType)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图表类型不能为空");
        }
        String goal = genChartByAiRequest.getGoal();
        if(StringUtils.isBlank(goal)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"生成目标不能为空");
        }
        interfaceInfoUtils.doCheckInterfaceInfo(GEN_CHART_INTERFACE_INFO_ID);
        // 获取当前登录用户的ak和sk，这样相当于用户自己的这个身份去调用，
        // 也不会担心它刷接口，因为知道是谁刷了这个接口，会比较安全
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        // 设置 userId
        genChartByAiRequest.setUserId(loginUser.getId());
//         调用第三方接口方法，传入对象，获取用户名
        fangAiClient fangAiClient = new fangAiClient(accessKey, secretKey);
        // 将 multipartFile 转成 File 后发送
        File file = MultipartFileToFile.multipartFileToFile(multipartFile);
        String genChartResult = fangAiClient.UsingBIGenChart(file, genChartByAiRequest);
        // 返回常规相应，并包含调用结果
        return ResultUtils.success(genChartResult);
    }

    @PostMapping("/invoke/copy/writing")
    public BaseResponse<String> invokeAiGenCopyWriting(String userInput ,HttpServletRequest request) throws Exception {


        if(StringUtils.isBlank(userInput)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "输入不能为空");
        }
        // 检查接口是否上线
        interfaceInfoUtils.doCheckInterfaceInfo(GEN_COPY_WRITING_INTERFACE_INFO_ID);
        // 获取当前登录用户的ak和sk，这样相当于用户自己的这个身份去调用，
        // 也不会担心它刷接口，因为知道是谁刷了这个接口，会比较安全
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        // 设置 userId
//         调用第三方接口方法，传入对象，获取用户名
        fangAiClient fangAiClient = new fangAiClient(accessKey, secretKey);
        String genCopyWritingByAi = fangAiClient.GenCopyWritingByAi(new GenCopyWritingRequest(loginUser.getId(), userInput));
        // 返回常规相应，并包含调用结果
        return ResultUtils.success(genCopyWritingByAi);
    }


}
