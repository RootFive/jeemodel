package com.jeemodel.bean.http;

import javax.validation.constraints.NotEmpty;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
/**
 * @author Rootfive
 */
@ApiModel(description = "请求公共参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PingTop {
	
	
	
	@ApiModelProperty(value = "【必传】,请求时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8。平台API服务端允许客户端请求最大时间误差为5分钟", example = "2021-01-01 12:00:00", required = true)
    @NotEmpty
	private Long timestamp;
	
	@ApiModelProperty(value = "【必传】,请求客户端，可选址为：m-android,m-ios,m-h5,pc-h5,other", example = "pc-h5", required = true)
    @NotEmpty
	private String client;
	
	
	
	@ApiModelProperty(value = "【必传】,请求渠道", example = "jeemodel", required = true)
    @NotEmpty
	private String channel;
	
	@ApiModelProperty(value = "【必传】,身份标识", example = "jeemodel", required = true)
    @NotEmpty
	private String partnerId;
	
	
	
	@ApiModelProperty(value = "【必传】,签名的摘要算法，可选值为：hmac，md5", example = "md5", required = true)
    @NotEmpty
	private String signType;
	
	//API输入参数签名结果，
	@ApiModelProperty(value = "【必传】,签名字符串", example = "e10adc3949ba59abbe56e057f20f883e", required = true)
    @NotEmpty
	private String sign;
	
	
	
	
	
	
	//XXX：+ 说明：下列字段待改进，改进的前提是接口请求地址统一
	
	@ApiModelProperty(value = "非必传,API接口名称", example = "jeemodel.api.user.list")
	private String method;
	
	@ApiModelProperty(value = "非必传,API协议版本，可选值：1.0", example = "1.0")
	private String version;
	
	@ApiModelProperty(value = "非必传,API响应格式。默认为json格式，可选值：json", example = "json")
	private String format;

	
	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (channel != null)
			builder.append("channel", channel);
		if (partnerId != null)
			builder.append("partnerId", partnerId);
		if (client != null)
			builder.append("client", client);
		if (timestamp != null)
			builder.append("timestamp", timestamp);
		if (signType != null)
			builder.append("signType", signType);
		if (sign != null)
			builder.append("sign", sign);
		if (method != null)
			builder.append("method", method);
		if (version != null)
			builder.append("version", version);
		if (format != null)
			builder.append("format", format);
		return builder.toString();
	}
}
