package com.jeemodel.bean.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rootfive 2020-12-24	 联系方式: QQ群：2236067977  
 * <p>Description: DTO_CRUD基类</p>
 * For example: 
 * <blockquote><pre>
 * 1、直接继承使用此类的数据库表的DTO对象类，XXX 即为数据表名，比如：
 * 		public  class XXXDTO extends BaseDTOCRUD{
 * 		}
 * 2、通常：可以数据库表CRUD操作，会产生物理删除，但是不会产生逻辑删除，
 * 3、数据库CRUD操作
 * 		涉及：
 * 			增加(Create)【注意】此处[增加]可以记录用户
 * 			检索查询(Retrieve)
 * 			更新(Update)
 * 			删除(Delete),注意：此处删除是物理删除
 * 		不涉及：
 * 			逻辑删除(Logical Delete)
 * 4、所含字段有：
 * 		继承：Long id				主键ID（自增）
 * 		继承：String remark		备注
 * 		继承：Date createTime		创建时间
 * 		继承：String createBy		创建时间
 * 
 * 		私有：String status		停用状态:0正常,1停用
 * 		私有：Date updateTime		更新时间
 * 		私有：String updateBy		更新者
 * </pre></blockquote><p>
 * @since   JeeModel 1.0.0
 */
@ApiModel(description = "DTO_CRUD基类")
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDTOCRUD extends BaseDTOCRD {

	@ApiModelProperty(value = "停用状态:0正常,1停用", example = "0")
	/** 停用状态:0正常,1停用 */
	protected  String status;
	
	@ApiModelProperty(value = "更新时间,格式：yyyy-MM-dd HH:mm:ss", example = "2021-01-20 12:59:59")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime updateTime ;
	
	@ApiModelProperty(value = "更新者", example = "jeemodel")
	protected String updateBy ;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("status", status);
		builder.append("updateTime", updateTime);
		builder.append("updateBy", updateBy);
		builder.append("createBy", createBy);
		builder.append("id", id);
		builder.append("remark", remark);
		builder.append("createTime", createTime);
		return builder.toString();
	}
}