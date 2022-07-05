package com.jeemodel.bean.rpc;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "请求参数")
public class Ping<Q> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private final static AtomicLong ECHO_AUTO = new AtomicLong(System.currentTimeMillis());

	/**
	 * API回声前缀
	 * es：echo server 服务之间调用ES开头 echo client
	 * ec：echo client 外部客户端调用 
	 */
	@ApiModelProperty(value = "【必传】,API回声，会在当次响应返回", example = "7daf70d482bd6cf7", required = true)
    @NotEmpty
	private String echo;
	
	@SuppressWarnings("deprecation")
	@ApiModelProperty(value = "【必传】,请求公共参数", required = true)
	@Valid
//	@NotNull
	private PingTop top;
	
	@ApiModelProperty(value = "业务数据", required = true)
	@NotNull
	@Valid
	private Q data;
	
	public Ping() {
		super();
		this.echo = "echo"+ECHO_AUTO.incrementAndGet();
	}

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (echo != null)
			builder.append("echo", echo);
		if (top != null)
			builder.append("top", top);
		if (data != null)
			builder.append("data", data);
		return builder.toString();
	}
}
