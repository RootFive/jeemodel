package com.jeemodel.data.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rootfive 2020-12-24	 联系方式: QQ群：2236067977  
 * <p>Description: 数据对象基类</p>
 * For example: 
 * <blockquote><pre>
 * 1、直接继承使用此类的数据库表，XXX 即为数据表名，比如：
 * 		public  class XXX extends BaseEntityCRUDX{
 * 		}
 * 2、通常：可以数据库表CRUD操作，其中删除操作不仅会产生物理删除，也会产生逻辑删除，
 * 3、数据库CRUD操作
 * 		涉及：
 * 			增加(Create)【注意】此处[增加]可以记录用户
 * 			检索查询(Retrieve)
 * 			更新(Update)
 * 			删除(Delete)【注意】此处删除包含是物理和逻辑两种删除
 * 		不涉及：无
 * 4、所含字段有：
 * 		继承：Long id				主键ID（自增）
 * 		继承：String remark		备注
 * 		继承：Date createTime		创建时间
 * 		继承：String createBy		创建时间
 * 		继承：String status		停用状态:0正常,1停用
 * 		继承：Date updateTime		更新时间
 * 		继承：String updateBy		更新者
 * 
 * 		私有：Integer delFlag		删除标志，逻辑删除标志：0代表存在 2代表删除，非0即代表删除
 * 		私有：Date delTime		删除时间，逻辑删除时间
 * </pre></blockquote><p>
 * @since   JeeModel 1.0.0
 */
@ApiModel(description = "数据对象基类")
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntityCRUDX extends BaseEntityCRUD	 {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "逻辑删除标志：0代表存在 2代表删除，非0即代表删除", example = "0")
	//@Excel(name = "删除标志", readConverterExp = "0=存,2=删除")
	protected Integer delFlag;

	@ApiModelProperty(value = "逻辑删除时间,格式：yyyy-MM-dd HH:mm:ss", example = "2021-01-20 12:59:59")
	//@Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime delTime ;

	
	
	/**
	 * 构造函数 DTO 转 Entity
	 * 
	 * @param baseDTO
	 */
//	public BaseEntityCRUDX(BaseDTOCRUDX baseDTOCRUDX) {
//		super(baseDTOCRUDX);
//		this.delFlag = baseDTOCRUDX.getDelFlag();
//		this.delTime = baseDTOCRUDX.getDelTime();
//	}
	
}