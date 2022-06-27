package com.jeemodel.core.utils.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jeemodel.core.constant.Constants;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.html.EscapeUtil;
import com.jeemodel.core.utils.http.HttpUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取IP方法
 */
@Slf4j
public class IpUtils {
	
	public static final String x_forwarded_for = "x-forwarded-for";
	public static final String Proxy_Client_IP = "Proxy-Client-IP";
	public static final String X_Forwarded_For = "X-Forwarded-For";
	public static final String WL_Proxy_Client_IP = "WL-Proxy-Client-IP";
	public static final String X_Real_IP = "X-Real-IP";
	
	

	// IP地址查询
	public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

	// 未知地址
	public static final String UNKNOWN = "XX XX";

	public static String getRealAddressByIP(String ip) {
		String address = UNKNOWN;
		// 内网不查询
		if (IpUtils.internalIp(ip)) {
			return "内网IP";
		}
		try {
			String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
			if (StringUtils.isEmpty(rspStr)) {
				log.error("获取地理位置异常 {}", ip);
				return UNKNOWN;
			}
			JSONObject obj = JSON.parseObject(rspStr);
			String region = obj.getString("pro");
			String city = obj.getString("city");
			return String.format("%s %s", region, city);
		} catch (Exception e) {
			log.error("获取地理位置异常 {}", ip);
		}
		return address;
	}
	
	
	
	
	
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		
		String ip = getRequestIPByHeaderName(request, x_forwarded_for);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequestIPByHeaderName(request, Proxy_Client_IP);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequestIPByHeaderName(request, X_Forwarded_For);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequestIPByHeaderName(request, WL_Proxy_Client_IP);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequestIPByHeaderName(request, X_Real_IP);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : EscapeUtil.clean(ip);
	}
	
	/**
	 * 	获取请求IP(根据headerName)
	 * @param request
	 * @param headerName
	 * @return
	 */
	public static String getRequestIPByHeaderName(HttpServletRequest request, String headerName) {
		String headerValue = request.getHeader(headerName);
		String remoteIp = null;
		if (StringUtils.isNotBlank(headerValue)) {
			String[] remoteIpArray = headerValue.split(",");
			log.debug("headerName={},headerValue={}", headerName, headerValue);
			remoteIp = StringUtils.trimToNull(remoteIpArray[0]);
		}
		log.debug("headerName={},remoteIP={}", headerName, remoteIp);
		return remoteIp;
	}
	
	
	
	/**
	 * 内网IPV4判断
	 * @param ip
	 * @return
	 */
	public static boolean internalIp(String ip) {
		byte[] addr = textToNumericFormatV4(ip);
		return internalIp(addr) || "127.0.0.1".equals(ip);
	}

	/**
	 * 内网IP判断
	 * @param addr
	 * @return
	 */
	private static boolean internalIp(byte[] addr) {
		if (BlankUtils.isNull(addr) || addr.length < 2) {
			return true;
		}
		final byte b0 = addr[0];
		final byte b1 = addr[1];
		// 10.x.x.x/8
		final byte SECTION_1 = 0x0A;
		// 172.16.x.x/12
		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
		// 192.168.x.x/16
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;
		switch (b0) {
		case SECTION_1:
			return true;
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return true;
			}
		case SECTION_5:
			switch (b1) {
			case SECTION_6:
				return true;
			}
		default:
			return false;
		}
	}

	/**
	 * 将IPv4地址转换成字节
	 * 
	 * @param text IPv4地址
	 * @return byte 字节
	 */
	public static byte[] textToNumericFormatV4(String text) {
		if (text.length() == 0) {
			return null;
		}

		byte[] bytes = new byte[4];
		String[] elements = text.split("\\.", -1);
		try {
			long l;
			int i;
			switch (elements.length) {
			case 1:
				l = Long.parseLong(elements[0]);
				if ((l < 0L) || (l > 4294967295L)) {
					return null;
				}
				bytes[0] = (byte) (int) (l >> 24 & 0xFF);
				bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
				bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 2:
				l = Integer.parseInt(elements[0]);
				if ((l < 0L) || (l > 255L)) {
					return null;
				}
				bytes[0] = (byte) (int) (l & 0xFF);
				l = Integer.parseInt(elements[1]);
				if ((l < 0L) || (l > 16777215L)) {
					return null;
				}
				bytes[1] = (byte) (int) (l >> 16 & 0xFF);
				bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 3:
				for (i = 0; i < 2; ++i) {
					l = Integer.parseInt(elements[i]);
					if ((l < 0L) || (l > 255L)) {
						return null;
					}
					bytes[i] = (byte) (int) (l & 0xFF);
				}
				l = Integer.parseInt(elements[2]);
				if ((l < 0L) || (l > 65535L)) {
					return null;
				}
				bytes[2] = (byte) (int) (l >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 4:
				for (i = 0; i < 4; ++i) {
					l = Integer.parseInt(elements[i]);
					if ((l < 0L) || (l > 255L)) {
						return null;
					}
					bytes[i] = (byte) (int) (l & 0xFF);
				}
				break;
			default:
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return bytes;
	}

	public static String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
		}
		return "127.0.0.1";
	}

	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
		}
		return "未知";
	}
	
	
	/**
	 * @Title			: 	ipv4ToLong
	 * method			: 	ip地址转成long型数字
	 * @return 			:	long
	 * @param strIp
	 * @Description     :   将IP地址转化成整数的方法如下： 
	 * 	<p>	1、通过String的split方法按.分隔得到4个长度的数组 
	 *  <br>2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1 
	 */
	public static long ipv4ToLong(String strIp) {
		String[] ip = strIp.split("\\.");
		return (Long.parseLong(ip[0]) << 24) 
				+ (Long.parseLong(ip[1]) << 16) 
				+ (Long.parseLong(ip[2]) << 8)
				+ Long.parseLong(ip[3]);
	}
	
	/**
	 * @Title			: 	longToIPv4
	 * method			: 	将十进制整数形式转换成127.0.0.1形式的ip地址 
	 * @return 			:	String
	 * @param 			:	longIp
	 * @Description     :   将整数形式的IP地址转化成字符串的方法如下： 
	 * <p>1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。 
	 * <br>2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。 
	 * <br>3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。 
	 * <br>4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
	 */
	public static String longToIPv4(long longIp) {
		// 直接右移24位  
		StringBuilder sb = new StringBuilder(String.valueOf((longIp >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位  
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位  
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0  
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}
	
	
	
	
}