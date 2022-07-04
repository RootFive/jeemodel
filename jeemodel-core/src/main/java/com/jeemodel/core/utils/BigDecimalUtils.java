package com.jeemodel.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author GuoHonglin
 * DecimalFormat 格式化“0”和“#”的区别
 * 1. 以“0”补位时：
 * 		如果数字少了，就会补“0”，小数和整数都会补；
 * 		如果数字多了，就切掉，但只切小数的末尾，整数不能切；
 * 		同时被切掉的小数位会进行四舍五入处理。
 * 
 * 2. 以“#”补位时：
 * 		如果数字少了，则不处理，不会补“0”，也不会补“#”；
 * 		如果数字多了，就切掉，但只切小数的末尾，整数不能切；
 * 		同时被切掉的小数位会进行四舍五入处理。
 */
public class BigDecimalUtils {
	
	/** 百:10x10 */
	public static final BigDecimal HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN);
	
	/** 千:10x100 */
	public static final BigDecimal THOUSAND = BigDecimal.TEN.multiply(HUNDRED);
	
	/** 万：10x1000:（英语没有万，要用十个一千表示，ten thousand）   */
	public static final BigDecimal TEN_THOUSAND = BigDecimal.TEN.multiply(THOUSAND);
	
	/** 十万：10x10000（英语没有十万，要用一百个一千表示，one hundred thousand） */
	public static final BigDecimal ONE_HUNDRED_THOUSAND = BigDecimal.TEN.multiply(TEN_THOUSAND);
	
	/** 百万：100x10000 */
	public static final BigDecimal MILLION = HUNDRED.multiply(TEN_THOUSAND);
	
	public static final DecimalFormat decimal_format_0 = new DecimalFormat("0");
	public static final DecimalFormat decimal_format_1 = new DecimalFormat("0.0");
	public static final DecimalFormat decimal_format_2 = new DecimalFormat("0.00");
	public static final DecimalFormat decimal_format_3 = new DecimalFormat("0.000");
	public static final DecimalFormat decimal_format_4 = new DecimalFormat("0.0000");
	public static final DecimalFormat decimal_format_5 = new DecimalFormat("0.00000");
	public static final DecimalFormat decimal_format_6 = new DecimalFormat("0.000000");
	public static final DecimalFormat decimal_format_7 = new DecimalFormat("0.0000000");
	public static final DecimalFormat decimal_format_8 = new DecimalFormat("0.00000000");
	public static final DecimalFormat decimal_format_9 = new DecimalFormat("0.000000000");
	
	
	/**
	 * 格式化输入，指定小数位，不足补0,支持百分比格式
	 * @param value
	 * @param decimal
	 * @param formatToPercentage
	 * @param roundingMode
	 * @return
     * @see    java.math.RoundingMode
	 */
	public static String format(BigDecimal value, int decimal,boolean formatToPercentage,RoundingMode roundingMode) {
		StringBuilder sb = new StringBuilder();
		if (value != null) {
			// 四舍五入
			if (formatToPercentage) {
				value = value.setScale(decimal+2, roundingMode).multiply(HUNDRED);
			}else {
				value = value.setScale(decimal, roundingMode);
			}
			// 不足两位小数补0
			DecimalFormat decimalFormat = getDecimalFormat(decimal);
			
			sb.append(decimalFormat.format(value));
			if (formatToPercentage) {
				sb.append("%");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 格式化输入，指定小数位，不足补0
	 * @param value
	 * @param decimal
	 * @return
	 */
	public static String formatToScale(BigDecimal value, int decimal) {
		return format(value, decimal, false, RoundingMode.HALF_UP);
	}
	
	
	/**
	 * BigDecimal转百分比格式，可指定百分比小数位，不足补0
	 * @param value	
	 * @param decimal	需要保留的百分比小数
	 * @return
	 */
	public static String formatToPercentage(BigDecimal value, int decimal) {
		return format(value, decimal, true, RoundingMode.HALF_UP);
	}
	
	
	
	private static DecimalFormat getDecimalFormat(int decimal) {
		DecimalFormat decimalFormat = null;
		if (decimal < 0) {
			decimalFormat = decimal_format_0;
		} else {
			switch (decimal) {
			case 0:
				decimalFormat = decimal_format_0;
				break;
			case 1:
				decimalFormat = decimal_format_1;
				break;
			case 2:
				decimalFormat = decimal_format_2;
				break;
			case 3:
				decimalFormat = decimal_format_3;
				break;
			case 4:
				decimalFormat = decimal_format_4;
				break;
			case 5:
				decimalFormat = decimal_format_5;
				break;
			case 6:
				decimalFormat = decimal_format_6;
				break;
			case 7:
				decimalFormat = decimal_format_7;
				break;
			case 8:
				decimalFormat = decimal_format_8;
				break;
			case 9:
				decimalFormat = decimal_format_9;
				break;
			default:
				StringBuilder sb = new StringBuilder("0.");
				for (int i = 0; i < decimal; i++) {
					sb.append(0);
				}
				decimalFormat = new DecimalFormat(sb.toString());
				break;
			}
		}
		return decimalFormat;
	}
	
}
