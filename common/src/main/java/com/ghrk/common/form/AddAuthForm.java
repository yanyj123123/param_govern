package com.ghrk.common.form;

import com.ghrk.common.validate.ValidAuthIdList;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
public class AddAuthForm implements Serializable {

    private static final long serialVersionUID = 1L;

    // 用户ID
    @NotNull(message = "请输入用户ID")
    @Pattern(regexp = "^GHCD\\d{5}$", message = "用户ID不合法!")
    private String userId;
    // 权限ID

    @NotEmpty(message = "权限不能为空!")
    @ValidAuthIdList(min = 1, max = 6, message = "权限不合法!") // 自定义校验规则
    private List<Integer> authIds;

    // 操作人的ID
    @NotNull(message = "请输入操作人员ID")
    @Pattern(regexp = "^GHCD\\d{5}$", message = "操作人员ID不合法!")
    private String operatorId;

}
