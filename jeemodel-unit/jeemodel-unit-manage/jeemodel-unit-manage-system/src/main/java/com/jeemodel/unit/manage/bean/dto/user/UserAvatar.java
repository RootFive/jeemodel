package com.jeemodel.unit.manage.bean.dto.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户头像上传")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAvatar {
	
	@ApiModelProperty(value = "用户头像相对路径" ,example = "/profile/auth/user/avatar/2021/01/26/b8dfa818-2068-48fb-a609-bcc07ca2abf3.jpeg")
	private String imgUrl;
}
