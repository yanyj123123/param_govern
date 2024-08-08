package com.ghrk.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author 
 * @email ${email}
 * @date 2024-07-12 16:37:18
 */
@Data
@TableName("user_info")
public class UserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID，用于唯一索引 GHCD+4+1
	 */
	private String userId;
	/**
	 * 用户的真实姓名
	 */
	private String userName;
	/**
	 * 用户的手机号码，用于登录
	 */
	private String userPhone;
	/**
	 * 用户的账号密码，用于登录
	 */
	private String password;
	/**
	 * 用户信息的创建时间
	 */
	private Date createTime;
	/**
	 * 用户信息的最近修改时间
	 */
	private Date updateTime;
	/**
	 * 用户的邮箱
	 */
	private String email;
	/**
	 * 用户注册城市
	 */
	private Integer city;
	/**
	 * 用户的注册地址
	 */
	private String registerAddress;

}
