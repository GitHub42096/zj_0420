package utils.bisai;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @yhcip:CID systemframework
 * @yhcip:name DataUtil
 * @yhcip:type
 * @yhcip:title 日期构件
 * @yhcip:desc 有关日期处理的构件
 */
public class DateUtil{
	/**
	 * 默认的时间戳格式:{@value}
	 */
	public static final String TIMESTAMPFORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 默认的时间格式:{@value}
	 */
	public static final String TIMEFORMAT = "HH:mm:ss";

	/**
	 * 默认的日期格式:{@value}
	 */
	public static final String DATEFORMAT = "yyyy-MM-dd";

	public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 比较年份
	 */
	public static final int COMPUTE_YEAR = Calendar.YEAR;

	/**
	 * 比较月份
	 */
	public static final int COMPUTE_MONTH = Calendar.MONTH;

	/**
	 * 比较天数
	 */
	public static final int COMPUTE_DAY = Calendar.DATE;

	/**
	 * @yhcip:title 得到当前时间
	 * @yhcip:desc 得到当前时间
	 * @return 返回当前时间 时间格式如：2007-4-29 11:39:08
	 * @author Administrator
	 */
	public static String getFullCurrentTime() {
		String returnStr = null;
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		returnStr = DateFormat.getDateTimeInstance().format(date);
		return returnStr;
	}

	/**
	 * @yhcip:title 得到当前时间
	 * @yhcip:desc 得到当前时间
	 * @return 返回当前时间 时间格式如：20070429113908
	 * @author Administrator
	 */
	public static String getCurrentTime() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	/**
	 * @yhcip:title 返回指定格式的当前时间
	 * @yhcip:desc 返回指定格式的当前时间 getCurrentTime("yyyy-MM-dd") 返回 2007-04-29
	 * @param format
	 *            时间格式
	 * @return 得到当前时间的指定格式
	 * @author Administrator
	 */
	public static String getCurrentTime(String format) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	/**
	 * @yhcip:title 得到当前时间
	 * @yhcip:desc 得到当前时间 返回格式如：Sun Apr 29 11:44:35 CST 2007
	 * @return 返回格式如：Sun Apr 29 11:44:35 CST 2007
	 * @author Administrator
	 */
	public static Date getCurrentDateTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * @yhcip:title 得到当前处在当年的第几周
	 * @yhcip:desc 得到当前处在当年的第几周
	 * @return 如18，表示现在是第18周
	 * @author Administrator
	 */
	public static int getWeekOfYear() {
		Calendar oneCalendar = Calendar.getInstance();
		return oneCalendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @yhcip:title 得到当前是第几月
	 * @yhcip:desc 得到当前是第几月
	 * @return 如4，表示现在是4月
	 * @author Administrator
	 */
	public static int getMonth() {
		Calendar oneCalendar = Calendar.getInstance();
		return oneCalendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * @yhcip:title 返回当前时间
	 * @yhcip:desc 返回当前时间
	 * @return 时间格式：yyyy-MM-dd HH:mm:ss
	 * @author Administrator
	 */
	public static String getCurDateTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(calendar.getTime());
		return s;
	}

	/**
	 * @yhcip:title 返回当前时间
	 * @yhcip:desc 返回当前时间
	 * @return 时间格式：yyyy-MM-dd
	 * @author Administrator
	 */
	public static Date getCurDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String s = simpledateformat.format(calendar.getTime());
		return java.sql.Date.valueOf(s);
	}

	/**
	 * @yhcip:title 得到当前日期(java.sql.Date类型)
	 * @yhcip:desc 得到当前日期(java.sql.Date类型) 注意：没有时间，只有日期 格式：2007-04-29
	 * @return 注意：没有时间，只有日期 格式：2007-04-29
	 * @author Administrator
	 */
	public static java.sql.Date getDate() {
		Calendar oneCalendar = Calendar.getInstance();
		return getDate(oneCalendar.get(Calendar.YEAR), oneCalendar.get(Calendar.MONTH) + 1, oneCalendar.get(Calendar.DATE));
	}

	/**
	 * @yhcip:title 得到日期
	 * @yhcip:desc 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期年、月、日不合法，会抛IllegalArgumentException(不需要catch)
	 * @param yyyy
	 *            4位年
	 * @param MM
	 *            月
	 * @param dd
	 *            月
	 * @return 得到日期
	 * @author Administrator
	 */
	public static java.sql.Date getDate(int yyyy, int MM, int dd) {
		if (!verityDate(yyyy, MM, dd)) {
			throw new IllegalArgumentException("This is illegimate date!");
		}

		Calendar oneCalendar = Calendar.getInstance();
		oneCalendar.clear();
		oneCalendar.set(yyyy, MM - 1, dd);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}

	/**
	 * @yhcip:title 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。年、月、日不合法，会抛IllegalArgumentException(不需要catch)
	 * @yhcip:desc
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 得到日期
	 * @author Administrator
	 */
	public static java.sql.Date getDate(String year, String month, String day) {
		int iYear = Integer.parseInt(year);
		int iMonth = Integer.parseInt(month);
		int iDay = Integer.parseInt(day);
		return getDate(iYear, iMonth, iDay);
	}

	/**
	 * @yhcip:title 通过传入年月日，时分秒，返回一个java.util.Date格式的时间
	 * @yhcip:desc 通过传入年月日，时分秒，返回一个java.util.Date格式的时间
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param year
	 *            年
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return 得到一个日期
	 * @author Administrator
	 */
	public static Date getDate(int month, int day, int year, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();

		try {
			calendar.set(year, month - 1, day, hour, minute, second);
		} catch (Exception e) {
			return null;
		}
		return new Date(calendar.getTime().getTime());
	}

	/**
	 * @yhcip:title 通过传入年月日，时分秒，返回一个java.util.Date格式的时间
	 * @yhcip:desc 通过传入年月日，时分秒，返回一个java.util.Date格式的时间
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param year
	 *            年
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return 得到一个日期
	 * @author Administrator
	 */
	public static Date getDate(String month, String day, String year, String hour, String minute, String second) {
		int iYear = Integer.parseInt(year);
		int iMonth = Integer.parseInt(month);
		int iDay = Integer.parseInt(day);
		int iHour = Integer.parseInt(hour);
		int iMinute = Integer.parseInt(minute);
		int iSecond = Integer.parseInt(second);

		return getDate(iMonth, iDay, iYear, iHour, iMinute, iSecond);
	}

	/**
	 * @yhcip:title 根据所给年、月、日，检验是否为合法日期。
	 * @yhcip:desc 根据所给年、月、日，检验是否为合法日期。
	 * @param yyyy
	 *            年
	 * @param MM
	 *            月
	 * @param dd
	 *            日
	 * @return 判断是否有效
	 * @author Administrator
	 */
	public static boolean verityDate(int yyyy, int MM, int dd) {
		boolean flag = false;

		if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
			if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
				if (dd <= 30) {
					flag = true;
				}
			} else if (MM == 2) {
				if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
					if (dd <= 29) {
						flag = true;
					}
				} else if (dd <= 28) {
					flag = true;

				}
			} else {
				flag = true;

			}
		}
		return flag;
	}

	/**
	 * @yhcip:title 将日期类型转换成字符串类型
	 * @yhcip:desc 将日期类型转换成字符串类型
	 * @param date
	 *            指定的日期
	 * @return 得到一个串 格式为 yyyy-MM-dd
	 * @author Administrator
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		String strDate = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
		strDate = simpleDateFormat.format(date);
		return strDate;
	}

	/**
	 * @yhcip:title 指定时间返回一个字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @yhcip:desc 指定时间返回一个字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @param date
	 *            指定时间
	 * @return 返回一个格式为yyyy-MM-dd HH:mm:ss的串
	 * @author Administrator
	 */
	public static String datetimeToString(Date date) {
		if (date == null) {
			return null;
		}
		String strDate = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIMEFORMAT);
		strDate = simpleDateFormat.format(date);
		return strDate;
	}

	/**
	 * @yhcip:title 指定时间，返回一个指定格式的字符串
	 * @yhcip:desc 指定时间，返回一个指定格式的字符串
	 * @param date
	 *            指定时间
	 * @param format
	 *            时间格式
	 * @return 串
	 * @author Administrator
	 */
	public static String datetimeToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		String strDate = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		strDate = simpleDateFormat.format(date);
		return strDate;
	}

	/**
	 * @yhcip:title 给一个时间，返回指定格时间串。格式为HH:mm:ss
	 * @yhcip:desc 给一个时间，返回指定格时间串。格式为HH:mm:ss
	 * @param date
	 * @return 时间串
	 * @author Administrator
	 */
	public static String timeToString(Date date) {
		if (date == null) {
			return null;
		}
		String strDate = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIMEFORMAT);
		strDate = simpleDateFormat.format(date);
		return strDate;
	}

	/**
	 * @yhcip:title 将用指定格式的字符串表示的日期转换成日期类型
	 * @yhcip:desc 将用指定格式的字符串表示的日期转换成日期类型
	 * @param strDate
	 *            要转换的日期字符串
	 * @param srcDateFormat
	 *            源日期格式字符串，如：“yyyy-MM-dd HH:mm:ss”，指明strDate包含的日期是什么格式，以便正确解析出日期
	 * @param dstDateFormat
	 *            目标日期格式字符串
	 * @return 日期
	 */
	public static Date stringToDate(String strDate, String srcDateFormat, String dstDateFormat) {

		Date rtDate = null;
		// 用源日期格式字符串，将日期字符串解析成日期类型
		Date tmpDate = new SimpleDateFormat(srcDateFormat).parse(strDate, new ParsePosition(0));
		// 将日期转换成目标格式字符串
		String tmpString = null;
		if (tmpDate != null) {
			tmpString = new SimpleDateFormat(dstDateFormat).format(tmpDate);
		}

		// 将目标日期字符串，转换成日期类型
		if (tmpString != null) {
			rtDate = new SimpleDateFormat(dstDateFormat).parse(tmpString, new ParsePosition(0));
		}
		return rtDate;
	}

	/**
	 * @yhcip:title 把字符串根据格式字符串转换为日期
	 * @yhcip:desc 把字符串根据格式字符串转换为日期
	 * @param strDate
	 *            字符串日期
	 * @param srcDateFormat
	 *            日期格式
	 * @return 日期
	 */
	public static Date stringToDate(String strDate, String srcDateFormat) {
		return new SimpleDateFormat(srcDateFormat).parse(strDate, new ParsePosition(0));
	}

	/**
	 * @yhcip:title 将字符串按默认日期格式转换成日期类型
	 * @yhcip:desc 将字符串按默认日期格式转换成日期类型
	 * @param strDate
	 *            字符串日期
	 * @return 日期
	 */
	public static Date stringToDate(String strDate) {
		return stringToDate(strDate, DATEFORMAT);
	}

	/**
	 * @yhcip:title 将日期类型转化为sql日期类型
	 * @yhcip:desc 将日期类型转化为sql日期类型
	 * @param date
	 *            日期类型
	 * @return sql日期类型
	 */
	public static java.sql.Date utilDateToSqlDate(Date date) {
		if (date == null) {
			return null;
		} else {
			return new java.sql.Date(date.getTime());
		}
	}

	/**
	 * @yhcip:title 将日期类型转化为sql时间类型
	 * @yhcip:desc 将日期类型转化为sql时间类型
	 * @param date
	 *            日期类型
	 * @return sql时间类型
	 */
	public static java.sql.Time utilDateToSqlTime(Date date) {
		if (date == null) {
			return null;
		} else {
			return new java.sql.Time(date.getTime());
		}
	}

	/**
	 * @yhcip:title 将日期类型转化为sql时间邮戳类型
	 * @yhcip:desc 将日期类型转化为sql时间邮戳类型
	 * @param date
	 *            日期类型
	 * @return sql时间邮戳类型
	 */
	public static Timestamp utilDateToSqlTimestamp(Date date) {
		if (date == null) {
			return null;
		} else {
			return new Timestamp(date.getTime());
		}
	}

	/**
	 * @yhcip:title 将字符串按默认日期格式转换成sql日期类型
	 * @yhcip:desc 将字符串按默认日期格式转换成sql日期类型
	 * @param strDate
	 *            字符串日期
	 * @return sql日期
	 */
	public static java.sql.Date stringToSqlDate(String strDate) {
		return stringToSqlDate(strDate, DATEFORMAT);
	}

	/**
	 * @yhcip:title 将字符串按指定日期格式转换成sql日期类型
	 * @yhcip:desc 将字符串按指定日期格式转换成sql日期类型
	 * @param strDate
	 *            字符串日期
	 * @param srcDateFormat
	 *            日期格式
	 * @return sql日期
	 */
	public static java.sql.Date stringToSqlDate(String strDate, String srcDateFormat) {
		return stringToSqlDate(strDate, srcDateFormat, DATEFORMAT);
	}

	/**
	 * @yhcip:title 将字符串按指定日期格式转换成sql日期类型
	 * @yhcip:desc 将字符串按指定日期格式转换成sql日期类型
	 * @param strDate
	 *            字符串日期
	 * @param srcDateFormat
	 *            源日期格式
	 * @param dstDateFormat
	 *            目标日期格式
	 * @return sql日期
	 */
	public static java.sql.Date stringToSqlDate(String strDate, String srcDateFormat, String dstDateFormat) {
		return utilDateToSqlDate(stringToDate(strDate, srcDateFormat, dstDateFormat));
	}

	/**
	 * @yhcip:title 将字符串按默认日期格式转化为sql时间类型
	 * @yhcip:desc 将字符串按默认日期格式转化为sql时间类型
	 * @param strDate
	 *            字符串
	 * @return sql时间类型
	 */
	public static java.sql.Time stringToSqlTime(String strDate) {
		return stringToSqlTime(strDate, TIMEFORMAT);
	}

	/**
	 * @yhcip:title 将字符串按指定日期格式转化为sql时间类型
	 * @yhcip:desc 将字符串按指定日期格式转化为sql时间类型
	 * @param strDate
	 *            字符串
	 * @param srcDateFormat
	 *            日期格式
	 * @return sql时间类型
	 */
	public static java.sql.Time stringToSqlTime(String strDate, String srcDateFormat) {
		return stringToSqlTime(strDate, srcDateFormat, TIMEFORMAT);
	}

	/**
	 * @yhcip:title 将字符串按指定日期格式转化为sql时间类型
	 * @yhcip:desc 将字符串按指定日期格式转化为sql时间类型
	 * @param strDate
	 *            字符串
	 * @param srcDateFormat
	 *            源日期格式
	 * @param dstDateFormat
	 *            目标日期格式
	 * @return sql时间类型
	 */
	public static java.sql.Time stringToSqlTime(String strDate, String srcDateFormat, String dstDateFormat) {
		return utilDateToSqlTime(stringToDate(strDate, srcDateFormat, dstDateFormat));
	}

	/**
	 * @yhcip:title 将字符串按默认日期格式转化为sql时间邮戳类型 格式为yyyy-MM-dd HH:mm:ss
	 * @yhcip:desc 将字符串按默认日期格式转化为sql时间邮戳类型 格式为yyyy-MM-dd HH:mm:ss
	 * @param strDate
	 *            字符串
	 * @return sql时间邮戳类型
	 */
	public static Timestamp stringToSqlTimestamp(String strDate) {
		return stringToSqlTimestamp(strDate, TIMESTAMPFORMAT);
	}

	/**
	 * @yhcip:title 将字符串按指定日期格式转化为sql时间邮戳类型 格式为yyyy-MM-dd HH:mm:ss
	 * @yhcip:desc 将字符串按指定日期格式转化为sql时间邮戳类型 格式为yyyy-MM-dd HH:mm:ss
	 * @param strDate
	 *            字符串
	 * @param srcDateFormat
	 *            日期格式
	 * @return sql时间邮戳类型
	 */
	public static Timestamp stringToSqlTimestamp(String strDate, String srcDateFormat) {
		return stringToSqlTimestamp(strDate, srcDateFormat, TIMESTAMPFORMAT);
	}

	/**
	 * @yhcip:title 将字符串按指定日期格式转化为sql时间邮戳类型 格式为yyyy-MM-dd HH:mm:ss
	 * @yhcip:desc 将字符串按指定日期格式转化为sql时间邮戳类型 格式为yyyy-MM-dd HH:mm:ss
	 * @param strDate
	 *            字符串
	 * @param srcDateFormat
	 *            源日期格式
	 * @param dstDateFormat
	 *            目标日期格式
	 * @return sql时间邮戳类型
	 */
	public static Timestamp stringToSqlTimestamp(String strDate, String srcDateFormat, String dstDateFormat) {
		return utilDateToSqlTimestamp(stringToDate(strDate, srcDateFormat, dstDateFormat));
	}

	/**
	 * @yhcip:title 指定时分秒 格式为HH:MM:SS or HH:MM
	 * @yhcip:desc 指定时分秒 格式为HH:MM:SS or HH:MM
	 * @param hour
	 *            The hour int
	 * @param minute
	 *            The minute int
	 * @param second
	 *            The second int
	 * @return A time String in the format HH:MM:SS or HH:MM
	 */
	public static String toTimeString(int hour, int minute, int second) {
		String hourStr;
		String minuteStr;
		String secondStr;

		if (hour < 10) {
			hourStr = "0" + hour;
		} else {
			hourStr = "" + hour;
		}
		if (minute < 10) {
			minuteStr = "0" + minute;
		} else {
			minuteStr = "" + minute;
		}
		if (second < 10) {
			secondStr = "0" + second;
		} else {
			secondStr = "" + second;
		}
		if (second == 0)
			return hourStr + ":" + minuteStr;
		else
			return hourStr + ":" + minuteStr + ":" + secondStr;
	}

	/**
	 * @yhcip:title 得到date当天的开始时间(时分秒0,0,0)
	 * @yhcip:desc 得到date当天的开始时间(时分秒0,0,0)
	 * @param date
	 * @return
	 */
	public static java.sql.Date getDayStart(java.sql.Date date) {
		return getDayStart(date, 0);
	}

	/**
	 * @yhcip:title 得到date当天后daysLater天的开始时间(时分秒0,0,0)
	 * @yhcip:desc 得到date当天后daysLater天的开始时间(时分秒0,0,0)
	 * @param date
	 * @param daysLater
	 * @return
	 */
	public static java.sql.Date getDayStart(java.sql.Date date, int daysLater) {
		Calendar tempCal = Calendar.getInstance();

		tempCal.setTime(new Date(date.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		return new java.sql.Date(tempCal.getTime().getTime());
	}

	/**
	 * @yhcip:title 得到date第二天的开始时间(时分秒0,0,0)
	 * @yhcip:desc 得到date第二天的开始时间(时分秒0,0,0)
	 * @param date
	 * @return
	 */
	public static java.sql.Date getNextDayStart(java.sql.Date date) {
		return getDayStart(date, 1);
	}

	/**
	 * @yhcip:title 得到date当天的结束时间(23:59:59)
	 * @yhcip:desc 得到date当天的结束时间(23:59:59)
	 * @param stamp
	 * @return
	 */
	public static java.sql.Date getDayEnd(java.sql.Date stamp) {
		return getDayEnd(stamp, 0);
	}

	/**
	 * @yhcip:title 得到date当天后daysLater天的结束时间(23:59:59)
	 * @yhcip:desc 得到date当天后daysLater天的结束时间(23:59:59)
	 * @param stamp
	 * @return
	 */
	public static java.sql.Date getDayEnd(java.sql.Date stamp, int daysLater) {
		Calendar tempCal = Calendar.getInstance();

		tempCal.setTime(new Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		return new java.sql.Date(tempCal.getTime().getTime());
	}

	/**
	 * @yhcip:title 得到月初日期
	 * @yhcip:desc 得到月初日期
	 * @return
	 * @author Administrator
	 */
	public static java.sql.Date monthBegin() {
		Calendar mth = Calendar.getInstance();

		mth.set(Calendar.DAY_OF_MONTH, 1);
		mth.set(Calendar.HOUR_OF_DAY, 0);
		mth.set(Calendar.MINUTE, 0);
		mth.set(Calendar.SECOND, 0);
		mth.set(Calendar.AM_PM, Calendar.AM);
		return new java.sql.Date(mth.getTime().getTime());
	}

	/**
	 * @yhcip:title 得到时间的显示字符串
	 * @yhcip:desc 得到时间的显示字符串
	 * @param datetime
	 * @return
	 * @author Administrator
	 */
	public static String getDateTimeDisp(String datetime) {
		if ((datetime == null) || (datetime.equals("")))
			return "";

		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		long datel = Long.parseLong(datetime);
		return formatter.format(new Date(datel));
	}

	/**
	 * @yhcip:title 检查字符串是否4位的年份
	 * @yhcip:desc 检查字符串是否4位的年份
	 * @param year
	 * @return
	 * @author Administrator
	 */
	public static boolean isYear(String year) {
		return StringUtils.isNumeric(year)&& (year != null) && year.length() == 4;
	}

	/**
	 * @yhcip:title 检查字符串是否月份(1位或2位)
	 * @yhcip:desc 检查字符串是否月份(1位或2位)
	 * @param month
	 * @return
	 * @author Administrator
	 */
	public static boolean isMonth(String month) {
		if (month == null)
			return false;
		if (month.length() != 1 && month.length() != 2)
			return false;
		if (!StringUtils.isNumeric(month))
			return false;
		int iMonth = Integer.parseInt(month);
		return iMonth >= 1 && iMonth <= 12;
	}

	/**
	 * @yhcip:title 检查字符串是否日期(1位或2位):1-31
	 * @yhcip:desc 检查字符串是否日期(1位或2位):1-31
	 * @param day
	 * @return
	 * @author Administrator
	 */
	public static boolean isDay(String day) {
		if (day == null)
			return false;
		if (day.length() != 1 && day.length() != 2)
			return false;
		if (!StringUtils.isNumeric(day))
			return false;
		int iDay = Integer.parseInt(day);
		return iDay >= 1 && iDay <= 31;
	}

	/**
	 * @yhcip:title 检查字符串是否小时(1位或2位):0-23
	 * @yhcip:desc 检查字符串是否小时(1位或2位):0-23
	 * @param hour
	 * @return
	 * @author Administrator
	 */
	public static boolean isHour(String hour) {
		if (hour == null)
			return false;
		if (hour.length() != 1 && hour.length() != 2)
			return false;
		if (!StringUtils.isNumeric(hour))
			return false;
		int iHour = Integer.parseInt(hour);
		return iHour >= 0 && iHour <= 23;
	}

	/**
	 * @yhcip:title 检查字符串是否分(1位或2位):0-59
	 * @yhcip:desc 检查字符串是否分(1位或2位):0-59
	 * @param minute
	 * @return
	 * @author Administrator
	 */
	public static boolean isMinute(String minute) {
		if (minute == null)
			return false;
		if (minute.length() != 1 && minute.length() != 2)
			return false;
		if (!StringUtils.isNumeric(minute))
			return false;
		int iMinute = Integer.parseInt(minute);
		return iMinute >= 0 && iMinute <= 59;
	}

	/**
	 * @yhcip:title 检查字符串是否秒(1位或2位):0-59
	 * @yhcip:desc 检查字符串是否秒(1位或2位):0-59
	 * @param second
	 * @return
	 * @author Administrator
	 */
	public static boolean isSecond(String second) {
		if (second == null)
			return false;
		if (second.length() != 1 && second.length() != 2)
			return false;
		if (!StringUtils.isNumeric(second))
			return false;
		int iSecond = Integer.parseInt(second);
		return iSecond >= 0 && iSecond <= 59;
	}

	/**
	 * @yhcip:title 检查年月日是否合法
	 * @yhcip:desc 检查年月日是否合法
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @author Administrator
	 */
	public static boolean isDate(String year, String month, String day) {
		if (!isYear(year))
			return false;
		if (!isMonth(month))
			return false;
		if (!isDay(day))
			return false;
		int iYear = Integer.parseInt(year);
		int iMonth = Integer.parseInt(month);
		int iDay = Integer.parseInt(day);

		return verityDate(iYear, iMonth, iDay);
	}

	/**
	 * @yhcip:title 检查date是否符合yyyy-MM-dd格式的日期
	 * @yhcip:desc 检查date是否符合yyyy-MM-dd格式的日期
	 * @param date
	 * @return
	 * @author Administrator
	 */
	public static boolean isDate(String date) {
		if (StringUtils.isEmpty(date))
			return false;
		char divide = '-';
		int index = date.indexOf(divide);
		if (index < 0)
			return false;
		String year = date.substring(0, index);
		int index2 = date.indexOf(divide, index + 1);
		if (index2 < 0)
			return false;
		String month = date.substring(index + 1, index2);
		String day = date.substring(index2 + 1);
		return isDate(year, month, day);
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对月份的计算会受具体时间（时分秒）的影响。 <br>
	 * 如：3月3号01:01:01到4月3号01:02:01，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeDayOnly方法替代，该方法会将日期天数置为1进行计算。
	 * <p>
	 * 使用此方法时，对月份的计算会受具体时间（天）的影响。 <br>
	 * 如：3月2号到4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * <br>
	 * 建议使用computeMonthOnly方法替代，该方法会将日期天数置为1进行计算。
	 * <p>
	 * 使用此方法时，对年份的计算会受具体时间（月和天）的影响。 <br>
	 * 如：2002年3月2号到2003年4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeYearOnly方法替代，该方法会将日期月份和天数置为1进行计算。
	 * <p>
	 * 按照computeFlag参数定义的计算方式，计算第一个日期到第二个日期之间的间隔
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @param computeFlag
	 *            计算内容
	 * @param bExact
	 *            精确计算标志
	 * @return
	 * @author ChenHao
	 */
	private static int compute(String arg1, String arg2, int computeFlag, String format, boolean bExact) {
		Date date1 = null;
		Date date2 = null;

		SimpleDateFormat sdf = null;

		if (format != null && format.trim().length() > 0)
			sdf = new SimpleDateFormat(format);
		else
			sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			date1 = sdf.parse(arg1);
			date2 = sdf.parse(arg2);

			return compute(date1, date2, computeFlag, bExact);
		} catch (Exception e) {
			throw new SysLevelException(e.getMessage());
		}
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对月份的计算会受具体时间（天）的影响。 <br>
	 * 如：3月2号到4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeMonthOnly方法替代，该方法会将日期天数置为1进行计算。
	 * <p>
	 * 使用此方法时，对年份的计算会受具体时间（月和天）的影响。 <br>
	 * 如：2002年3月2号到2003年4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeYearOnly方法替代，该方法会将日期月份和天数置为1进行计算。
	 * <p>
	 * 按照computeFlag参数定义的计算方式，计算第一个日期到第二个日期之间的间隔
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @param computeFlag
	 *            计算内容
	 * @param bExact
	 *            精确计算标志
	 * @return
	 * @author ChenHao
	 */
	private static int compute(Date arg1, Date arg2, int computeFlag, boolean bExact) {
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(arg1);
		ca1.set(Calendar.HOUR, 0);
		ca1.set(Calendar.MINUTE, 0);
		ca1.set(Calendar.SECOND, 0);
		ca1.set(Calendar.MILLISECOND, 0);

		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(arg2);
		ca2.set(Calendar.HOUR, 0);
		ca2.set(Calendar.MINUTE, 0);
		ca2.set(Calendar.SECOND, 0);
		ca2.set(Calendar.MILLISECOND, 0);

		int elapsed = 0;

		if (ca1.after(ca2)) // date1 > date2
		{
			while (ca1.after(ca2)) {
				ca1.add(computeFlag, -1);

				elapsed--;
			}

			if (bExact) // 进行精确比较
			{
				if (COMPUTE_MONTH == computeFlag) {
					if (ca1.get(Calendar.DATE) != ca2.get(Calendar.DATE))
						elapsed = elapsed + 1;
				}
				if (COMPUTE_YEAR == computeFlag) {
					if (ca1.get(Calendar.MONTH) != ca2.get(Calendar.MONTH))
						elapsed = elapsed + 1;
					else if (ca1.get(Calendar.DATE) != ca2.get(Calendar.DATE))
						elapsed = elapsed + 1;
				}
			}

			return -elapsed;

		} else if (ca1.before(ca2))// date1 < date2
		{
			while (ca1.before(ca2)) {
				ca1.add(computeFlag, 1);

				elapsed++;
			}

			if (bExact) // 进行精确比较
			{
				if (COMPUTE_MONTH == computeFlag) {
					if (ca1.get(Calendar.DATE) != ca2.get(Calendar.DATE))
						elapsed = elapsed - 1;
				}
				if (COMPUTE_YEAR == computeFlag) {
					if (ca1.get(Calendar.MONTH) > ca2.get(Calendar.MONTH))
						elapsed = elapsed - 1;
					else if (ca1.get(Calendar.DATE) != ca2.get(Calendar.DATE))
						elapsed = elapsed - 1;
				}
			}

			return -elapsed;
		} else
			// 相等
			return 0;
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对年份的计算会受具体时间（月和天）的影响。 <br>
	 * 如：2002年3月2号到2003年4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeYearOnly方法替代，该方法会将日期月份和天数置为1进行计算。
	 * <p>
	 * 计算第一个日期到第二个日期之间的间隔年数
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @param format
	 *            计算内容
	 * @param bExact
	 *            精确计算标志
	 * @return
	 * @author ChenHao
	 */
	public static int computeYear(String arg1, String arg2, String format, boolean bExact) {
		return compute(arg1, arg2, COMPUTE_YEAR, format, bExact);
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对年份的计算会受具体时间（月和天）的影响。 <br>
	 * 如：2002年3月2号到2003年4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeYearOnly方法替代，该方法会将日期月份和天数置为1进行计算。
	 * <p>
	 * 计算第一个日期到第二个日期之间的间隔年数
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @param bExact
	 *            精确计算标志
	 * @return
	 * @author ChenHao
	 */
	public static int computeYear(Date arg1, Date arg2, boolean bExact) {
		return compute(arg1, arg2, COMPUTE_YEAR, bExact);
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对月份的计算会受具体时间（天）的影响。 <br>
	 * 如：3月2号到4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeMonthOnly方法替代，该方法会将日期天数置为1进行计算。
	 * <p>
	 * 计算第一个日期到第二个日期之间的间隔月数
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @param format
	 *            计算内容
	 * @param bExact
	 *            精确计算标志
	 * @return
	 * @author ChenHao
	 */
	public static int computeMonth(String arg1, String arg2, String format, boolean bExact) {
		return compute(arg1, arg2, COMPUTE_MONTH, format, bExact);
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对月份的计算会受具体时间（天）的影响。 <br>
	 * 如：3月2号到4月3号，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeMonthOnly方法替代，该方法会将日期天数置为1进行计算。
	 * <p>
	 * 计算第一个日期到第二个日期之间的间隔月数
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @param bExact
	 *            精确计算标志
	 * @return
	 * @author ChenHao
	 */
	public static int computeMonth(Date arg1, Date arg2, boolean bExact) {
		return compute(arg1, arg2, COMPUTE_MONTH, bExact);
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对月份的计算会受具体时间（时分秒）的影响。 <br>
	 * 如：3月3号01:01:01到4月3号01:02:01，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeMonthOnly方法替代，该方法会将日期天数置为1进行计算。
	 * <p>
	 * 计算第一个日期到第二个日期之间的间隔天数
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @param format
	 *            计算内容
	 * @return
	 * @author ChenHao
	 */
	public static int computeDay(String arg1, String arg2, String format) {
		return compute(arg1, arg2, COMPUTE_DAY, format, true);
	}

	/**
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * <p>
	 * 使用此方法时，对月份的计算会受具体时间（时分秒）的影响。 <br>
	 * 如：3月3号01:01:01到4月3号01:02:01，可能因计算方法的不同，造成结果为1或2。 <br>
	 * 建议使用computeMonthOnly方法替代，该方法会将日期天数置为1进行计算。
	 * <p>
	 * 计算第一个日期到第二个日期之间的间隔天数
	 * <p>
	 * 如果arg1大于arg2返回值大于0，如果arg1等于arg2返回0，如果arg1小于arg2返回值小于0
	 * <p>
	 * 如果参数bExact为false，若计算内容不足年或不足月时，返回值按足年或足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 如果参数bExact为true，若计算内容不足年或不足月时，返回值按不足年或不足月计算，仅对计算年或计算月时有效
	 * <p>
	 * 例如：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-20"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-1
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-24"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-3 又：
	 * <p>
	 * arg1="2002-09-23"，arg2="2002-11-23"
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=true，返回-2
	 * <p>
	 * 若 computeFlag=COMPARE_MONTH，bExact=false，返回-2
	 * 
	 * @param arg1
	 *            第一个日期参数
	 * @param arg2
	 *            第二个日期参数
	 * @return
	 * @author ChenHao
	 */
	public static int computeDay(Date arg1, Date arg2) {
		return compute(arg1, arg2, COMPUTE_DAY, true);
	}

	/**
	 * 将日期传返回yyyymm格式
	 */
	public static String fnGetStr4Y2M(String szStr) {
		String szRst = szStr;
		szRst = szRst.replaceAll("[ \\|\\-:\\.]", "");
		szRst = szRst.substring(0, 6);
		return szRst;
	}

	/**
	 * 根据所给的起始,终止时间来计算间隔月数
	 * 
	 * @param startDate
	 *            YYYYMM
	 * @param endDate
	 *            YYYYMM
	 * @return 间隔月数
	 */
	public static int getIntervalMonth(String startDate, String endDate) {
		if (startDate.length() > 6) {
			startDate = fnGetStr4Y2M(startDate);
		}
		if (endDate.length() > 6) {
			endDate = fnGetStr4Y2M(endDate);
		}
		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int startMonth = 0;
		if (startDate.substring(4, 5).equals("0")) {
			startMonth = Integer.parseInt(startDate.substring(5));
		}
		startMonth = Integer.parseInt(startDate.substring(4, 6));

		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int endMonth = 0;
		if (endDate.substring(4, 5).equals("0")) {
			endMonth = Integer.parseInt(endDate.substring(5));
		}
		endMonth = Integer.parseInt(endDate.substring(4, 6));

		int intervalMonth = (endYear * 12 + endMonth) - (startYear * 12 + startMonth);
		return intervalMonth;
	}

	/**
	 * 根据所给的起始,终止时间来计算间隔月数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 间隔月数
	 */
	public static int getIntervalMonth(Date startDate, Date endDate) {
		return computeMonthOnly(startDate, endDate);
	}

	/**
	 * 根据所给的起始,终止时间来计算间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 间隔天数 重载sysframework的DateUtil.java的getIntervalDay方法，原方法有错
	 */
	public static int getIntervalDay(java.sql.Date startDate, java.sql.Date endDate) {
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) (interval / (1000 * 60 * 60 * 24));
		return intervalday;
	}

	/**
	 * 根据所给的起始,终止时间来计算间隔天数[天到天]
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 间隔天数 重载sysframework的DateUtil.java的getIntervalDay方法，原方法有错
	 */
	public static int getIntervalDay(Timestamp startDate, Timestamp endDate) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTimeInMillis(startDate.getTime());
		calendar2.setTimeInMillis(endDate.getTime());
		calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE), 0, 0, 0);
		calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DATE), 0, 0, 0);
		long startdate = calendar1.getTimeInMillis();
		long enddate = calendar2.getTimeInMillis();
		long interval = enddate - startdate;
		int intervalday = (int) (interval / (1000 * 60 * 60 * 24));
		return intervalday;
	}

	/**
	 * 计算时间月份间隔，忽略天数和时分秒的差异
	 * <p>
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。
	 * <p>
	 * 若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @return 月份间隔
	 * @author ChenHao
	 */
	public static int computeMonthOnly(Date dateBegin, Date dateEnd) {
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(dateBegin);
		ca1.set(Calendar.DATE, 1);
		ca1.set(Calendar.HOUR, 0);
		ca1.set(Calendar.MINUTE, 0);
		ca1.set(Calendar.SECOND, 0);
		ca1.set(Calendar.MILLISECOND, 0);

		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(dateEnd);
		ca2.set(Calendar.DATE, 1);
		ca2.set(Calendar.HOUR, 0);
		ca2.set(Calendar.MINUTE, 0);
		ca2.set(Calendar.SECOND, 0);
		ca2.set(Calendar.MILLISECOND, 0);

		return compute(ca1.getTime(), ca2.getTime(), COMPUTE_MONTH, true);
	}

	/**
	 * 计算从dateBegin到dateEnd之间有多少个月，忽略天数和时分秒的差异
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param bWith
	 *            是否将dateEnd包含到计算结果中
	 * @return 月份间隔
	 * @author ChenHao
	 */
	public static int computeMonthOnly(Date dateBegin, Date dateEnd, boolean bWith) {
		int value = computeMonthOnly(dateBegin, dateEnd);

		if (bWith) {
			if (dateBegin.after(dateEnd))
				value += 1;
			else if (dateBegin.before(dateEnd))
				value -= 1;
			else
				value = 1;
		}

		return value;
	}

	/**
	 * 计算时间月份间隔，忽略天数和时分秒的差异
	 * <p>
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。
	 * <p>
	 * 若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param format
	 *            日期格式
	 * @return 月份间隔
	 * @author ChenHao
	 */
	public static int computeMonthOnly(String dateBegin, String dateEnd, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			Calendar ca1 = Calendar.getInstance();
			ca1.setTime(sdf.parse(dateBegin));
			ca1.set(Calendar.DATE, 1);
			ca1.set(Calendar.HOUR, 0);
			ca1.set(Calendar.MINUTE, 0);
			ca1.set(Calendar.SECOND, 0);
			ca1.set(Calendar.MILLISECOND, 0);

			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(sdf.parse(dateEnd));
			ca2.set(Calendar.DATE, 1);
			ca2.set(Calendar.HOUR, 0);
			ca2.set(Calendar.MINUTE, 0);
			ca2.set(Calendar.SECOND, 0);
			ca2.set(Calendar.MILLISECOND, 0);

			return compute(ca1.getTime(), ca2.getTime(), COMPUTE_MONTH, true);
		} catch (Exception e) {
			throw new SysLevelException(e.getMessage());
		}
	}

	/**
	 * 计算从dateBegin到dateEnd之间有多少个月，忽略天数和时分秒的差异
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param format
	 *            日期格式
	 * @param bWith
	 *            是否将dateEnd包含到计算结果中
	 * @return 月份间隔
	 * @author ChenHao
	 */
	public static int computeMonthOnly(String dateBegin, String dateEnd, String format, boolean bWith) {
		int value = computeMonthOnly(dateBegin, dateEnd, format);

		if (bWith) {
			Date begin = stringToDate(dateBegin, format);
			Date end = stringToDate(dateEnd, format);

			if (begin.after(end))
				value += 1;
			else if (begin.before(end))
				value -= 1;
			else
				value = 1;
		}

		return value;
	}

	/**
	 * 计算时间年份间隔，忽略月份、天数和时分秒的差异
	 * <p>
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。
	 * <p>
	 * 若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @return 年份间隔
	 * @author ChenHao
	 */
	public static int computeYearOnly(Date dateBegin, Date dateEnd) {
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(dateBegin);
		ca1.set(Calendar.DATE, 1);
		ca1.set(Calendar.MONTH, 1);
		ca1.set(Calendar.HOUR, 0);
		ca1.set(Calendar.MINUTE, 0);
		ca1.set(Calendar.SECOND, 0);
		ca1.set(Calendar.MILLISECOND, 0);

		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(dateEnd);
		ca2.set(Calendar.DATE, 1);
		ca2.set(Calendar.MONTH, 1);
		ca2.set(Calendar.HOUR, 0);
		ca2.set(Calendar.MINUTE, 0);
		ca2.set(Calendar.SECOND, 0);
		ca2.set(Calendar.MILLISECOND, 0);

		return compute(ca1.getTime(), ca2.getTime(), COMPUTE_YEAR, true);
	}

	/**
	 * 计算从dateBegin到dateEnd之间有多少年，忽略月份、天数和时分秒的差异
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param bWith
	 *            是否将dateEnd包含到计算结果中
	 * @return 年份间隔
	 * @author ChenHao
	 */
	public static int computeYearOnly(Date dateBegin, Date dateEnd, boolean bWith) {
		int value = computeYearOnly(dateBegin, dateEnd);

		if (bWith) {
			if (dateBegin.after(dateEnd))
				value += 1;
			else if (dateBegin.before(dateEnd))
				value -= 1;
			else
				value = 1;
		}

		return value;
	}

	/**
	 * 计算时间年份间隔，忽略月份、天数和时分秒的差异
	 * <p>
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。
	 * <p>
	 * 若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param format
	 *            日期格式
	 * @return 年份间隔
	 * @author ChenHao
	 */
	public static int computeYearOnly(String dateBegin, String dateEnd, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			Calendar ca1 = Calendar.getInstance();
			ca1.setTime(sdf.parse(dateBegin));
			ca1.set(Calendar.DATE, 1);
			ca1.set(Calendar.MONTH, 1);
			ca1.set(Calendar.HOUR, 0);
			ca1.set(Calendar.MINUTE, 0);
			ca1.set(Calendar.SECOND, 0);
			ca1.set(Calendar.MILLISECOND, 0);

			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(sdf.parse(dateEnd));
			ca2.set(Calendar.DATE, 1);
			ca2.set(Calendar.MONTH, 1);
			ca2.set(Calendar.HOUR, 0);
			ca2.set(Calendar.MINUTE, 0);
			ca2.set(Calendar.SECOND, 0);
			ca2.set(Calendar.MILLISECOND, 0);

			return compute(ca1.getTime(), ca2.getTime(), COMPUTE_YEAR, true);
		} catch (Exception e) {
			throw new SysLevelException(e.getMessage());
		}
	}

	/**
	 * 计算从dateBegin到dateEnd之间有多少年，忽略月份、天数和时分秒的差异
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param format
	 *            日期格式
	 * @param bWith
	 *            是否将dateEnd包含到计算结果中
	 * @return 年份间隔
	 * @author ChenHao
	 */
	public static int computeYearOnly(String dateBegin, String dateEnd, String format, boolean bWith) {
		int value = computeYearOnly(dateBegin, dateEnd, format);

		if (bWith) {
			Date begin = stringToDate(dateBegin, format);
			Date end = stringToDate(dateEnd, format);

			if (begin.after(end))
				value += 1;
			else if (begin.before(end))
				value -= 1;
			else
				value = 1;
		}

		return value;
	}

	/**
	 * 计算时间天数间隔，忽略月份、天数和时分秒的差异
	 * <p>
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。
	 * <p>
	 * 若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @return 年份间隔
	 * @author ChenHao
	 */
	public static int computeDateOnly(Date dateBegin, Date dateEnd) {
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(dateBegin);
		ca1.set(Calendar.HOUR, 0);
		ca1.set(Calendar.MINUTE, 0);
		ca1.set(Calendar.SECOND, 0);
		ca1.set(Calendar.MILLISECOND, 0);

		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(dateEnd);
		ca2.set(Calendar.HOUR, 0);
		ca2.set(Calendar.MINUTE, 0);
		ca2.set(Calendar.SECOND, 0);
		ca2.set(Calendar.MILLISECOND, 0);

		return compute(ca1.getTime(), ca2.getTime(), COMPUTE_DAY, true);
	}

	/**
	 * 计算从dateBegin到dateEnd之间有多少天，忽略时分秒的差异
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param bWith
	 *            是否将dateEnd包含到计算结果中
	 * @return 天数间隔
	 * @author ChenHao
	 */
	public static int computeDateOnly(Date dateBegin, Date dateEnd, boolean bWith) {
		int value = computeDateOnly(dateBegin, dateEnd);

		if (bWith) {
			if (dateBegin.after(dateEnd))
				value += 1;
			else if (dateBegin.before(dateEnd))
				value -= 1;
			else
				value = 1;
		}

		return value;
	}

	/**
	 * 计算时间天数间隔，忽略月份、天数和时分秒的差异
	 * <p>
	 * 注意：此方法仅用于计算日期之间<b>间隔<b>数。
	 * <p>
	 * 若用于表达日期之间有多少天/月/年，应按需要在结果之上±1（如期号的统计）
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param format
	 *            日期格式
	 * @return 年份间隔
	 * @author ChenHao
	 */
	public static int computeDateOnly(String dateBegin, String dateEnd, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			Calendar ca1 = Calendar.getInstance();
			ca1.setTime(sdf.parse(dateBegin));
			ca1.set(Calendar.HOUR, 0);
			ca1.set(Calendar.MINUTE, 0);
			ca1.set(Calendar.SECOND, 0);
			ca1.set(Calendar.MILLISECOND, 0);

			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(sdf.parse(dateEnd));
			ca2.set(Calendar.HOUR, 0);
			ca2.set(Calendar.MINUTE, 0);
			ca2.set(Calendar.SECOND, 0);
			ca2.set(Calendar.MILLISECOND, 0);

			return compute(ca1.getTime(), ca2.getTime(), COMPUTE_DAY, true);
		} catch (Exception e) {
			throw new SysLevelException(e.getMessage());
		}
	}

	/**
	 * 计算从dateBegin到dateEnd之间有多少天，忽略时分秒的差异
	 * 
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            终止时间
	 * @param format
	 *            日期格式
	 * @param bWith
	 *            是否将dateEnd包含到计算结果中
	 * @return 天数间隔
	 * @author ChenHao
	 */
	public static int computeDateOnly(String dateBegin, String dateEnd, String format, boolean bWith) {
		int value = computeDateOnly(dateBegin, dateEnd, format);

		if (bWith) {
			Date begin = stringToDate(dateBegin, format);
			Date end = stringToDate(dateEnd, format);

			if (begin.after(end))
				value += 1;
			else if (begin.before(end))
				value -= 1;
			else
				value = 1;
		}

		return value;
	}

	/**
	 * 得到当前时间
	 * 
	 * @yhcip:title
	 * @yhcip:desc
	 * @return
	 */
	public static Date nowDate() {
		return new Date();
	}

	/**
	 * 传入年月日时分秒得到java.util.Date 
	 * @yhcip:title
	 * @yhcip:desc
	 * @param month
	 * @param day
	 * @param year
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date toDate(int month, int day, int year, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.set(year, month - 1, day, hour, minute, second);
		} catch (Exception e) {
			return null;
		}
		return new Date(calendar.getTime().getTime());
	}

	/**
	 * 传入年月日时分秒得到java.util.Date
	 * 
	 * @yhcip:title
	 * @yhcip:desc
	 * @param monthStr
	 * @param dayStr
	 * @param yearStr
	 * @param hourStr
	 * @param minuteStr
	 * @param secondStr
	 * @return
	 */
	public static Date toDate(String monthStr, String dayStr, String yearStr, String hourStr, String minuteStr, String secondStr) {
		int month, day, year, hour, minute, second;

		try {
			month = Integer.parseInt(monthStr);
			day = Integer.parseInt(dayStr);
			year = Integer.parseInt(yearStr);
			hour = Integer.parseInt(hourStr);
			minute = Integer.parseInt(minuteStr);
			second = Integer.parseInt(secondStr);
		} catch (Exception e) {
			return null;
		}
		return toDate(month, day, year, hour, minute, second);
	}

	public static int getDaysInMonth(Calendar cal) {
		return getDaysInMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}

	public static int getDaysInMonth(int month, int year) {

		if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {

			return 31;
		} else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {

			return 30;
		} else {
			if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {

				return 29;
			} else {
				return 28;
			}
		}
	}

	public static int getLastDayOfWeek(Calendar cal) {
		int firstDayOfWeek = cal.getFirstDayOfWeek();

		if (firstDayOfWeek == Calendar.SUNDAY) {
			return Calendar.SATURDAY;
		} else if (firstDayOfWeek == Calendar.MONDAY) {
			return Calendar.SUNDAY;
		} else if (firstDayOfWeek == Calendar.TUESDAY) {
			return Calendar.MONDAY;
		} else if (firstDayOfWeek == Calendar.WEDNESDAY) {
			return Calendar.TUESDAY;
		} else if (firstDayOfWeek == Calendar.THURSDAY) {
			return Calendar.WEDNESDAY;
		} else if (firstDayOfWeek == Calendar.FRIDAY) {
			return Calendar.THURSDAY;
		}
		return Calendar.FRIDAY;
	}

	public static boolean isGregorianDate(int month, int day, int year) {
		if ((month < 0) || (month > 11)) {
			return false;
		}
		int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (month == 1) {
			int febMax = 28;
			if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {

				febMax = 29;
			}
			if ((day < 0) || (day > febMax)) {
				return false;
			}
		} else if ((day < 0) || (day > months[month])) {
			return false;
		}
		return true;
	}

	public static boolean isJulianDate(int month, int day, int year) {
		if ((month < 0) || (month > 11)) {
			return false;
		}
		int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (month == 1) {
			int febMax = 28;

			if ((year % 4) == 0) {
				febMax = 29;
			}

			if ((day < 0) || (day > febMax)) {
				return false;
			}
		} else if ((day < 0) || (day > months[month])) {
			return false;
		}
		return true;
	}

}
