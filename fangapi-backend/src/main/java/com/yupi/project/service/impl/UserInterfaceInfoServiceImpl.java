package com.yupi.project.service.impl;
import java.util.Date;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.fangapicommon.model.entity.User;
import com.fang.fangapicommon.model.entity.UserInterfaceInfo;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        // 校验剩余调用次数是否大于0
        if(userInterfaceInfo.getLeftNum() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"剩余次数不能小于 0");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean invokeCount(long userId, long interfaceInfoId) {
        // 校验参数
        if(userId <= 0 || interfaceInfoId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 先查询一下接口，看一下当前用户是否有调用该接口数据，若没有，则直接创建
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        // 如果没有，则创建用户接口信息，然后再去更新
        if(ObjectUtil.isNull(userInterfaceInfo)) {
            UserInterfaceInfo createUserInterfaceInfo = new UserInterfaceInfo();
            createUserInterfaceInfo.setUserId(userId);
            createUserInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
            createUserInterfaceInfo.setTotalNum(0);
            createUserInterfaceInfo.setLeftNum(20);
            createUserInterfaceInfo.setStatus(1);
            this.save(createUserInterfaceInfo);
        }

        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        //匹配两个条件的字段进行更新
        updateWrapper.eq("userId",userId);
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        // todo 加一操作得先校验一下这个用户接口信息是否存在字段，若数据库不存在该行数据，则应创建新的数据，而不是直接更新
        // todo 加锁，防止出现事务安全问题
        // 校验剩余调用次数是否大于0
        userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        if(userInterfaceInfo.getLeftNum() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"剩余次数不能小于 0");
        }
        // setSql 方法用于设置要更新的 SQL 语句。这里通过 SQL 表达式实现了两个字段的更新操作：
        // leftNum=leftNum-1和totalNum=totalNum+1。意思是将leftNum字段减一，totalNum字段加一。
        updateWrapper.setSql("leftNum = leftNum -1, totalNum = totalNum + 1");
        // 最后，调用update方法执行更新操作，并返回更新是否成功的结果
        return this.update(updateWrapper);
    }
}




