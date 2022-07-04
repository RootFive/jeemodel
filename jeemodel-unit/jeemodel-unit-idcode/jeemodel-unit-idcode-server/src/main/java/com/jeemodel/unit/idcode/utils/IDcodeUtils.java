package com.jeemodel.unit.idcode.utils;

import com.jeemodel.core.utils.ExceptionUtils;

public class IDcodeUtils {


	/**
	 * 下一个场景标识, 生成的场景码最多由三部分组成 1、场景码 2、补位0（可能会有） 3、UID序列号（转成36进制的大写字符串形式）
	 * 
	 * @return
	 */
	public static String nextSceneIDCode(String sceneCode, String sceneName, Integer uidLength, int uidSerial) {
		// UID上限制（不包含）
		int uidUpLimit = 0;
		switch (uidLength) {
		case 2:
			// UID长度2，上限:1296 = 36 的2次方
			uidUpLimit = 1296;
			break;
		case 3:
			// UID长度3，上限:46656 = 36 的3次方
			uidUpLimit = 46656;
			break;
		case 4:
			// UID长度4，上限:1679616 = 36 的4次方
			uidUpLimit = 1679616;
			break;
		case 5:
			// UID长度5，上限:60466176 = 36 的5次方
			uidUpLimit = 60466176;
			break;
		default:
			// UID长度6，上限:2176782336 = 36 的6次方,最大值是取值是int最大值：2147483647
			uidUpLimit = Integer.MAX_VALUE;
			break;
		}

		if (uidSerial >= uidUpLimit) {
			throw ExceptionUtils.fail("{}-标识编码已经达到上限：{}", sceneName, uidUpLimit);
		}

		String useSceneUID = Integer.toString(uidSerial, 36).toUpperCase();
		Integer alternate = uidLength - useSceneUID.length();
		StringBuilder sb = new StringBuilder(sceneCode);
		if (alternate > 0) {
			for (int i = 0; i < alternate; i++) {
				sb.append(0);
			}
		}
		sb.append(useSceneUID);

		return sb.toString();
	}
}
