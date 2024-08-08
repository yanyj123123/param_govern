package com.ghrk.provider.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghrk.common.dto.MenuAuth;
import com.ghrk.common.dto.UserAuthDto;
import com.ghrk.common.entity.UserAuthEntity;
import com.ghrk.common.form.AddAuthForm;
import com.ghrk.common.form.DeleteAuthForm;
import com.ghrk.common.form.UpdateAuthForm;
import com.ghrk.common.service.ApolloService;
import com.ghrk.common.service.UserAuthService;
import com.ghrk.common.utils.Constant;
import com.ghrk.common.utils.PageUtils;
import com.ghrk.common.utils.Query;
import com.ghrk.provider.dao.UserAuthDao;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Component
@DubboService
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuthEntity> implements UserAuthService {

    @Autowired
    private UserAuthDao userAuthDao;

    @DubboReference
    private ApolloService apolloService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // userId和authId是可选参数；若传了该参数，则判断参数是否为空
        IPage<UserAuthDto> page = userAuthDao.getUserAuthPage(new Query<UserAuthDto>().getPage(params),
                (params.get(Constant.USER_ID) == null || Objects.equals(params.get(Constant.USER_ID), "")) ? null : (String) params.get(Constant.USER_ID),
                params.get(Constant.AUTHID) == null || Objects.equals(params.get(Constant.AUTHID), "") ? null : Integer.parseInt((String) params.get(Constant.AUTHID)));

        return new PageUtils(page);
    }

    @Override
    public List<String> getUserAuthList(String userId) {
        // 从数据库中获取该用户的所有权限ID列表
        List<Integer> userAuthList = userAuthDao.getUserAuthList(userId);
        // 从apollo中获取 菜单权限映射
        String apolloMenuAuth = apolloService.getMenuAuth();
        //Object parse = JSON.parse(permission);
        List<MenuAuth> menuAuths = JSON.parseArray(apolloMenuAuth, MenuAuth.class);
        List<String> menu = new ArrayList<>();
        for (MenuAuth ma : menuAuths) {
            // 把权限映射 中的 权限 转为 List
            if(ma.getAuth() == null || ma.getAuth().equals("")) {
                continue;
            }
            List<Integer> authList = Arrays.stream(ma.getAuth().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            // 判断用户权限  是否在 菜单权限中
            boolean match = authList.stream().anyMatch(userAuthList::contains);
            if (match) {
                menu.add(ma.getMenu());
            }
        }
        return menu;
    }

    @Override
    public List<UserAuthDto> getUserAuth(String userId) {
        return userAuthDao.getUserAuth(userId);
    }

    @Override
    public void deleteAuth(DeleteAuthForm deleteAuthForm) {
        userAuthDao.deleteAuth(deleteAuthForm);
    }

    @Override
    public void updateAuth(UpdateAuthForm updateAuthForm) {
        userAuthDao.updateAuth(updateAuthForm);
    }

    // 获取用户ID  权限ID 和 状态
    @Override
    public UserAuthDto getUserIdAndAuthIdAndStatus(String userId, Integer authId) {
        return userAuthDao.getUserIdAndAuthIdAndStatus(userId, authId);
    }

    @Override
    @Transactional
    public void updateExistAuth(UpdateAuthForm updateAuthForm) {
        // 新权限 状态置1
        userAuthDao.updateAuthStatus(updateAuthForm.getUserId(), updateAuthForm.getAuthId(), 1, updateAuthForm.getOperatorId());
        // 旧权限 状态置0
        userAuthDao.updateAuthStatus(updateAuthForm.getUserId(), updateAuthForm.getOldAuth(), 0, updateAuthForm.getOperatorId());

    }

    @Override
    @Transactional
    public void addAuth(AddAuthForm addAuthForm) {
        for (int authId : addAuthForm.getAuthIds()){
            // 获取数据库中的记录
            UserAuthDto userAuthDto = userAuthDao.getUserIdAndAuthIdAndStatus(addAuthForm.getUserId(), authId);
            // 判断有没有记录
            if(userAuthDto == null) {
                // 新增权限记录
                userAuthDao.saveAuth(addAuthForm.getUserId(), authId, addAuthForm.getOperatorId());
            }else {
                // 更新 权限状态
                userAuthDao.updateAuthStatus(addAuthForm.getUserId(), authId, 1, addAuthForm.getOperatorId());
            }
        }
    }


}