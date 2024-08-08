package com.ghrk.consumer.controller;

import com.ghrk.common.entity.UserInfoEntity;
import com.ghrk.common.exception.GlobalException;
import com.ghrk.common.service.UserInfoService;
import com.ghrk.common.utils.Constant;
import com.ghrk.common.utils.PageUtils;
import com.ghrk.common.utils.R;
import com.ghrk.common.utils.Validate;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Pattern;


/**
 * 用户信息
 */
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
    @DubboReference
    private UserInfoService userInfoService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userInfoService.queryPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/{id}")
    public R info(@PathVariable("id") String userId){
        boolean valid = Validate.validateUserId(userId);
        if(!valid){
            throw new GlobalException("用户ID不合法!");
        }
        UserInfoEntity userInfo = userInfoService.getByUserId(userId);

        return R.ok().put("userInfo", userInfo);
    }

    @GetMapping("/textException")
    public R testException(){
        //throw new NumberFormatException("异常");
        userInfoService.testException();
        return R.ok();
    }

}
