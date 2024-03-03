package com.yupi.project.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.fangapicommon.model.dto.ChartQueryRequest;
import com.fang.fangapicommon.model.entity.Chart;
import com.fang.fangapicommon.model.entity.User;
import com.fang.fangapicommon.model.vo.ChartVO;
import com.fang.fangapicommon.service.InnerListAiProductionService;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.constant.UserConstant;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/AiProduction")
public class ListAiProductionController {

    @Resource
    private UserService userService;

    @DubboReference
    private InnerListAiProductionService innerListAiProductionService;
    /**
     * 不走网关，直接获取我生成的图表
     * 获取我生成的图表，直接调用内部方法
     * @param request
     * @return
     */
    @PostMapping("/list/myChart")
    public BaseResponse<Page<ChartVO>> listMyGenChartByPage(@RequestBody ChartQueryRequest chartQueryRequest, HttpServletRequest request) {
        if(chartQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"查询条件不应为空");
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long userId = loginUser.getId();
        Page<ChartVO> chartPage = innerListAiProductionService.listMyChartByPage(userId, chartQueryRequest);
        return ResultUtils.success(chartPage);
    }
}
