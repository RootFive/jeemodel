package com.jeemodel.bean.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rootfive 2020-12-24	 联系方式: QQ群：2236067977  
 * <p>Description: DTO基类</p>
 * For example: 
 * <blockquote><pre>
 * 1、直接继承使用此类的数据库表的DTO对象类，XXX 即为数据表名，比如：
 * 		public  class XXXDTO extends BaseDTO{
 * 		}
 * 2、通常：此类表只做记录功能使用，不会发生数据更新变化，如日志记录表等 
 * 3、数据库CRUD操作
 * 		涉及：
 * 			增加(Create)
 * 			检索查询(Retrieve)
 * 			删除(Delete),【注意】删除是物理删除
 * 		不涉及：
 * 			更新(Update)
 * 			逻辑删除(Logical Delete)
 * 4、主要字段：
 * 		私有：Long id				主键ID（自增）
 * 		私有：String remark		备注
 * 		私有：Date createTime		创建时间
 * </pre></blockquote><p>
 * @since   JeeModel 1.0.0
 * Java 代码注释——TODO、FIXME、XXX
 * TODO: + 说明： 标识处有功能代码待编写
 * FIXME: + 说明：标识处代码需要修正
 * XXX：+ 说明：标识处代码待改进
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DTO基类")
public abstract class BaseDTO {

	@ApiModelProperty(value = "主键ID", example = "1")
	protected Long id;

	@ApiModelProperty(value = "备注", example = "研发测试")
	protected String remark;
	
	@ApiModelProperty(value = "创建时间,格式：yyyy-MM-dd HH:mm:ss", example = "2021-01-20 12:59:59")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime createTime;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("id", id);
		builder.append("remark", remark);
		builder.append("createTime", createTime);
		return builder.toString();
	}
}