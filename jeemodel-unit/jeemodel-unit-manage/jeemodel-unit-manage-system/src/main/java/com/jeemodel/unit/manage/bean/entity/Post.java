package com.jeemodel.unit.manage.bean.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.data.entity.BaseEntityCRUDX;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表 manage_post
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_post")
public class Post extends BaseEntityCRUDX {
	
	private static final long serialVersionUID = 1L;

	/** 岗位编码 */
	@NotBlank(message = "岗位编码不能为空")
	@Size(min = 0, max = 64, message = "岗位编码长度不能超过64个字符")
	@Excel(name = "岗位编码")
	private String postCode;

	/** 岗位名称 */
	@NotBlank(message = "岗位名称不能为空")
	@Size(min = 0, max = 50, message = "岗位名称长度不能超过50个字符")
	@Excel(name = "岗位名称")
	private String postName;

	/** 岗位排序 */
	@NotBlank(message = "显示顺序不能为空")
	@Excel(name = "岗位排序")
	private String postSort;

	/** 状态（0正常 1停用） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/** 用户是否存在此岗位标识 默认不存在 */
//	private boolean flag = false;

}
