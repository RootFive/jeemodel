package com.jeemodel.bean.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeemodel.bean.enums.code.sub.IBaseSubCode;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rootfive
 * @EqualsAndHashCode: 
 * 1. 此注解会生成equals(Object other) 和 hashCode()方法。 
 * 2. 它默认使用非静态，非瞬态的属性 
 * 3. 可通过参数exclude排除一些属性 
 * 4. 可通过参数of指定仅使用哪些属性
 * 5. 它默认仅使用该类中定义的属性且不调用父类的方法 
 * 6. 可通过callSuper=true解决上一点问题。让其生成的方法中调用父类的方法。
 */
@ApiModel(description = "基础响应")
@Data
@NoArgsConstructor
public class Pong {

	@ApiModelProperty(value = "响应回声，API回声", example = "7daf70d482bd6cf7")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String echo;

	@ApiModelProperty(value = "消息状态码", example = "0")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected Integer code;

	@ApiModelProperty(value = "消息状态码描述", example = "成功")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String msg;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ApiModelProperty(value = "消息子状态码", example = "00000")
	protected String subCode;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "消息子状态码描述", example = "一切 ok")
	protected String subMsg;

	/**
	 * @param echo
	 * @param baseSubCode
	 */
	public Pong(String echo,IBaseSubCode baseSubCode) {
		super();
		this.echo = echo;
		this.code = baseSubCode.getType().getCode().code();
		this.msg =baseSubCode.getType().getCode().msg();
		this.subCode = baseSubCode.getSubCode();
		this.subMsg = baseSubCode.getExplain();
	}
	
	
	/**
	 * @param echo
	 * @param baseSubCode
	 * @param customMsg
	 */
	public Pong(String echo,IBaseSubCode baseSubCode,String customMsg) {
		super();
		this.echo = echo;
		this.code = baseSubCode.getType().getCode().code();
		this.msg =baseSubCode.getType().getCode().msg();
		this.subCode = baseSubCode.getSubCode();
		this.subMsg = customMsg;
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (echo != null) {
			builder.append("echo", echo);
		}
		builder.append("code", code);
		builder.append("msg", msg);
		builder.append("subCode", subCode);
		builder.append("subMsg", subMsg);
		return builder.toString();
	}
}
