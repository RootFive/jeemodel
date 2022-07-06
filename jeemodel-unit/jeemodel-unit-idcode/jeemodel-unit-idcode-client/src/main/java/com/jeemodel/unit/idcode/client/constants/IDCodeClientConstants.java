package com.jeemodel.unit.idcode.client.constants;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.solution.netty.client.invoke.ResponseFuture;
import com.jeemodel.unit.idcode.common.bean.ProtoDTO;

/**
 * 通用常量信息
 * @author Rootfive
 */
public class IDCodeClientConstants {
	
	/** 响应结果 */
	public static final ConcurrentMap<String, ResponseFuture<PongData<ProtoDTO>>> RESPONSE_RESULT = new ConcurrentHashMap<>();
}