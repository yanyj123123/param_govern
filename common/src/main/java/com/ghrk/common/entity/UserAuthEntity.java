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
@TableName("user_auth")
public class UserAuthEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID，与权限ID一起唯一索引用户对应的某一权限
	 */
	private String userId;
	/**
	 * 权限ID，与用户ID一起唯一索引用户对应的某一权限
	 */
	private Integer authId;
	/**
	 * 权限状态，值为0或1:0表示权限失效，1表示权限有效
	 */
	private Integer authStatus;
	/**
	 * 用户权限的创建时间
	 */

	private Date createTime;
	/**
	 * 用户权限的最新修改时间
	 */
	private Date updateTime;
	/**
	 * 最近对这条权限进行操作的人员
	 */
	private String operatorId;
	/**
	 * 最近对这条权限进行操作的方式，值为0，1，2：0表示添加，1表示删除，2表示修改
	 */
	private Integer operation;

}
