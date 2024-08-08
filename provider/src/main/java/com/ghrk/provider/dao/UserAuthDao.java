package com.ghrk.provider.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ghrk.common.dto.UserAuthDto;
import com.ghrk.common.entity.UserAuthEntity;
import com.ghrk.common.form.DeleteAuthForm;
import com.ghrk.common.form.UpdateAuthForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 
 * @email ${email}
 * @date 2024-07-12 16:37:18
 */
@Mapper
public interface UserAuthDao extends BaseMapper<UserAuthEntity> {

    IPage<UserAuthDto> getUserAuthPage(IPage<UserAuthDto> page, @Param("userId") String userId, @Param("authId") Integer authId);


    // 获取用户ID ，用户姓名， 用户权限ID，权限更新时间
    List<UserAuthDto> getUserAuth(@Param("userId") String userId);

    void deleteAuth(DeleteAuthForm deleteAuthForm);

    void updateAuth(UpdateAuthForm updateAuthForm);

    List<Integer> getUserAuthList(@Param("userId") String userId);

    UserAuthDto getUserIdAndAuthIdAndStatus(@Param("userId") String userId, @Param("authId") Integer authId);

    void updateAuthStatus(@Param("userId") String userId, @Param("authId") Integer authId, @Param("status") Integer status, @Param("operatorId") String operatorId);

    void saveAuth(@Param("userId") String userId, @Param("authId") Integer authId, @Param("operatorId") String operatorId);
}
