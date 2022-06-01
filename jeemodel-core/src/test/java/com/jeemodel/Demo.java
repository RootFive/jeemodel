package com.jeemodel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;

public class Demo  {
	public static void main(String[] args) {
		LocalDate localDate = LocalDate.now();
		
		// 获取年
		int year = localDate.getYear();
		System.out.println(year);
		int year1 = localDate.get(ChronoField.YEAR);
		System.out.println(year1);

		// 获取月
		Month month = localDate.getMonth();
		System.out.println(month);
		int month1 = localDate.get(ChronoField.MONTH_OF_YEAR);
		System.out.println(month1);

		// 获取日
		int day = localDate.getDayOfMonth();
		System.out.println(day);
		int day1 = localDate.get(ChronoField.DAY_OF_MONTH);
		System.out.println(day1);

		// 获取星期几
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		System.out.println(dayOfWeek);
		int dayOfWeek1 = localDate.get(ChronoField.DAY_OF_WEEK);
		System.out.println(dayOfWeek1);
	}
}
