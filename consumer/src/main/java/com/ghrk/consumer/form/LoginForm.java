package com.ghrk.consumer.form;

import com.ghrk.common.utils.Constant;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 登录表单
 */
@Data
public class LoginForm {
    // 用户ID
    @NotNull(message = "请输入用户ID")
    @Pattern(regexp = Constant.USERID_REGEX, message = "用户ID不合法")
    private String userId;
    // 密码
    private String password;
    // 验证码
    private String captcha;
    // UUID 根据UUID生成的验证码
    private String uuid;

}
