package com.jeemodel.solution.file.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "通用文件-上传响应")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {

	@ApiModelProperty(value = "文件相对地址")
	private String fileName;
	
	@ApiModelProperty(value = "文件访问查看地址")
	private String url;
}
