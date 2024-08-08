package com.ghrk.common.form;

import com.ghrk.common.utils.Constant;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class DeleteAuthForm implements Serializable {

    private static final long serialVersionUID = 1L;

    // 用户ID
    @NotNull(message = "请输入用户ID")
    @Pattern(regexp = Constant.USERID_REGEX, message = "用户ID不合法!")
    private String userId;

    // 权限ID
    @NotNull(message = "请输入权限!")
    @Range(min = 1, max = 6, message = "未查询到该权限!")
    private Integer authId;

    // 操作人的ID
    @NotNull(message = "请输入操作人员ID")
    @Pattern(regexp = "^GHCD\\d{5}$", message = "操作人员ID不合法!")
    private String operatorId;

}
