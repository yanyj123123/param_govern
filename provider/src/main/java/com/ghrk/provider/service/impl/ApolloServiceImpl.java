package com.ghrk.provider.service.impl;

import com.ghrk.common.dto.City;
import com.ghrk.common.service.ApolloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@DubboService
public class ApolloServiceImpl implements ApolloService {



    // 菜单权限映射
    @Value("${MenuAuth}")
    private String menuAuth;

    @Override
    public String getMenuAuth() {
        return menuAuth;
    }


}
