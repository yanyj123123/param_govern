package com.ghrk.consumer.controller;

import com.ghrk.common.dto.UserAuthDto;
import com.ghrk.common.exception.GlobalException;
import com.ghrk.common.form.AddAuthForm;
import com.ghrk.common.form.DeleteAuthForm;
import com.ghrk.common.form.UpdateAuthForm;
import com.ghrk.common.service.UserAuthService;
import com.ghrk.common.utils.Constant;
import com.ghrk.common.utils.PageUtils;
import com.ghrk.common.utils.R;
import com.ghrk.common.utils.Validate;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @DubboReference
    private UserAuthService userAuthService;


    /**
     * 通过用户ID获取权限
     */
    @GetMapping("/search")
    public R getUserAuth(@RequestParam Map<String, Object> params){
        // 字段校验
        boolean valid = Validate.validateUserId(params.get(Constant.USER_ID));
        if(!valid){
            throw new GlobalException("用户ID不合法!");
        }
        PageUtils page = userAuthService.queryPage(params);

        return R.ok().put("data", page);
    }

    /**
     * 新增
     */
    @RequestMapping("/save")
    public R save(@RequestBody AddAuthForm addAuthForm){
        userAuthService.addAuth(addAuthForm);
        return R.ok("新增成功!");
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody UpdateAuthForm updateAuthForm){
        // 查询数据库中该用户是否已经存在新权限的记录
        UserAuthDto user = userAuthService.getUserIdAndAuthIdAndStatus(updateAuthForm.getUserId(), updateAuthForm.getAuthId());
        // 若不存在记录，则在修改
        if (user == null) {
            userAuthService.updateAuth(updateAuthForm);
        }else {
            // 若存在记录，则修改旧记录和新记录
            if (user.getAuthStatus() == 0) {
                userAuthService.updateExistAuth(updateAuthForm);
            }else {
                return R.error("权限未改变!");
            }
        }
        return R.ok("更新成功!");
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody DeleteAuthForm deleteAuthForm){
        userAuthService.deleteAuth(deleteAuthForm);
        return R.ok("删除成功!");
    }

}
