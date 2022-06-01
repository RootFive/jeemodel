package com.jeemodel.unit.manage.bean.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.data.entity.BaseEntityCRUDX;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告表 manage_notice
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_notice")
public class Notice extends BaseEntityCRUDX {
	private static final long serialVersionUID = 1L;

	/** 公告标题 */
	@NotBlank(message = "公告标题不能为空")
	@Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
	private String noticeTitle;

	/** 公告类型（1通知 2公告） */
	private String noticeType;

	/** 公告内容 */
	@NotBlank(message = "公告内容不能为空")
	private String noticeContent;

	
}
