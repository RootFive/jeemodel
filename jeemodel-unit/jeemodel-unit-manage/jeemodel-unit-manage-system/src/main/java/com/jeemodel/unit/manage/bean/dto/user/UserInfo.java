package com.jeemodel.unit.manage.bean.dto.user;

import java.util.List;

import com.jeemodel.unit.manage.bean.entity.Post;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户详细信息（新增修改时）")
@Data
@NoArgsConstructor
public class UserInfo {
	
	@ApiModelProperty(value = "用户可以操作的角色信息,【比如：超级管理admin可以看到全部角色，非admin可以看到除admin 意外的其他角色】")
	private List<Role> roles;
	
	@ApiModelProperty(value = "全部的部门信息")
	private List<Post> posts;
	
	
	@ApiModelProperty(value = "用户的基本信息")
	private User user;
	
	@ApiModelProperty(value = "用户的角色Ids")
	private List<Long> roleIds;
	
	@ApiModelProperty(value = "用户的部门Ids")
	private List<Long> postIds;
	
	
	public UserInfo(List<Role> roles, List<Post> posts) {
		super();
		this.roles = roles;
		this.posts = posts;
	}
	
}
