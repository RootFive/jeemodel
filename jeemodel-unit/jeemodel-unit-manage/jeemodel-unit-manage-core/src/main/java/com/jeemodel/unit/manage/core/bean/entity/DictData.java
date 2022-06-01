package com.jeemodel.unit.manage.core.bean.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.bean.annotation.Excel.ColumnType;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;
import com.jeemodel.data.entity.BaseEntityCRUD;
import com.jeemodel.unit.manage.core.constant.UserConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表 manage_dict_data
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("manage_dict_data")
public class DictData extends BaseEntityCRUD {
	private static final long serialVersionUID = 1L;

	/** 字典排序 */
	@Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
	private Long dictSort;

	/** 字典标签 */
	@NotBlank(message = "字典标签不能为空")
	@Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
	@Excel(name = "字典标签")
	private String dictLabel;

	/** 字典键值 */
	@NotBlank(message = "字典键值不能为空")
	@Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
	@Excel(name = "字典键值")
	private String dictValue;

	/** 字典类型 */
	@NotBlank(message = "字典类型不能为空")
	@Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
	@Excel(name = "字典类型")
	private String dictType;

	/** 样式属性（其他样式扩展） */
	@Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
	private String cssClass;

	/** 表格字典样式 */
	private String listClass;

	/** 是否默认（Y是 N否） */
	@Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
	private String isDefault;

	/** 字典状态（0正常 1停用） */
	@Excel(name = "字典状态", readConverterExp = "0=正常,1=停用")
	private String status;
	

	public boolean getDefault() {
		return UserConstants.YES.equals(this.isDefault) ? true : false;
	}


	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("dictSort", dictSort);
		builder.append("dictLabel", dictLabel);
		builder.append("dictValue", dictValue);
		builder.append("dictType", dictType);
		builder.append("cssClass", cssClass);
		builder.append("listClass", listClass);
		builder.append("isDefault", isDefault);
		builder.append("status", status);
		builder.append("updateTime", updateTime);
		builder.append("updateBy", updateBy);
		builder.append("createBy", createBy);
		builder.append("remark", remark);
		builder.append("id", id);
		builder.append("createTime", createTime);
		return builder.toString();
	}

}
