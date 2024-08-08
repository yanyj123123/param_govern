package com.ghrk.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghrk.common.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author 
 * @email ${email}
 * @date 2024-07-12 16:37:18
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {

    UserInfoEntity getUserByUserId(String userId);
}
