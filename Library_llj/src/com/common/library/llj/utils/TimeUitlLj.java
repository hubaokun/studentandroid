package com.common.library.llj.utils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;

/**
 * 日期转换工具类milliseconds->date->str->date->milliseconds;date->calendar
 * 
 * @author llj 2014/7/12
 * 
 */
public class TimeUitlLj {
	/**
	 * 1.Date转换为Calendar
	 * 
	 * @param date
	 * @return calendar
	 */
	public static final Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 2.将当前Date转换成指定格式的字符串
	 * 
	 * @param format
	 *            1："yyyy年MM月dd日 HH时mm分ss秒" 2:"yyyy-MM-dd HH:mm:ss" 3:"yyyy/MM/dd HH:mm:ss" 4:"yyyy年MM月dd日 HH时mm分ss秒 E " 5:"yyyy/MM/dd E" 6."yyyy/MM/dd"
	 * @return dateStr 指定格式字符串
	 */
	public static final String getCurrentTimeString(int format) {
		return dateToString(format, new Date());
	}

	/**
	 * 3.获取指定时间与当前时间毫秒数差的绝对值
	 * 
	 * @param milliseconds
	 *            指定毫秒数
	 * @return 毫秒数绝对值,是毫秒，不是秒
	 */
	public static final Long getDifferMilliseconds(long milliseconds) {
		Long currentMilliseconds = new Date().getTime();
		Long differMilliseconds = milliseconds - currentMilliseconds;
		return Math.abs(differMilliseconds);
	}

	/**
	 * 4.获取返回时间于当前时间对比后的值
	 * 
	 * @param date
	 *            获取的时间
	 * @return 返回时间于当前时间对比后的值
	 */
	public static final String getTimeDiff(Date date) {
		Calendar currentDate = Calendar.getInstance();// 获取当前时间
		String year = currentDate.get(Calendar.YEAR) + "";// 获取当前年份
		long diff = currentDate.getTimeInMillis() - date.getTime();
		if (diff < 0)
			return 0 + "秒钟前";
		else if (diff < 60000)
			return diff / 1000 + "秒钟前";
		else if (diff < 3600000)
			return diff / 60000 + "分钟前";
		else if (diff < 86400000)
			return diff / 3600000 + "小时前";
		else {
			String newdate = DateFormat.format("yyyy-MM-dd kk:mm", date).toString();
			if (newdate.contains(year)) {
				return newdate.substring(5);
			} else {
				return newdate;
			}
		}
	}

	/**
	 * 5. 显示秒前，分前，小时前，天前，周前，一个月前，二个月前，三个月前，或者（自定义格式的年月日时分秒）
	 * 
	 * @param context
	 * @param milliseconds
	 * @param format
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat")
	public static String getTimeString(Long milliseconds, int format) {
		Date afterDate = new Date();
		long beforeTime = milliseconds;
		Date beforeDate = new Date(beforeTime);
		if (afterDate.getYear() == beforeDate.getYear()) {
			int month = afterDate.getMonth() - beforeDate.getMonth();
			long day=(afterDate.getTime() - beforeTime)/ (1000 * 60 * 60 * 24);
			if(day>7){
				return millisecondsToString(format, milliseconds);
			}else{
				return getDateString(afterDate.getTime() - beforeTime);
			}
			
			
//			if (month > 3 || month < 0) {
//				// 三个月后就显示年月日，时分秒
//				return millisecondsToString(format, milliseconds);
//			} else {
//				int day = afterDate.getDate() - beforeDate.getDate();
//				if (month == 3) {
//					if (day >= 0) {
//						return "三个月前";
//					} else {
//						return "二个月前";
//					}
//				} else if (month == 2) {
//					if (day >= 0) {
//						return "二个月前";
//					} else {
//						return "一个月前";
//					}
//				} else if (month == 1) {
//					if (day >= 0) {
//						return "一个月前";
//					} else {
//						return getDateString(afterDate.getTime() - beforeTime);
//					}
//				} else if (month == 0) {
//					return getDateString(afterDate.getTime() - beforeTime);
//				}
//			}
		}
		return millisecondsToString(format, milliseconds);
	}

	private static String getDateString(long time) {
		long days = time / (1000 * 60 * 60 * 24);
		if (days >= 7) {
			return days / 7 + "周前";
		} else {
			if (days > 0) {
				return days + "天前";
			} else {
				long hours = time / (1000 * 60 * 60);
				if (hours > 0) {
					return hours + "小时前";
				} else {
					long minutes = time / (1000 * 60);
					if (minutes > 0) {
						return minutes + "分钟前";
					} else {
						long seconds = time / 1000 - minutes * 60;
						if (seconds <= 0) {
							seconds = 10;
						}
						return seconds + "秒钟前";
					}
				}
			}
		}
	}

	/**
	 * 6.
	 * 
	 * @param context
	 * @param time
	 *            传入秒值
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("deprecation")
	public static String getNewTimeString(Context context, Long milliseconds) {
		Date afterDate = new Date();
		Long beforeTime = milliseconds;
		Date beforeDate = new Date(beforeTime);
		if (beforeDate.getYear() == afterDate.getYear()) {
			if (beforeDate.getDate() == afterDate.getDate() && beforeDate.getMonth() == afterDate.getMonth()) {
				if ((afterDate.getTime() - beforeTime) < 60 * 60 * 1000) {
					if ((afterDate.getTime() - beforeTime) < 60 * 1000) {
						int seconds = (int) ((afterDate.getTime() - beforeTime) / 1000);
						if (seconds <= 0) {
							seconds = 10;
						}
						return seconds + "秒钟前";
					} else {
						return (afterDate.getTime() - beforeTime) / (60 * 1000) + "分钟前";
					}
				} else {
					SimpleDateFormat sDate = new SimpleDateFormat("HH:mm:ss");
					String values = sDate.format(beforeDate);
					return "今天  " + values;
				}
			} else {
				SimpleDateFormat sDate = new SimpleDateFormat("MM-dd HH:mm:ss");
				String values = sDate.format(beforeDate);
				return values;
			}
		} else {
			SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
			String values = sDate.format(beforeDate);
			return values;
		}
	}

	/**
	 * 7.获得对应天的星座
	 * 
	 * @param month
	 *            月份
	 * @param day
	 *            天
	 * @return 星座
	 */
	public static final String getConstellation(int month, int day) {
		if ((month == 3 && day > 20) || (month == 4 && day < 20))
			return "白羊座";
		if ((month == 4 && day > 19) || (month == 5 && day < 21))
			return "金牛座";
		if ((month == 5 && day > 20) || (month == 6 && day < 20))
			return "双子座";
		if ((month == 6 && day > 21) || (month == 7 && day < 23))
			return "巨蟹座";
		if ((month == 7 && day > 22) || (month == 8 && day < 23))
			return "狮子座";
		if ((month == 8 && day > 22) || (month == 9 && day < 23))
			return "处女座";
		if ((month == 9 && day > 20) || (month == 10 && day < 23))
			return "天秤座";
		if ((month == 10 && day > 22) || (month == 11 && day < 22))
			return "天蝎座";
		if ((month == 11 && day > 21) || (month == 12 && day < 22))
			return "射手座";
		if ((month == 12 && day > 21) || (month == 1 && day < 20))
			return "摩羯座";
		if ((month == 1 && day > 19) || (month == 2 && day < 19))
			return "水瓶座";
		if ((month == 2 && day > 18) || (month == 3 && day < 21))
			return "双鱼座";
		return null;
	}

	// --------------------------------------------------------------------------------------------------------
	/**
	 * 8.将指定milliseconds转换成指定格式的字符串
	 * 
	 * @param format
	 *            1："yyyy年MM月dd日 HH时mm分ss秒" 2:"yyyy-MM-dd HH:mm:ss" 3:"yyyy/MM/dd HH:mm:ss" 4:"yyyy年MM月dd日 HH时mm分ss秒 E " 5:"yyyy/MM/dd E" 6."yyyy/MM/dd"7.yyyy-MM-dd HH:mm
	 * @return dateStr 指定格式字符串
	 */
	public static final String millisecondsToString(int format, Long milliseconds) {
		return dateToString(format, new Date(milliseconds));
	}

	/**
	 * <p>
	 * 9.将指定Date转换成指定格式的字符串
	 * </p>
	 * 
	 * @param format
	 *            格式 <li>1."yyyy年MM月dd日 HH时mm分ss秒"</li> <li>2."yyyy-MM-dd HH:mm:ss"</li> <li>3."yyyy/MM/dd HH:mm:ss"</li> <li>4."yyyy年MM月dd日 HH时mm分ss秒 E "</li> <li>5."yyyy/MM/dd E"</li> <li>
	 *            6."yyyy/MM/dd"</li> <li>7."yyyy-MM-dd HH:mm"</li> <li>8."yyyy年MM月dd日"</li> <li>9."yyyy-MM-dd"</li><li>10."HH:mm"</li>
	 * @return dateStr 指定格式字符串
	 */
	@SuppressLint("SimpleDateFormat")
	public static final String dateToString(int format, Date date) {
		SimpleDateFormat simpleDateFormat = null;
		String dateStr = null;
		switch (format) {
		case 1:
			simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			dateStr = simpleDateFormat.format(date);
			break;
		case 2:
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateStr = simpleDateFormat.format(date);
			break;
		case 3:
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			dateStr = simpleDateFormat.format(date);
			break;
		case 4:
			simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
			dateStr = simpleDateFormat.format(date);
			break;
		case 5:
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd E");
			dateStr = simpleDateFormat.format(date);
			break;
		case 6:
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd ");
			dateStr = simpleDateFormat.format(date);
			break;
		case 7:
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			dateStr = simpleDateFormat.format(date);
			break;
		case 8:
			simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			dateStr = simpleDateFormat.format(date);
			break;
		case 9:
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = simpleDateFormat.format(date);
			break;
		case 10:
			simpleDateFormat = new SimpleDateFormat("HH:mm");
			dateStr = simpleDateFormat.format(date);
			break;
		}
		return dateStr;
	}

	/**
	 * 10.字符串转换为date,也可以使用androidAPI中的DateFormat来实现
	 * 
	 * @param format
	 *            格式 <li>1."yyyy年MM月dd日 HH时mm分ss秒"</li> <li>2."yyyy-MM-dd HH:mm:ss"</li> <li>3."yyyy/MM/dd HH:mm:ss"</li> <li>4."yyyy年MM月dd日 HH时mm分ss秒 E "</li> <li>5."yyyy/MM/dd E"</li> <li>
	 *            6."yyyy/MM/dd"</li> <li>7."yyyy-MM-dd HH:mm"</li> <li>8."yyyy年MM月dd日"</li> <li>9."yyyy-MM-dd"</li>
	 * @param dateStr
	 *            时间字符串
	 * @return date格式
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static final Date stringToDate(int format, String dateStr) throws ParseException {
		if (TextUtils.isEmpty(dateStr)) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = null;
		Date date = null;
		switch (format) {
		case 1:
			simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 2:
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 3:
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 4:
			simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 5:
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd E");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 6:
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd ");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 7:
			simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 8:
			simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
			date = simpleDateFormat.parse(dateStr);
			break;
		case 9:
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = simpleDateFormat.parse(dateStr);
			break;
		}
		return date;
	}

	/**
	 * 11.字符串转换为Milliseconds
	 * 
	 * @param format
	 *            格式 1："yyyy年MM月dd日 HH时mm分ss秒" 2:"yyyy-MM-dd HH:mm:ss" 3:"yyyy/MM/dd HH:mm:ss" 4:"yyyy年MM月dd日 HH时mm分ss秒 E " 5:"yyyy/MM/dd E" 6."yyyy/MM/dd"
	 * @param dateStr
	 *            时间字符串
	 * @return Milliseconds
	 * @throws ParseException
	 */
	public static final Long stringToMilliseconds(int format, String dateStr) {
		if (TextUtils.isEmpty(dateStr)) {
			return 0l;
		}
		try {
			return stringToDate(format, dateStr).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0l;
	}

	// -------------------------------------------------------------------------------------------------------->
	/**
	 * 12.返回一个日时分秒的集合
	 * 
	 * @param type
	 *            倒计时类型1.日时分秒，2.日时分，3.时分秒
	 * @param milliseconds
	 *            毫秒数
	 * @return 日时分秒的集合1.日时分秒集合大小为8；2.日时分集合大小为6；3.时分秒集合大小为6
	 */
	public static final List<String> countDownString(int type, long milliseconds) {
		List<String> list = new ArrayList<String>();
		long day = 0;
		long hour = 0;
		long minute = 0;
		long second = 0;
		second = milliseconds;
		day = second / (1000 * 60 * 60 * 24);
		hour = (second - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		minute = (second - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
		second = (second - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / (1000);
		switch (type) {
		case 1:
			// 日时分秒
			addDateList(day, list);
			addDateList(hour, list);
			addDateList(minute, list);
			addDateList(second, list);
			break;
		case 2:
			// 日时分
			addDateList(day, list);
			addDateList(hour, list);
			addDateList(minute, list);
			break;
		case 3:
			// 时分秒
			addDateList(hour, list);
			addDateList(minute, list);
			addDateList(second, list);
			break;
		}
		return list;
	}

	/**
	 * 13. 在小于10的数前面加0
	 * 
	 * @param date
	 *            int类型数据
	 * @return
	 */
	public static final String to0Date(int date) {
		String str = null;
		if (date < 10) {
			str = "0" + date;
		} else {
			str = "" + date;
		}
		return str;
	}

	/**
	 * 往集合里添加日时分秒数据
	 * 
	 * @param date
	 * @param list
	 */
	private static final void addDateList(long date, List<String> list) {
		String str1 = null;
		String str2 = null;
		if (date == 0) {
			list.add("0");
			list.add("0");
		} else if (date > 0 && date < 10) {
			list.add("0");
			list.add(date + "");
		} else if (date >= 10) {
			String str = date + "";
			str1 = str.split("")[1];
			str2 = str.split("")[2];
			list.add(str1);
			list.add(str2);
		}
	}

	/**
	 * 
	 * @param mill
	 * @return
	 */
	public static String awayFromFuture(long mill) {
		BigInteger bigInteger = BigInteger.valueOf(mill);
		if (bigInteger.subtract(BigInteger.valueOf(30 * 24 * 60 * 60).multiply(BigInteger.valueOf(1000))).signum() > 0) {
			String month = bigInteger.divide(BigInteger.valueOf(30 * 24 * 60 * 60).multiply(BigInteger.valueOf(1000))).toString();
			return month + "月";
		} else if (mill > 24 * 60 * 60 * 1000) {
			long day = mill / (24 * 60 * 60 * 1000);
			return day + "天";
		} else if (mill > 60 * 60 * 1000) {
			long hour = mill / (60 * 60 * 1000);
			return hour + "小时";
		} else if (mill > 60 * 1000) {
			long minute = mill / (60 * 1000);
			return minute + "分钟";
		} else if (mill > 1000) {
			long seconds = mill / (1000);
			return seconds + "秒";
		}
		return null;
	}

}
