package com.jeemodel.bean.enums.code.sub.base;

import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;

import lombok.Getter;

/**
 * @author Rootfive
 * <p>SubCode类型枚举</p>
 */
@Getter
public enum BaseSubCodeTypeSystemEnum  implements IBaseSubCodeType{
	
	/** 调用成功： {@link CodeEnum#OK} */
	OK(CodeEnum.OK),

	/** 请求错误： {@link CodeEnum#ERROR_110} */
	ERROR(CodeEnum.ERROR_110),


	/** 未知错误： {@link CodeEnum#UNKNOWN_112112} */
	UNKNOWN(CodeEnum.UNKNOWN_112112),

	/** 服务不可用： {@link CodeEnum#SOS_191519} */
	SOS(CodeEnum.SOS_191519),
	
	
	//通用基本类型 上 XXX =========
	
	/** 请求异常（可能是非人为操作），命名范围：EA0101-E0199 {@link CodeEnum#ERROR_110}   */
	REQUEST_ANOMALOUS( CodeEnum.ERROR_110, "请求异常"),
	;

	/** 父级别错误码 */
	private CodeEnum code;
	/** 注释;案语;批注 */
	private String note;

	private BaseSubCodeTypeSystemEnum(CodeEnum code) {
		this.code = code;
	}
	
	private BaseSubCodeTypeSystemEnum(CodeEnum code, String note) {
		this.code = code;
		this.note = note;
	}
}
