package com.ghrk.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserAuthDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，用于唯一索引 GHCD+4+1
     */
    private String userId;
    /**
     * 用户的真实姓名
     */
    private String userName;
    /**
     * 用户权限ID
     */
    private Integer authId;

    /**
     * 权限状态  0：失效  1：有效
     */
    private Integer authStatus;

    /**
     * 权限更新时间
     */
    private Date updateTime;


}
