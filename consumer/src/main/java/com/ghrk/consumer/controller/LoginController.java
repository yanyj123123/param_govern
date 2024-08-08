package com.ghrk.consumer.controller;

import com.ghrk.common.entity.UserInfoEntity;
import com.ghrk.common.service.CaptchaService;
import com.ghrk.common.service.UserAuthService;
import com.ghrk.common.service.UserInfoService;
import com.ghrk.common.utils.JwtUtilsInterface;
import com.ghrk.common.utils.R;
import com.ghrk.consumer.form.LoginForm;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {

    @DubboReference
    private CaptchaService captchaService;

    @DubboReference
    private UserInfoService userInfoService;

    @DubboReference
    private JwtUtilsInterface jwtUtils;

    @DubboReference
    private UserAuthService userAuthService;

    /**
     * 验证码
     */
    @GetMapping("/captcha")
    public R captcha(@RequestParam("uuid") String uuid) {
        String captcha = captchaService.getCaptcha(uuid);
        return R.ok().put("captcha", captcha);
    }

    /**
     * 登录接口
     * @param form 登录表单参数
     * @return R
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginForm form){
        boolean captcha = captchaService.validate(form.getUuid(), form.getCaptcha());
        if(!captcha){
            return R.error("验证码不正确");
        }

        //用户信息
        UserInfoEntity user = userInfoService.getUserByUserId(form.getUserId());

        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(form.getPassword())) {
            return R.error("账号或密码不正确");
        }
        List<String> userAuthList = userAuthService.getUserAuthList(user.getUserId());
        //生成token
        String token = jwtUtils.generateToken(user.getUserId());
        HashMap<String, Object> data = new HashMap<>();
        data.put(jwtUtils.getHeader(), token);
        data.put("userId", user.getUserId());
        data.put("userName", user.getUserName());
        data.put("authList", userAuthList);

        return R.ok().put("data", data);
    }

    @GetMapping("menu")
    public R getMenu(){
        return R.ok();
    }
}
