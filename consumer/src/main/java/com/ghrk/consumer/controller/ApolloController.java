package com.ghrk.consumer.controller;

import com.alibaba.fastjson2.JSON;
import com.ghrk.common.dto.City;
import com.ghrk.common.dto.MenuAuth;
import com.ghrk.common.service.ApolloService;
import com.ghrk.common.utils.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApolloController {

//    @DubboReference
//    private ApolloService apolloService;
//
//    @GetMapping("/permissionDict")
//    public R getPermissionDict(){
//        String permissionDict = apolloService.getPermissionDict();
//        return R.ok().put("data", permissionDict);
//    }
//
//    @GetMapping("/cities")
//    public R getCities(){
//        String cityString = apolloService.getCities();
//        List<City> cities = JSON.parseArray(cityString, City.class);
//        return R.ok().put("data", cities);
//    }
//
//


}
