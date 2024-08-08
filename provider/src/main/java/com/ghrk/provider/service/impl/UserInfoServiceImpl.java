package com.ghrk.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghrk.common.entity.UserInfoEntity;
import com.ghrk.common.exception.GlobalException;
import com.ghrk.common.service.UserInfoService;
import com.ghrk.common.utils.Constant;
import com.ghrk.common.utils.PageUtils;
import com.ghrk.common.utils.Query;
import com.ghrk.provider.dao.UserInfoDao;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@DubboService
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<UserInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserInfoEntity.class, user -> !"password".equals(user.getProperty()));
        wrapper.likeRight(params.get(Constant.USER_ID) != null && !Objects.equals(params.get(Constant.USER_ID), ""), UserInfoEntity::getUserId, params.get(Constant.USER_ID));
        wrapper.eq(params.get(Constant.CITY) != null && !Objects.equals(params.get(Constant.CITY), ""), UserInfoEntity::getCity, params.get(Constant.CITY));
        IPage<UserInfoEntity> page = this.page(
                new Query<UserInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public UserInfoEntity getUserByUserId(String userId) {
        return userInfoDao.getUserByUserId(userId);
    }

    @Override
    public void testException() {
        throw new GlobalException("test GlobalException");
    }

    @Override
    public UserInfoEntity getByUserId(String userId) {
        LambdaQueryWrapper<UserInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserInfoEntity.class, user -> !"password".equals(user.getProperty()));
        wrapper.eq(UserInfoEntity::getUserId, userId);
        return this.getOne(wrapper);
    }


}