package com.jeemodel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateTimeUtilsTest  {
	public static void main(String[] args) {
		LocalTime localTime = LocalTime.of(3, 55,18);
		LocalDate localDate = LocalDate.of(1998, 3, 16);
		LocalDateTime localDateTime = LocalDateTime.of(2021, 12, 16,15,35,48);
		Date date = new Date();
		Instant instant = Instant.now();
		System.out.println("localTime="+localTime);
		System.out.println("localDate="+localDate);
		System.out.println("localDateTime="+localDateTime);
		System.out.println("date="+date);
		System.out.println("instant="+instant);
		System.out.println("===================================================");
		System.out.println("===================================================");
		System.out.println("===================================================");
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMM());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMM(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMMdd());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMMdd(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMMddBySlash());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMMddBySlash(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMMddHHmmss());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyyMMddHHmmss(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM(localDate));
		
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_dd());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_dd(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_dd_HH_mm_ss());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_dd_HH_mm_ss(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_dd_HH_mm_ss_SSS());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_dd_HH_mm_ss_SSS(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_ddTHH_mm_ss());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_ddTHH_mm_ss(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_ddTHH_mm_ss_SSS());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.yyyy_MM_ddTHH_mm_ss_SSS(localDateTime));
		
		
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.HH_mm());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.HH_mm(localTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.HH_mm_ss());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.HH_mm_ss(localTime));
		
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toDate(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toLocalDate(date));
		
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toDate(localDateTime));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toLocalDateTime(date));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toDate(instant));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toInstant(date));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toInstant(localDateTime));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.toInstant(localDate));
		
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.timeMillis(localDateTime));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.timeMillis(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.getYear(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.getMonthOfYear(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.getDayOfWeek(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatTime());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatTime("HH:mm:ss"));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatTime(localTime));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatTime(localTime,"HH:mm:ss"));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDate());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDate("yyyy-MM-dd"));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDate(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDate(localDate,"yyyy-MM-dd"));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDateTime());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDateTime("yyyy-MM-dd"));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDateTime(localDateTime));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.formatDateTime(localDateTime,"yyyy-MM-dd"));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.minDateTimeOfDay());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.minDateTimeOfDay(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.minDateTimeOfDay(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.maxDateTimeOfDay());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.maxDateTimeOfDay(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.maxDateTimeOfDay(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.minTime());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.minTime("HH:mm:ss"));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.maxTime());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.maxTime("HH:mm"));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.atStartOfDay());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.atStartOfDay(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.atStartOfDay(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.atEndOfDay());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.atEndOfDay(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.atEndOfDay(localDateTime));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfWeek());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfWeek(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.lastDayOfWeek());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.lastDayOfWeek(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfMonth());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfMonth(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfMonth());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfMonth(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfQuarter());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfQuarter(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfQuarter());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfQuarter(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfYear());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfYear(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfYear());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.firstDayOfYear(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.allDaysOfWeek(localDate));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.allDaysOfMonth(localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.systemZone());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.currentSecond());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.currentMilli());
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.isBirthday(LocalDate.now(),localDate ));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.isLeapYear(localDate ));
		
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.between(localDateTime,LocalDateTime.now(),ChronoUnit.NANOS ));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.getServerStartDate());
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.parseDate("2021-01-30"));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.parse("yyyy-MM-dd","2021-01-30"));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.parseDateToStr("yyyy-MM-dd",new Date()));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.betweenLocalDate(LocalDate.now(),localDate));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.betweenDate(new Date(date.getTime()- 155555512L),date));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.datePath(false));
		System.out.println(com.jeemodel.core.utils.DateTimeUtils.datePath(true));
		
		
		
		
		
		
		
		
		
		
		
	}
}
