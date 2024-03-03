package com.yupi.project.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.fangapicommon.model.entity.User;
import com.fangapi.fangapiclientsdk.Client.fangApiClient;
import com.google.gson.Gson;
import com.yupi.project.Utils.InterfaceInfoUtils;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.DeleteRequest;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.constant.CommonConstant;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.model.dto.loveStory.LoveStoryAddRequest;
import com.yupi.project.model.dto.loveStory.LoveStoryQueryRequest;
import com.yupi.project.model.dto.loveStory.LoveStoryUpdateRequest;
import com.yupi.project.model.entity.LoveStory;
import com.yupi.project.model.vo.LoveStoryVO;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.LoveStoryService;
import com.yupi.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/love/story")
public class LoveStoryController {
    @Resource
    private LoveStoryService loveStoryService;

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoUtils interfaceInfoUtils;

    private static final int LOVE_STORY_INTERFACE_INFO_ID = 47;
    /**
     * 随机生成土味情话
     * @param request
     * @return
     */
    @GetMapping("/random/gen")
    public BaseResponse<LoveStoryVO> randomGenLoveStory(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        // 判断是否登录
        if(ObjectUtil.hasNull(loginUser)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 判断接口是否上线
        interfaceInfoUtils.doCheckInterfaceInfo(LOVE_STORY_INTERFACE_INFO_ID);
        // 如果已经登录，直接调用接口方法
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        // 使用客户端 SDK ，发送请求调用接口
        fangApiClient fangApiClient = new fangApiClient(accessKey, secretKey);
        String randomLoveStory = fangApiClient.getRandomLoveStory();
        Gson gson = new Gson();
        LoveStoryVO loveStoryVO = gson.fromJson(randomLoveStory, LoveStoryVO.class);
        return ResultUtils.success(loveStoryVO);
    }


    /**
     * 创建
     *
     * @param loveStoryAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Long> addLoveStory(@RequestBody LoveStoryAddRequest loveStoryAddRequest, HttpServletRequest request) {
        if (loveStoryAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoveStory loveStory = new LoveStory();
        BeanUtils.copyProperties(loveStoryAddRequest, loveStory);
        // 校验
        User loginUser = userService.getLoginUser(request);
        boolean result = loveStoryService.save(loveStory);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newLoveStoryId = loveStory.getId();
        return ResultUtils.success(newLoveStoryId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> deleteLoveStory(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        LoveStory oldLoveStory = loveStoryService.getById(id);
        if (oldLoveStory == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean b = loveStoryService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param loveStoryUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> updateLoveStory(@RequestBody LoveStoryUpdateRequest loveStoryUpdateRequest,
                                            HttpServletRequest request) {
        if (loveStoryUpdateRequest == null || loveStoryUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoveStory loveStory = new LoveStory();
        BeanUtils.copyProperties(loveStoryUpdateRequest, loveStory);
        // 参数校验

        User user = userService.getLoginUser(request);
        long id = loveStoryUpdateRequest.getId();
        // 判断是否存在
        LoveStory oldLoveStory = loveStoryService.getById(id);
        if (oldLoveStory == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        boolean result = loveStoryService.updateById(loveStory);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<LoveStory> getLoveStoryById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoveStory loveStory = loveStoryService.getById(id);
        return ResultUtils.success(loveStory);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param loveStoryQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<LoveStory>> listLoveStory(LoveStoryQueryRequest loveStoryQueryRequest) {
        LoveStory loveStoryQuery = new LoveStory();
        if (loveStoryQueryRequest != null) {
            BeanUtils.copyProperties(loveStoryQueryRequest, loveStoryQuery);
        }
        QueryWrapper<LoveStory> queryWrapper = new QueryWrapper<>(loveStoryQuery);
        List<LoveStory> loveStoryList = loveStoryService.list(queryWrapper);
        return ResultUtils.success(loveStoryList);
    }

    /**
     * 分页获取列表
     *
     * @param loveStoryQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Page<LoveStory>> listLoveStoryByPage(LoveStoryQueryRequest loveStoryQueryRequest, HttpServletRequest request) {
        if (loveStoryQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoveStory loveStoryQuery = new LoveStory();
        BeanUtils.copyProperties(loveStoryQueryRequest, loveStoryQuery);
        long current = loveStoryQueryRequest.getCurrent();
        long size = loveStoryQueryRequest.getPageSize();
        String sortField = loveStoryQueryRequest.getSortField();
        String sortOrder = loveStoryQueryRequest.getSortOrder();
        String content = loveStoryQuery.getContent();
        // content 需支持模糊搜索
        loveStoryQuery.setContent(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<LoveStory> queryWrapper = new QueryWrapper<>(loveStoryQuery);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<LoveStory> loveStoryPage = loveStoryService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(loveStoryPage);
    }

    // endregion


}
