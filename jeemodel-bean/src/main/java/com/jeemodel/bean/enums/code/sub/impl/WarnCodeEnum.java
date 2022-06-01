package com.jeemodel.bean.enums.code.sub.impl;


import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.enums.code.sub.base.BaseSubCodeTypeServiceLogicEnum;

import lombok.Getter;

/**
 * @author Rootfive
 */
@Getter
public enum WarnCodeEnum implements ISubCodeServiceLogic {

	/** 非法的参数：{@link CodeEnum#WARN} */
	WARN	(BaseSubCodeTypeServiceLogicEnum.WARN,"好像还能抢救一下！！！"), 
	
	//通用基本类型 上 XXX =========
	
	/** 非法参数，类型格式错误 ，命名范围：W0101-W0199 {@link SubCodeTypeEnum2#REQUEST_PARAM_FORMAT_ERROR}   */
	W0101( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "手机号码"), 
	W0102( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "固定电话"),
	W0103( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "邮政编码"), 
	W0104( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "通讯地址"),
	W0105( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "邮箱Email"), 
	W0106( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "腾讯QQ号"), 
	W0107( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "日期时间"),
	W0108( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "Cron表达式"), 
	W0109( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "文件类型格式"), 
	W0110( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "域名"),
	W0111( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "IPv4"), 
	W0112( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "IPv6"),
	W0113( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "URL"), 
	W0114( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "URI"), 
	W0115( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_ERROR, "文件格式不支持"), 
	
	
	/** 非法参数，长度大小越界值超出限制 ，命名范围：W0201-W0299 {@link SubCodeTypeServiceLogicEnum2#REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT}   */
	W0201( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT, "长度太短"), 
	W0202( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT, "长度太长"), 
	W0203( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT, "低于最小值"), 
	W0204( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT, "超过最大值"), 
	W0205( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT, "数量太少"), 
	W0206( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT, "数量太多"), 
	
	
	/** 文件上传（或导入），命名范围：W0301-W0350 {@link SubCodeTypeServiceLogicEnum2#REQUEST_PARAM_FILE_UPLOAD_IMPORT}   */
	W0301( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "文件类型格式不支持"), 
	W0302( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "图片类型不支持"),
	W0303( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "Flash类型不支持"),
	W0304( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "媒体类型不支持"),
	W0305( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "视频类型不支持"),	
	W0306( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "文件名太长"),
	W0307( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "文件太大"), 
	W0308( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "文件太小"),
	W0309( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "文件数量太多"), 
	W0310( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "文件数量太少"), 
	W0311( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_UPLOAD_IMPORT, "文件内容为空"),
	

	

	
	/** 文件下载（或导出），命名范围：W0351-W0399
	 * <p> Use with  {@link SubCodeTypeServiceLogicEnum2#REQUEST_PARAM_FILE_DOWNLOAD_EXPORT}   */
	W0351( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_DOWNLOAD_EXPORT, "出现异常"), 
	W0352( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_DOWNLOAD_EXPORT, "数量太多"), 
	W0353( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_FILE_DOWNLOAD_EXPORT, "文件太大"),
	
	
	/** 非法参数，参数内容不合规 ，命名范围：W0401-W0499 {@link SubCodeTypeServiceLogicEnum2#REQUEST_PARAM_ILLEGAL_CONTENT}   */
	W0401( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_ILLEGAL_CONTENT, "包含恶意跳转链接"), 
	W0402( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_ILLEGAL_CONTENT, "包含非法敏感词汇"), 
	W0403( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_ILLEGAL_CONTENT, "图片包含违禁信息"), 
	W0404( BaseSubCodeTypeServiceLogicEnum.REQUEST_PARAM_ILLEGAL_CONTENT, "文件侵犯版权"),	
	
	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;
	
	
	private WarnCodeEnum( IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}

	@Override
	public String getSubCode() {
		return this.name();
	}

}
