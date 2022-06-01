package com.jeemodel.data.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Rootfive 2020-12-24 联系方式: QQ群：2236067977
 *         <p>
 *         Description: 数据对象基类
 *         </p>
 *         For example: <blockquote>
 * 
 *         <pre>
 * 1、直接继承使用此类的数据库表，XXX 即为数据表名，比如：
 * 		public  class XXX extends BaseEntity{
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
 *         </pre>
 * 
 *         </blockquote>
 *         <p>
 * @since JeeModel 1.0.0 Java 代码注释——TODO、FIXME、XXX TODO: + 说明： 标识处有功能代码待编写
 *        FIXME: + 说明：标识处代码需要修正 XXX：+ 说明：标识处代码待改进
 * 
 */
@Data
@ApiModel(description = "数据对象基类")
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID", example = "1")
	@TableId(type = IdType.AUTO)
	protected Long id;

	@ApiModelProperty(value = "备注", example = "研发测试")
	protected String remark;

	@ApiModelProperty(value = "创建时间,格式：yyyy-MM-dd HH:mm:ss", example = "2021-01-20 12:59:59")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime;

	/**
	 * 构造函数 DTO 转 Entity
	 * 
	 * @param baseDTO
	 */
//	public BaseEntity(BaseDTO baseDTO) {
//		super();
//		this.id = baseDTO.getId();
//		this.remark = baseDTO.getRemark();
//		this.createTime = baseDTO.getCreateTime();
//	}

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("id", id);
		builder.append("remark", remark);
		builder.append("createTime", createTime);
		return builder.toString();
	}

}