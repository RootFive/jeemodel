package com.jeemodel.core.utils.ip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.http.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IPInternetUtils {

	public static String getInternetIP(boolean isReturnIPV4) {
		
		List<String> ipV4List = new ArrayList<>();

		String ip = null;

		// 第1种方式
		try {
			ip = getNowIP1();
			String ipTrim = StringUtils.trimToEmpty(ip);
			if (!IpUtils.internalIp(ipTrim)) {
				ipV4List.add(ipTrim);
			}
		} catch (Exception e) {
			log.warn("getNowIP5 获取公网IP异常", e);
		}

		// 第2种方式
		try {
			ip = getNowIP2();
			String ipTrim = StringUtils.trimToEmpty(ip);
			if (!IpUtils.internalIp(ipTrim)) {
				ipV4List.add(ipTrim);
			}
		} catch (Exception e) {
			log.warn("getNowIP5 获取公网IP异常", e);
		}

		// 第3种方式
		try {
			ip = getNowIP3();
			String ipTrim = StringUtils.trimToEmpty(ip);
			if (!IpUtils.internalIp(ipTrim)) {
				ipV4List.add(ipTrim);
			}
		} catch (Exception e) {
			log.warn("getNowIP5 获取公网IP异常", e);
		}
		
		log.info("ipV4List={}",ipV4List);

		if (isReturnIPV4 && ipV4List.size() > 0) {
			String ipv4 = ipV4List.stream().collect(Collectors.groupingBy(String::valueOf)).values().stream()
					.sorted((a, b) -> b.size() - a.size()).collect(Collectors.toList()).get(0).get(0);

			return ipv4;
		} else {
			return new HashSet<>(ipV4List).toString();
		}
	}

	private static String getNowIP1() throws IOException {

		String sendGet = HttpUtils.sendGet("http://v6r.ipip.net/?format=callback");

		String[] split = StringUtils.split(sendGet, "'");

		return split[1];
	}

	private static String getNowIP2() throws IOException {

		return HttpUtils.sendGet("http://checkip.amazonaws.com/");
	}

	private static String getNowIP3() throws IOException {

		return HttpUtils.sendGet("https://ipv4.icanhazip.com/");
	}

}
