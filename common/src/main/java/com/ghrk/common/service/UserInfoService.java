package com.ghrk.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ghrk.common.entity.UserInfoEntity;
import com.ghrk.common.utils.PageUtils;

import java.util.Map;

/**
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserInfoEntity getUserByUserId(String userId);

    void testException();

    UserInfoEntity getByUserId(String userId);

}

