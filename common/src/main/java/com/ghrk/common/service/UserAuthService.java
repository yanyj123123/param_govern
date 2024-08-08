package com.ghrk.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ghrk.common.dto.UserAuthDto;
import com.ghrk.common.entity.UserAuthEntity;
import com.ghrk.common.form.AddAuthForm;
import com.ghrk.common.form.DeleteAuthForm;
import com.ghrk.common.form.UpdateAuthForm;
import com.ghrk.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 用户权限服务
 */
public interface UserAuthService extends IService<UserAuthEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> getUserAuthList(String userId);

    List<UserAuthDto> getUserAuth(String userId);

    void deleteAuth(DeleteAuthForm deleteAuthForm);

    void updateAuth(UpdateAuthForm updateAuthForm);

    UserAuthDto getUserIdAndAuthIdAndStatus(String userId, Integer authId);

    void updateExistAuth(UpdateAuthForm updateAuthForm);

    void addAuth(AddAuthForm addAuthForm);

}

