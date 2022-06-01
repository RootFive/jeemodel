package com.jeemodel.bean.enums.code.sub.base;

import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;

import lombok.Getter;

/**
 * @author Rootfive
 * <p>SubCode类型枚举</p>
 */
@Getter
public enum BaseSubCodeTypeServiceLogicEnum  implements IBaseSubCodeType{
	

	/** 缺少参数： {@link CodeEnum#MISSING_119} */
	MISSING(CodeEnum.MISSING_119),

	/** 非法参数： {@link CodeEnum#WARN_120} */
	WARN(CodeEnum.WARN_120),

	/** 处理失败： {@link CodeEnum#FAIL_122} */
	FAIL(CodeEnum.FAIL_122),
	
	//通用基本类型 上 XXX =========
	
	
	/** 非法参数，类型/格式错误 ，命名范围：W0101-W0199 {@link CodeEnum#WARN_120}   */
	REQUEST_PARAM_FORMAT_ERROR( CodeEnum.WARN_120, "参数类型/格式错误"),
	
	/** 非法参数，超出限制值（长度大小数量值越界） ，命名范围：W0201-W0299 {@link CodeEnum#WARN_120}   */
	REQUEST_PARAM_FORMAT_IS_OVER_THE_LIMIT( CodeEnum.WARN_120, "参数超出限制值"),
	
	
	/** 文件上传（或导入），命名范围：W0301-W0350 {@link CodeEnum#WARN_120}   */
	REQUEST_PARAM_FILE_UPLOAD_IMPORT( CodeEnum.WARN_120, "文件上传（或导入）"), 
	
	/** 文件下载（或导出），命名范围：W0351-W0399 {@link CodeEnum#WARN_120}   */
	REQUEST_PARAM_FILE_DOWNLOAD_EXPORT( CodeEnum.WARN_120, "文件下载（或导出）"), 

	
	/** 非法参数，参数内容不合规 ，命名范围：W0401-W0499 {@link CodeEnum#WARN_120}   */
	REQUEST_PARAM_ILLEGAL_CONTENT( CodeEnum.WARN_120, "参数内容非法"),
	

	
	
	
	
	/** 请求被拒绝 ，命名范围：F0101-F0109 {@link CodeEnum#FAIL_122}   */
	REQUEST_NO_ACCESS( CodeEnum.FAIL_122, "请求被拒绝"),

	
	
	
	;

	/** 父级别错误码 */
	private CodeEnum code;
	/** 注释;案语;批注 */
	private String note;

	private BaseSubCodeTypeServiceLogicEnum(CodeEnum code) {
		this.code = code;
	}
	
	private BaseSubCodeTypeServiceLogicEnum(CodeEnum code, String note) {
		this.code = code;
		this.note = note;
	}


}
