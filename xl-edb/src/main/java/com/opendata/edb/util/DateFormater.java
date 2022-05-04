package com.opendata.edb.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * 格式化日期，从字符串产生日期，从日期产生字符串
 */
public class DateFormater {
	/**
	 * * 将日期转化为字符串。 字符串格式("YYYY-MM-DD")，小时、分、秒被忽略。
	 */
	public static String DateToString(Date date) {
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String strDateTime = "";
		if (date != null) {
			strDateTime = formater.format(date);
		}
		return strDateTime;
	}

	/**
	 * * 将日期转化为字符串。
	 * 
	 * @param Date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return String 类型
	 */
	public static String DateToString(Date date, String pattern) {
		String strDateTime = null;
		try {
			java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(pattern);
			strDateTime = formater.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strDateTime;
	}

	/**
	 * 将传入的年月日转化为Date类型
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return Date类型
	 */
	public static Date YmdToDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/**
	 * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
	 * 例如："2002-07-01"或者"2002-7-1"或者"2002-7-01"或者"2002-07-1"是等价的。
	 */
	public static Date StringToDate(String str) {
		Date dateTime = null;
		try {
			if (!(str == null || str.equals(""))) {
				java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd");
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dateTime;

	}

	/**
	 * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
	 * 例如："2002-07-01"或者"2002-7-1"或者"2002-7-01"或者"2002-07-1"是等价的。
	 */
	public static Date StringToDate(String str, String pattern) {
		Date dateTime = null;
		try {
			if (!(str == null || str.equals(""))) {
				java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(pattern);
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dateTime;

	}

	/**
	 * 日期时间带时分秒的Timestamp表示
	 * 
	 * @return Timestamp
	 */
	public static Timestamp StringToDateHMS(String str) throws Exception {
		Timestamp time = null;
		try {
			time = java.sql.Timestamp.valueOf(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return time;

	}

	/**
	 * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
	 * 
	 * @param date
	 *            一个日期
	 * @return Date对象。
	 */
	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
	 * 
	 * @param date
	 *            一个日期
	 * @return Date对象。
	 */
	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 判断两个时间是否在同一周
	 * 
	 * @param date1
	 *            �?始时�?
	 * @param date2
	 *            结束时间
	 * @return 返回结果，false为不是同�?星期时间，true为是同一个星期时�?
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的�?后一周横跨来年第�?周的话则�?后一周即算做来年的第�?�?
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 取得两个日期之间的差�?
	 * 
	 * @author naxj
	 * @param String
	 *            差�?�的类型
	 * @param date
	 *            第一个日�?
	 * @param date
	 *            第二个日�?
	 * @return long 两个日期之间的差值，类型由第�?个String参数指定
	 */
	public static long dateDiff(String style, Date fromdate, Date todate) {
		byte byte0;
		byte byte1 = 0;

		int i = 1;
		Date date2;
		Date date3;
		if (fromdate.getTime() > todate.getTime()) {
			i = -1;
			date2 = todate;
			date3 = fromdate;
		} else {
			date2 = fromdate;
			date3 = todate;
		}
		if (style.equals("yyyy"))
			byte0 = 1;
		else if (style.equals("m"))
			byte0 = 2;
		else if (style.equals("d"))
			byte0 = 5;
		else if (style.equals("y"))
			byte0 = 5;
		else if (style.equals("w"))
			byte0 = 4;
		else if (style.equals("ww"))
			byte0 = 3;
		else if (style.equals("h")) {
			byte0 = 5;
			byte1 = 11;
		} else if (style.equals("n")) {
			byte0 = 5;
			byte1 = 12;
		} else if (style.equals("s")) {
			byte0 = 5;
			byte1 = 13;
		} else {
			return -1;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		long l = 0;
		calendar.add(byte0, 1);
		for (Date date4 = calendar.getTime(); date4.getTime() <= date3.getTime();) {
			calendar.add(byte0, 1);
			date4 = calendar.getTime();
			l++;
		}

		if (byte1 == 11 || byte1 == 12 || byte1 == 13) {
			calendar.setTime(date2);
			calendar.add(byte0, (int) l);
			switch (byte1) {
			case 11:
				l *= 24;
				break;

			case 12:
				l = l * 24 * 60;
				break;

			case 13:
				l = l * 24 * 60 * 60;
				break;
			}
			calendar.add(byte1, 1);
			for (Date date6 = calendar.getTime(); date6.getTime() <= date3.getTime();) {
				calendar.add(byte1, 1);
				date6 = calendar.getTime();
				l++;
			}

		}
		return l * i;
	}

	/**
	 * 分钟�?
	 * 
	 * @param fromdate
	 * @param todate
	 * @return
	 */
	public static double hourDiff(Date fromdate, Date todate) {
		Date date2;
		Date date3;
		if (fromdate.getTime() > todate.getTime()) {
			date2 = todate;
			date3 = fromdate;
		} else {
			date2 = fromdate;
			date3 = todate;
		}
		double diffLong = date3.getTime() - date2.getTime();
		double diffDouble = diffLong / 1000 / 60 / 60;
		return diffDouble;
	}

	/**
	 * 日期+小时
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date dateTimeAdd(Date date, double hour) {
		double tempDouble = date.getTime() + hour * 60 * 60 * 1000;
		long tempLong = Math.round(tempDouble);
		return new Date(tempLong);
	}

}