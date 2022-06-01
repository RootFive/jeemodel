package com.jeemodel.unit.manage.core.bean.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.data.entity.BaseEntityCRUD;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表 manage_dict
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_dict")
public class Dict extends BaseEntityCRUD {
	private static final long serialVersionUID = 1L;
	
	/** 字典名称 */
	@NotBlank(message = "字典名称不能为空")
	@Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
	@Excel(name = "字典名称")
	private String dictName;

	/** 字典类型 */
	@NotBlank(message = "字典类型不能为空")
	@Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
	@Excel(name = "字典类型")
	private String dictType;

	/** 字典状态（0正常 1停用） */
	@Excel(name = "字典状态", readConverterExp = "0=正常,1=停用")
	private String status;

}
