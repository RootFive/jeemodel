package com.jeemodel.bean.dto;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rootfive 2020-12-24	 联系方式: QQ群：2236067977  
 * <p>Description: DTO_CRD基类</p>
 * For example: 
 * <blockquote><pre>
 * 1、直接继承使用此类的数据库表的DTO对象类，XXX 即为数据表名，比如：
 * 		public  class XXXDTO extends BaseDTOCRD{
 * 		}
 * 2、通常：此类表只做关系表使用，一般不会发生单条的数据更新，只会发生新增和物理删除（非逻辑删除）
 * 3、数据库CRUD操作
 * 		涉及：
 * 			增加(Create)【注意】此处[增加]可以记录用户
 * 			检索查询(Retrieve)
 * 			删除(Delete)【注意】此处[增加]删除是物理删除
 * 		不涉及：
 * 			更新(Update)
 * 			逻辑删除(Logical Delete)
 * 4、所含字段有：
 * 		继承：Long id				主键ID（自增）
 * 		继承：String remark		备注
 * 		继承：Date createTime		创建时间
 * 
 * 		私有：String createBy		创建时间
 * </pre></blockquote><p>
 * @since   JeeModel 1.0.0
 */
@ApiModel(description = "DTO_CRD基类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDTOCRD extends BaseDTO {

	@ApiModelProperty(value = "创建者", example = "admin")
	protected String createBy;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("createBy", createBy);
		builder.append("id", id);
		builder.append("remark", remark);
		builder.append("createTime", createTime);
		return builder.toString();
	}
}