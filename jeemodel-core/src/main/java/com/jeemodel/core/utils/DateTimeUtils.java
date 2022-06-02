package com.jeemodel.core.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeemodel.bean.exception.type.UtilsException;
import com.jeemodel.core.constant.time.TimeHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 日期时间工具类
 * @author Rootfive
 * Java 8 新API基于ISO标准日历系统.时间戳 (以unix元年 1970-01-01 00:00:00 到某个时间之间的毫秒值)
 *===================================================================
 *	java.time包下的所有类都是不可变类型而且线程安全。
 *-------------------------------------------------------------------
 *	类名				描述
 *	Instant			时间戳
 *	Duration		持续时间，时间差
 *	LocalDate		只包含日期，比如：2020-05-20
 *	LocalTime		只包含时间，比如：13:14:00
 *	LocalDateTime	包含日期和时间，比如：2020-05-20 13:14:00
 *	Period			时间段
 *	ZoneOffset		时区偏移量，比如：+8:00
 *	ZonedDateTime	带时区的时间
 *	Clock			时钟，比如获取目前美国纽约的时间
 *===================================================================
 *	java.time.format包
 *-------------------------------------------------------------------
 *	DateTimeFormatter	时间格式化
 *===================================================================
 *	将LocalDateTime字段以指定格式化日期的方式返回给前端 
 *	在LocalDateTime字段上添加@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=“yyyy-MM-dd HH:mm:ss”)注解即可，如下：
 *-------------------------------------------------------------------
 *		@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
 *		protected LocalDateTime createTime;
 *===================================================================
 *	对前端传入的日期进行格式化 
 *	在LocalDateTime字段上添加@DateTimeFormat(pattern = “yyyy-MM-dd HH:mm:ss”)注解即可，如下：
 *-------------------------------------------------------------------
 *		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 *		protected LocalDateTime createTime;
 *
 */
@Slf4j
public class DateTimeUtils extends org.apache.commons.lang3.time.DateUtils {

	/**
	 * 获取当前【年月，格式：yyyyMM，如202204】
	 * @return
	 */
	public static final String yyyyMM() {
		return TimeHelper.FTER_YYYYMM.format(LocalDate.now());
	}

	/**
	 * 获取指定LocalDate【年月，格式：yyyyMM，如202204】
	 * @return
	 */
	public static final String yyyyMM(LocalDate localDate) {
		return TimeHelper.FTER_YYYYMM.format(localDate);
	}

	/**
	 * 获取当前【年月日，格式：yyyyMMdd，如20220427】
	 * @return
	 */
	public static final String yyyyMMdd() {
		return TimeHelper.FTER_YYYYMMDD.format(LocalDate.now());
	}

	/**
	 * 获取指定LocalDate【年月日，格式：yyyyMMdd，如20220427】
	 * @return
	 */
	public static final String yyyyMMdd(LocalDate localDate) {
		return TimeHelper.FTER_YYYYMMDD.format(localDate);
	}

	/**
	 * 获取当前【年月日，格式：yyyy/MM/dd，如2022/04/27】
	 * @return
	 */
	public static final String yyyyMMddBySlash() {
		return TimeHelper.FTER_YYYYMMDD_BY_SLASH.format(LocalDate.now());
	}

	/**
	 * 获取指定LocalDate【年月日，格式：yyyy/MM/dd，如2022/04/27】
	 * @return
	 */
	public static final String yyyyMMddBySlash(LocalDate localDate) {
		return TimeHelper.FTER_YYYYMMDD_BY_SLASH.format(localDate);
	}

	/**
	 * 获取当前【年月日时分秒，格式：yyyyMMddHHmmss，如20220427174238】
	 * @return
	 */
	public static final String yyyyMMddHHmmss() {
		return TimeHelper.FTER_YYYYMMDDHHMMSS.format(LocalDateTime.now());
	}

	/**
	 * 获取指定LocalDate【年月日时分秒，格式：yyyyMMddHHmmss，如20220427174238】
	 * @return
	 */
	public static final String yyyyMMddHHmmss(LocalDateTime localDateTime) {
		return TimeHelper.FTER_YYYYMMDDHHMMSS.format(localDateTime);
	}

	/**
	 * 获取当前【年月，格式：yyyy-MM，如2022-04】
	 * @return
	 */
	public static final String yyyy_MM() {
		return TimeHelper.FTER_YYYY_MM.format(LocalDate.now());
	}

	/**
	 * 获取指定LocalDate【年月，格式：yyyy-MM，如2022-04】
	 * @return
	 */
	public static final String yyyy_MM(LocalDate localDate) {
		return TimeHelper.FTER_YYYY_MM.format(localDate);
	}

	/**
	 * 获取当前Date【年月日，格式：yyyy-MM-dd，如2022-04-27】
	 * @return
	 */
	public static final String yyyy_MM_dd() {
		return TimeHelper.DATE_FORMATTER.format(LocalDate.now());
	}

	/**
	 * 获取指定【年月日，格式：yyyy-MM-dd，如2022-04-27】
	 * @return
	 */
	public static final String yyyy_MM_dd(LocalDate localDate) {
		return TimeHelper.DATE_FORMATTER.format(localDate);
	}

	/**
	 * 获取当前DateTime【年月日时分秒，格式：yyyy-MM-dd HH:mm:ss，如2022-04-27 17:45:28】
	 * @return
	 */
	public static final String yyyy_MM_dd_HH_mm_ss() {
		return TimeHelper.DATETIME_FORMATTER.format(LocalDateTime.now());
	}

	/**
	 * 获取指定LocalDateTime【年月日时分秒，格式：yyyy-MM-dd HH:mm:ss，如2022-04-27 17:45:28】
	 * @return
	 */
	public static final String yyyy_MM_dd_HH_mm_ss(LocalDateTime localDateTime) {
		return TimeHelper.DATETIME_FORMATTER.format(localDateTime);
	}

	/**
	 * 获取当前DateTime【年月日时分秒，格式：yyyy-MM-dd HH:mm:ss.SSS，如2022-04-27 17:45:28.368】
	 * @return
	 */
	public static final String yyyy_MM_dd_HH_mm_ss_SSS() {
		return TimeHelper.FTER_YYYY_MM_DD_HH_MM_SS_SSS.format(LocalDateTime.now());
	}

	/**
	 * 获取指定LocalDateTime【年月日时分秒，格式：yyyy-MM-dd HH:mm:ss.SSS，如2022-04-27 17:45:28.368】
	 * @return
	 */
	public static final String yyyy_MM_dd_HH_mm_ss_SSS(LocalDateTime localDateTime) {
		return TimeHelper.FTER_YYYY_MM_DD_HH_MM_SS_SSS.format(localDateTime);
	}

	/**
	 * 获取当前DateTime【年月日时分秒，格式：yyyy-MM-dd'T'HH:mm:ss，如2022-04-27T17:45:28】
	 * @return
	 */
	public static final String yyyy_MM_ddTHH_mm_ss() {
		return TimeHelper.FTER_YYYY_MM_DD_T_HH_MM_SS.format(LocalDateTime.now());
	}

	/**
	 * 获取指定LocalDateTime【年月日时分秒，格式：yyyy-MM-dd'T'HH:mm:ss，如2022-04-27T17:45:28】
	 * @return
	 */
	public static final String yyyy_MM_ddTHH_mm_ss(LocalDateTime localDateTime) {
		return TimeHelper.FTER_YYYY_MM_DD_T_HH_MM_SS.format(localDateTime);
	}

	/**
	 * 获取当前DateTime【年月日时分秒，格式：yyyy-MM-dd'T'HH:mm:ss.SSS，如2022-04-27T17:45:28.258】
	 * @return
	 */
	public static final String yyyy_MM_ddTHH_mm_ss_SSS() {
		return TimeHelper.FTER_YYYY_MM_DD_T_HH_MM_SS_SSS.format(LocalDateTime.now());
	}

	/**
	 * 获取指定LocalDateTime【年月日时分秒，格式：yyyy-MM-dd'T'HH:mm:ss.SSS，如2022-04-27T17:45:28.258】
	 * @return
	 */
	public static final String yyyy_MM_ddTHH_mm_ss_SSS(LocalDateTime localDateTime) {
		return TimeHelper.FTER_YYYY_MM_DD_T_HH_MM_SS_SSS.format(localDateTime);
	}

	/**
	 * 获取当前【时分，格式：HH:mm，如17:38】
	 * @return
	 */
	public static final String HH_mm() {
		return TimeHelper.FTER_HH_MM.format(LocalTime.now());
	}

	/**
	 * 获取指定LocalTime【时分，格式：HH:mm，如17:38】
	 * @return
	 */
	public static final String HH_mm(LocalTime localTime) {
		return TimeHelper.FTER_HH_MM.format(localTime);
	}

	/**
	 * 获取当前Time【时分秒，格式：HH:mm:ss，如17:38:28】
	 * @return
	 */
	public static final String HH_mm_ss() {
		return TimeHelper.TIME_FORMATTER.format(LocalTime.now());
	}

	/**
	 * 获取指定LocalTime【时分秒，格式：HH:mm:ss，如17:38:28】
	 * @return
	 */
	public static final String HH_mm_ss(LocalTime localTime) {
		return TimeHelper.TIME_FORMATTER.format(localTime);
	}

	// XXX 以上是常用
	// ------------------------------------------
	// ------------------------------------------
	// XXX 以下是Java8+时间

	/**
	 * LocalDate -> Date
	 * @param localDate
	 * @return
	 */
	public static Date toDate(final LocalDate localDate) {
		LocalDateTime atStartOfDay = localDate.atStartOfDay();
		return toDate(atStartOfDay);
	}

	/**
	 * Date -> LocalDate
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(final Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * LocalDateTime -> Date
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(final LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		return toDate(instant);
	}

	/**
	 * Date -> LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(final Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
	
	/**
	 * 时间戳转LocalDateTime
	 * long -> LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(final long timeMillis) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.systemDefault());
	}
	
	/**
	 * Instant（时间戳）-> Date
	 * @param instant
	 * @return
	 */
	public static Date toDate(final Instant instant) {
		return Date.from(instant);
	}

	/**
	 * Date -> Instant（时间戳）
	 * @param date
	 * @return
	 */
	public static Instant toInstant(final Date date) {
		return date.toInstant();
	}

	/**
	 * LocalDateTime -> Instant（时间戳）
	 * @param localDateTime
	 * @return
	 */
	public static Instant toInstant(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
	}

	/**
	 * LocalDate -> Instant（时间戳）
	 * @param localDate
	 * @return
	 */
	public static Instant toInstant(LocalDate localDate) {
		return toInstant(localDate.atStartOfDay());
	}

	/**
	 * LocalDateTime -> 时间戳
	 * @param localDateTime
	 * @return
	 */
	public static long timeMillis(final LocalDateTime localDateTime) {
		return toInstant(localDateTime).toEpochMilli();
	}

	/**
	 * LocalDate -> 时间戳
	 * @param localDate
	 * @return
	 */
	public static long timeMillis(final LocalDate localDate) {
		return toInstant(localDate).toEpochMilli();
	}

	/**
	 * 获取年
	 * @param localDate
	 * @return
	 */
	public static int getYear(final LocalDate localDate) {
		return localDate.get(ChronoField.YEAR);
	}

	/**
	 * 获取月
	 * @param localDate
	 * @return
	 */
	public static int getMonthOfYear(final LocalDate localDate) {
		return localDate.get(ChronoField.MONTH_OF_YEAR);
	}

	/**
	 * 获取星期几
	 * @param localDate
	 * @return
	 */
	public static int getDayOfWeek(final LocalDate localDate) {
		return localDate.get(ChronoField.DAY_OF_WEEK);
	}


	/**
	 * Time格式化，默认当前LocalTime、默认格式：HH:mm:ss
	 * @param localTime
	 * @return
	 */
	public static String formatTime() {
		return formatTime(LocalTime.now());
	}

	/**
	 * Time格式化，默认当前LocalTime、指定格式
	 * @param format
	 * @return
	 */
	public static String formatTime(final String format) {
		return formatTime(LocalTime.now(), format);
	}

	/**
	 * Time格式化，指定LocalTime、默认格式：HH:mm:ss
	 * @param localTime
	 * @return
	 */
	public static String formatTime(final LocalTime localTime) {
		return TimeHelper.TIME_FORMATTER.format(localTime);
	}

	/**
	 * Time格式化：指定LocalTime、指定格式
	 * @param localTime
	 * @param format
	 * @return
	 */
	public static String formatTime(final LocalTime localTime, final String format) {
		return DateTimeFormatter.ofPattern(format).format(localTime);
	}


	/**
	 * Date格式化，默认当前LocalDate、默认格式：yyyy-MM-dd
	 * @param localDate
	 * @return
	 */
	public static String formatDate() {
		return formatDate(LocalDate.now());
	}

	/**
	 * Date格式化，默认当前LocalDate、指定格式
	 * @param format
	 * @return
	 */
	public static String formatDate(final String format) {
		return formatDate(LocalDate.now(), format);
	}

	/**
	 * Date格式化，指定LocalDate、默认格式：yyyy-MM-dd
	 * @param localDate
	 * @return
	 */
	public static String formatDate(final LocalDate localDate) {
		return TimeHelper.DATE_FORMATTER.format(localDate);
	}

	/**
	 * Date格式化：指定LocalDate、指定格式
	 * @param localDate
	 * @param format
	 * @return
	 */
	public static String formatDate(final LocalDate localDate, final String format) {
		return DateTimeFormatter.ofPattern(format).format(localDate);
	}


	/**
	 * DateTime格式化，默认当前LocalDateTime、默认格式：yyyy-MM-dd HH:mm:ss
	 * @param localDateTime
	 * @return
	 */
	public static String formatDateTime() {
		return formatDateTime(LocalDateTime.now());
	}

	/**
	 * DateTime格式化，默认当前LocalDateTime、指定格式
	 * @param format
	 * @return
	 */
	public static String formatDateTime(final String format) {
		return formatDateTime(LocalDateTime.now(), format);
	}

	/**
	 * DateTime格式化，指定LocalDateTime、默认格式：yyyy-MM-dd HH:mm:ss
	 * @param localDateTime
	 * @return
	 */
	public static String formatDateTime(final LocalDateTime localDateTime) {
		return TimeHelper.DATETIME_FORMATTER.format(localDateTime);
	}

	/**
	 * DateTime格式化，指定LocalDateTime、指定格式
	 * @param localDateTime
	 * @param format
	 * @return
	 */
	public static String formatDateTime(final LocalDateTime localDateTime, final String format) {
		return DateTimeFormatter.ofPattern(format).format(localDateTime);
	}


	/**
	 * 今天最小DateTime,格式:yyyy-MM-dd 00:00:00
	 *
	 * @param localDate 本地日期
	 * @return {@link String}
	 */
	public static String minDateTimeOfDay() {
		return minDateTimeOfDay(LocalDate.now());
	}

	/**
	 * 一天最小DateTime,格式:yyyy-MM-dd 00:00:00
	 *
	 * @param localDate 本地日期
	 * @return {@link String}
	 */
	public static String minDateTimeOfDay(final LocalDate localDate) {
		return formatDateTime(localDate.atStartOfDay());
	}

	/**
	 * 一天最小DateTime,格式:yyyy-MM-dd 00:00:00
	 *
	 * @param localDateTime 本地日期时间
	 * @return {@link String}
	 */
	public static String minDateTimeOfDay(final LocalDateTime localDateTime) {
		LocalDateTime atStartOfDay = atStartOfDay(localDateTime);
		return formatDateTime(atStartOfDay);
	}

	/**
	 * 今天最大DateTime,格式:yyyy-MM-dd 23:59:59
	 *
	 * @param localDate 本地日期
	 * @return {@link String}
	 */
	public static String maxDateTimeOfDay() {
		return maxDateTimeOfDay(LocalDate.now());
	}

	/**
	 * 一天最大DateTime,格式:yyyy-MM-dd 23:59:59
	 *
	 * @param localDate 本地日期
	 * @return {@link String}
	 */
	public static String maxDateTimeOfDay(final LocalDate localDate) {
		LocalDateTime atEndOfDay = atEndOfDay(localDate);
		return formatDateTime(atEndOfDay);
	}

	/**
	 * 一天最大DateTime,格式:yyyy-MM-dd 23:59:59
	 *
	 * @param localDateTime 本地日期时间
	 * @return {@link String}
	 */
	public static String maxDateTimeOfDay(final LocalDateTime localDateTime) {
		LocalDateTime atEndOfDay = atEndOfDay(localDateTime);
		return formatDateTime(atEndOfDay);
	}


	/**
	 * 获取当天最大Time 00:00:00
	 * @return
	 */
	public static final String minTime() {
		return formatTime(LocalTime.MIN);
	}

	/**
	 * 获取当天最大Time：指定格式
	 * @param format
	 * @return
	 */
	public static final String minTime(final String format) {
		return formatTime(LocalTime.MIN, format);
	}

	/**
	 * 获取当天最大Time 23:59:59
	 * @return
	 */
	public static final String maxTime() {
		return formatTime(LocalTime.MAX);
	}

	/**
	 * 获取当天最大Time：指定格式
	 * @param format
	 * @return
	 */
	public static final String maxTime(final String format) {
		return formatTime(LocalTime.MAX, format);
	}


	/**
	 * 今天开始的LocalDateTime
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime atStartOfDay() {
		return atStartOfDay(LocalDate.now());
	}

	/**
	 * 一天开始的LocalDateTime
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime atStartOfDay(final LocalDate localDate) {
		return LocalDateTime.of(localDate, LocalTime.MIN);
	}

	/**
	 * 一天开始的LocalDateTime
	 *
	 * @param localDateTime 本地日期
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime atStartOfDay(final LocalDateTime localDateTime) {
		return atStartOfDay(localDateTime.toLocalDate());
	}

	/**
	 * 今天结束的LocalDateTime
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime atEndOfDay() {
		return atEndOfDay(LocalDate.now());
	}

	/**
	 * 一天结束的LocalDateTime
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime atEndOfDay(final LocalDate localDate) {
		return LocalDateTime.of(localDate, LocalTime.MAX);
	}

	/**
	 * 一天结束的LocalDateTime
	 *
	 * @param LocalDateTime 本地日期时间
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime atEndOfDay(final LocalDateTime localDateTime) {
		return atEndOfDay(localDateTime.toLocalDate());
	}

	/**
	 * 本周的第一天(周一)
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfWeek() {
		return firstDayOfWeek(LocalDate.now());
	}

	/**
	 * 一周的第一天(周一)
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfWeek(final LocalDate localDate) {
		return localDate.with(DayOfWeek.MONDAY);
	}

	/**
	 * 本周的最后一天(周日)
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfWeek() {
		return lastDayOfWeek(LocalDate.now());
	}

	/**
	 * 一周的最后一天(周日)
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfWeek(final LocalDate localDate) {
		return localDate.with(DayOfWeek.SUNDAY);
	}

	/**
	 * 本月的第一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfMonth() {
		return firstDayOfMonth(LocalDate.now());
	}

	/**
	 * 月的第一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfMonth(final LocalDate localDate) {
		return localDate.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 本月的最后一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfMonth() {
		return lastDayOfMonth(LocalDate.now());
	}

	/**
	 * 月的最后一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfMonth(final LocalDate localDate) {
		return localDate.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 本季度的第一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfQuarter() {
		return firstDayOfQuarter(LocalDate.now());
	}

	/**
	 * 季度的第一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfQuarter(final LocalDate localDate) {
		Month firstMonthOfQuarter = localDate.getMonth().firstMonthOfQuarter();
		return LocalDate.of(localDate.getYear(), firstMonthOfQuarter, 1);
	}

	/**
	 * 本季度最后一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfQuarter() {
		return lastDayOfQuarter(LocalDate.now());
	}

	/**
	 * 季度最后一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfQuarter(final LocalDate localDate) {
		Month lastMonthOfQuarter = localDate.getMonth().firstMonthOfQuarter().plus(2);
		return LocalDate.of(localDate.getYear(), lastMonthOfQuarter, lastMonthOfQuarter.length(localDate.isLeapYear()));
	}

	/**
	 * 本年的第一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfYear() {
		return firstDayOfYear(LocalDate.now());
	}

	/**
	 * 每年的第一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate firstDayOfYear(final LocalDate localDate) {
		return localDate.with(TemporalAdjusters.firstDayOfYear());
	}

	/**
	 * 本年的最后一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfYear() {
		return lastDayOfYear(LocalDate.now());
	}

	/**
	 * 每年的最后一天
	 *
	 * @param localDate 本地日期
	 * @return {@link LocalDate}
	 */
	public static LocalDate lastDayOfYear(final LocalDate localDate) {
		return localDate.with(TemporalAdjusters.lastDayOfYear());
	}

	/**
	 * 每周的所有日期  即周一到周日
	 *
	 * @param localDate 本地日期
	 * @return {@link List<LocalDate>}
	 */
	public static List<LocalDate> allDaysOfWeek(final LocalDate localDate) {
		List<LocalDate> allDays = new ArrayList<>();
		allDays.add(localDate.with(DayOfWeek.MONDAY));
		allDays.add(localDate.with(DayOfWeek.TUESDAY));
		allDays.add(localDate.with(DayOfWeek.WEDNESDAY));
		allDays.add(localDate.with(DayOfWeek.THURSDAY));
		allDays.add(localDate.with(DayOfWeek.FRIDAY));
		allDays.add(localDate.with(DayOfWeek.SATURDAY));
		allDays.add(localDate.with(DayOfWeek.SUNDAY));
		return allDays;
	}

	/**
	 * 每月的所有日期  即1日到31日
	 *
	 * @param localDate 本地日期
	 * @return {@link List<LocalDate>}
	 */
	public static List<LocalDate> allDaysOfMonth(final LocalDate localDate) {
		List<LocalDate> allDays = new ArrayList<>();
		LocalDate firstDayOfMonth = firstDayOfMonth(localDate);
		LocalDate lastDayOfMonth = lastDayOfMonth(localDate);
		allDays.add(firstDayOfMonth);
		int i = 1;
		LocalDate temp = firstDayOfMonth;
		while (!temp.isEqual(lastDayOfMonth)) {
			LocalDate day = firstDayOfMonth.plusDays(i);
			allDays.add(day);
			temp = day;
			i++;
		}
		return allDays;
	}

	/**
	 * 当前系统的时区
	 * @return
	 */
	public static ZoneId systemZone() {
		return ZoneId.systemDefault();
	}

	/**
	 * 当前秒数
	 * @return
	 */
	public static long currentSecond() {
		return Instant.now().getEpochSecond();
	}

	/**
	 * 当前毫秒数
	 * @return
	 */
	public static long currentMilli() {
		return Instant.now().toEpochMilli();
	}

	/**
	 * 判断是不是生日
	 * @param dateOfBirth 出生日期
	 * @param localDate   需要判断的日期
	 * @return
	 */
	public static boolean isBirthday(final LocalDate dateOfBirth, final LocalDate localDate) {
		MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
		MonthDay currentMonthDay = MonthDay.from(localDate);
		return currentMonthDay.equals(birthday);
	}

	/**
	 * 是否是闰年
	 * @param localDate
	 * @param needCompare
	 * @return
	 */
	public static boolean isLeapYear(final LocalDate localDate) {
		return localDate.isLeapYear();
	}

	/**
	 * 计算两个时间之间的间隔：指定间隔单位
	 * @param startInclusive 开始时间
	 * @param endExclusive 结束时间
	 * @param timeUnit 时间单位：日，时，分，秒，毫秒，纳秒
	 * @return
	 */
	public static long between(final LocalDateTime startInclusive, final LocalDateTime endExclusive, final ChronoUnit timeUnit) {
		Duration between = Duration.between(startInclusive, endExclusive);
		long betweenLong = 0;
		switch (timeUnit) {
		case DAYS:
			betweenLong = between.toDays();
			break;
		case HOURS:
			betweenLong = between.toHours();
			break;
		case MINUTES:
			betweenLong = between.toMinutes();
			break;
		case SECONDS:
			betweenLong = between.toMinutes() * 60L;
			break;
		case MILLIS:
			betweenLong = between.toMillis();
			break;
		case NANOS:
			betweenLong = between.toNanos();
			break;
		default:
			throw new UtilsException("时间单位不支持");
		}
		return betweenLong;
	}

	// XXX 以上是Java8+时间
	// ------------------------------------------
	// ------------------------------------------
	// XXX 以下是Java8-时间 java.util.Date

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(final Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), TimeHelper.parsePatterns);
		} catch (ParseException e) {
			log.warn("Date反序列化异常,ParseException:", e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 解析时间
	 * @param format
	 * @param ts
	 * @return
	 */
	public static final Date parse(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			log.warn("Date反序列化异常,ParseException:", e.getMessage(), e);
			throw new UtilsException("Date反序列化异常");
		}
	}

	/**
	 * 序列化时间
	 * @param format
	 * @param date
	 * @return
	 */
	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * @return 当前时间
	 */
	public static final Date nowDate() {
		return new Date();
	}

	// XXX 以上是Java8-时间 java.util.Date
	// ------------------------------------------
	// ------------------------------------------
	// XXX 以下是自定义

	/**
	 * 计算两个日期之间的间隔，年数、天数、月数
	 * @param localDate
	 * @param otherLocalDate
	 * @return
	 */
	public static String betweenLocalDate(final LocalDate localDate, final LocalDate otherLocalDate) {
		Period period = Period.between(localDate, otherLocalDate);

		// 相差 年、月、日时间
		int years = period.getYears();
		int months = period.getMonths();
		int days = period.getDays();

		StringBuilder sb = new StringBuilder();
		if (years != 0) {
			sb.append(years).append("年");
		}

		if (months != 0) {
			sb.append(months).append("个月");
		}

		if (days != 0) {
			sb.append(days).append("天");
		}
		return sb.toString();
	}

	/**
	 * 计算两个时间差
	 */
	public static String betweenDate(final Date endDate, final Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		 long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		
		StringBuilder sb = new StringBuilder();
		if (day > 0) {
			sb.append(day).append("天");
		}
		if (hour > 0) {
			sb.append(hour).append("小时");
		}
		if (min > 0) {
			sb.append(min).append("分钟");
		}
		if (sec > 0) {
			sb.append(sec).append("秒");
		}
		return sb.toString();
	}

	/**
	 * 日期路径 即年月日
	 * @param isWithSlash 是否带斜杠
	 * @return 【2018/08/08】或【20180808】
	 */
	public static final String datePath(final boolean isWithSlash) {
		if (isWithSlash) {
			return yyyyMMddBySlash();
		} else {
			return yyyyMMdd();
		}
	}

}
