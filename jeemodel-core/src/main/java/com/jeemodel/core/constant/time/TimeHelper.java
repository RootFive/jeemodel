package com.jeemodel.core.constant.time;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * 请求方式
 *
 * @author Rootfive
 */
public interface TimeHelper {


	/**  1小时与 毫秒 换算： 1s = 1000ms  */
	public static final long PER_SECOND_WITH_MILLISECOND = 1000L;

	/**  1分钟与 毫秒 换算： 1m= 60 * 1000ms =  60000ms */
	public static final long PER_MINUTE_WITH_MILLISECOND = 60 * 1000L;

	/**  1小时与 毫秒 换算： 1h = 60 * 60 * 1000ms =  3600000ms   */
	public static final long PER_HOUR_WITH_MILLISECOND = 60 * 60 * 1000L;

	/**  1天与 毫秒 换算： 1d = 24 * 60 * 60 * 1000ms =  86400000ms   */
	public static final long PER_DAY_WITH_MILLISECOND = 24 * 60 * 60 * 1000L;
	
	
	/** Time格式化字符串 */
	public static final String TIME_FORMAT = "HH:mm:ss";

	/** Date格式化字符串 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/** DateTime格式化字符串 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	
	
	public static final String F_YYYY_MM = "yyyy-MM";
	
	public static final String F_YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static final String F_YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	public static final String F_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static final String F_YYYYMM = "yyyyMM";

	public static final String F_YYYYMMDD = "yyyyMMdd";
	
	public static final String F_YYYYMMDD_BY_SLASH = "yyyy/MM/dd";
	
	public static final String F_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String F_HH_MM = "HH:mm";

	
	public static final String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/** Time格式化：HH:mm:ss */
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
	
	/** Date格式化：yyyy-MM-dd */
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
	/** DateTime格式化：yyyy-MM-dd HH:mm:ss */
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
	
	
	
	/** Date格式化：yyyy-MM */
	public static final DateTimeFormatter FTER_YYYY_MM = DateTimeFormatter.ofPattern(F_YYYY_MM);
	
	/** Date格式化：yyyyMM */
	public static final DateTimeFormatter FTER_YYYYMM = DateTimeFormatter.ofPattern(F_YYYYMM);
	
	/** Date格式化：YYYYMMDD */
	public static final DateTimeFormatter FTER_YYYYMMDD = DateTimeFormatter.ofPattern(F_YYYYMMDD);
	
	/** Date格式化：yyyy/MM/dd */
	public static final DateTimeFormatter FTER_YYYYMMDD_BY_SLASH = DateTimeFormatter.ofPattern(F_YYYYMMDD_BY_SLASH);
	
	/** DateTime格式化：YYYYMMDDHHMMSS */
	public static final DateTimeFormatter FTER_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern(F_YYYYMMDDHHMMSS);
	
	/** DateTime格式化：yyyy-MM-dd'T'HH:mm:ss */
	public static final DateTimeFormatter FTER_YYYY_MM_DD_T_HH_MM_SS = DateTimeFormatter.ofPattern(F_YYYY_MM_DD_T_HH_MM_SS);
	
	/** DateTime格式化：yyyy-MM-dd'T'HH:mm:ss.SSS */
	public static final DateTimeFormatter FTER_YYYY_MM_DD_T_HH_MM_SS_SSS = DateTimeFormatter.ofPattern(F_YYYY_MM_DD_T_HH_MM_SS_SSS);
	
	/** DateTime格式化：yyyy-MM-dd HH:mm:ss.SSS */
	public static final DateTimeFormatter FTER_YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormatter.ofPattern(F_YYYY_MM_DD_HH_MM_SS_SSS);

	/** Time格式化：HH:mm */
	public static final DateTimeFormatter FTER_HH_MM = DateTimeFormatter.ofPattern(F_HH_MM);
	
}
