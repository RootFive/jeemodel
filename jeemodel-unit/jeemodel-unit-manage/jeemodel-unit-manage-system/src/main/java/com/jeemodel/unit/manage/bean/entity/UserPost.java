package com.jeemodel.unit.manage.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.data.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和岗位关联 manage_user_post
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_user_post")
public class UserPost extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private Long userId;

	/** 岗位ID */
	private Long postId;

}
