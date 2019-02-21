package utils.excel_read_write;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.helper.StringUtil;

public class DateUtil extends DateUtils {
    public static final String TIMESTAMPFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMEFORMAT = "HH:mm:ss";
    public static final String DATEFORMAT = "yyyy-MM-dd";
    public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int COMPUTE_YEAR = 1;
    public static final int COMPUTE_MONTH = 2;
    public static final int COMPUTE_DAY = 5;

    public DateUtil() {
    }

    public static String getFullCurrentTime() {
        String returnStr = null;
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        returnStr = DateFormat.getDateTimeInstance().format(date);
        return returnStr;
    }

    public static String getCurrentTime() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    public static String getCurrentTime(String format) {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    public static Date getCurrentDateTime() {
        return new Date(System.currentTimeMillis());
    }

    public static int getWeekOfYear() {
        Calendar oneCalendar = Calendar.getInstance();
        return oneCalendar.get(3);
    }

    public static int getMonth() {
        Calendar oneCalendar = Calendar.getInstance();
        return oneCalendar.get(2) + 1;
    }

    public static String getCurDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpledateformat.format(calendar.getTime());
        return s;
    }

    public static Date getCurDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpledateformat.format(calendar.getTime());
        return java.sql.Date.valueOf(s);
    }

    public static java.sql.Date getDate() {
        Calendar oneCalendar = Calendar.getInstance();
        return getDate(oneCalendar.get(1), oneCalendar.get(2) + 1, oneCalendar.get(5));
    }

    public static java.sql.Date getDate(int yyyy, int MM, int dd) {
        if (!verityDate(yyyy, MM, dd)) {
            throw new IllegalArgumentException("This is illegimate date!");
        } else {
            Calendar oneCalendar = Calendar.getInstance();
            oneCalendar.clear();
            oneCalendar.set(yyyy, MM - 1, dd);
            return new java.sql.Date(oneCalendar.getTime().getTime());
        }
    }

    public static java.sql.Date getDate(String year, String month, String day) {
        int iYear = Integer.parseInt(year);
        int iMonth = Integer.parseInt(month);
        int iDay = Integer.parseInt(day);
        return getDate(iYear, iMonth, iDay);
    }

    public static Date getDate(int month, int day, int year, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.set(year, month - 1, day, hour, minute, second);
        } catch (Exception var8) {
            return null;
        }

        return new Date(calendar.getTime().getTime());
    }

    public static Date getDate(String month, String day, String year, String hour, String minute, String second) {
        int iYear = Integer.parseInt(year);
        int iMonth = Integer.parseInt(month);
        int iDay = Integer.parseInt(day);
        int iHour = Integer.parseInt(hour);
        int iMinute = Integer.parseInt(minute);
        int iSecond = Integer.parseInt(second);
        return getDate(iMonth, iDay, iYear, iHour, iMinute, iSecond);
    }

    public static boolean verityDate(int yyyy, int MM, int dd) {
        boolean flag = false;
        if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
            if (MM != 4 && MM != 6 && MM != 9 && MM != 11) {
                if (MM == 2) {
                    if ((yyyy % 100 == 0 || yyyy % 4 != 0) && yyyy % 400 != 0) {
                        if (dd <= 28) {
                            flag = true;
                        }
                    } else if (dd <= 29) {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
            } else if (dd <= 30) {
                flag = true;
            }
        }

        return flag;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        } else {
            String strDate = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            strDate = simpleDateFormat.format(date);
            return strDate;
        }
    }

    public static String datetimeToString(Date date) {
        if (date == null) {
            return null;
        } else {
            String strDate = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strDate = simpleDateFormat.format(date);
            return strDate;
        }
    }

    public static String datetimeToString(Date date, String format) {
        if (date == null) {
            return null;
        } else {
            String strDate = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            strDate = simpleDateFormat.format(date);
            return strDate;
        }
    }

    public static String timeToString(Date date) {
        if (date == null) {
            return null;
        } else {
            String strDate = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            strDate = simpleDateFormat.format(date);
            return strDate;
        }
    }

    public static Date stringToDate(String strDate, String srcDateFormat, String dstDateFormat) {
        Date rtDate = null;
        Date tmpDate = (new SimpleDateFormat(srcDateFormat)).parse(strDate, new ParsePosition(0));
        String tmpString = null;
        if (tmpDate != null) {
            tmpString = (new SimpleDateFormat(dstDateFormat)).format(tmpDate);
        }

        if (tmpString != null) {
            rtDate = (new SimpleDateFormat(dstDateFormat)).parse(tmpString, new ParsePosition(0));
        }

        return rtDate;
    }

    public static Date stringToDate(String strDate, String srcDateFormat) {
        return (new SimpleDateFormat(srcDateFormat)).parse(strDate, new ParsePosition(0));
    }

    public static Date stringToDate(String strDate) {
        return stringToDate(strDate, "yyyy-MM-dd");
    }

    public static java.sql.Date utilDateToSqlDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    public static Time utilDateToSqlTime(Date date) {
        return date == null ? null : new Time(date.getTime());
    }

    public static Timestamp utilDateToSqlTimestamp(Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static java.sql.Date stringToSqlDate(String strDate) {
        return stringToSqlDate(strDate, "yyyy-MM-dd");
    }

    public static java.sql.Date stringToSqlDate(String strDate, String srcDateFormat) {
        return stringToSqlDate(strDate, srcDateFormat, "yyyy-MM-dd");
    }

    public static java.sql.Date stringToSqlDate(String strDate, String srcDateFormat, String dstDateFormat) {
        return utilDateToSqlDate(stringToDate(strDate, srcDateFormat, dstDateFormat));
    }

    public static Time stringToSqlTime(String strDate) {
        return stringToSqlTime(strDate, "HH:mm:ss");
    }

    public static Time stringToSqlTime(String strDate, String srcDateFormat) {
        return stringToSqlTime(strDate, srcDateFormat, "HH:mm:ss");
    }

    public static Time stringToSqlTime(String strDate, String srcDateFormat, String dstDateFormat) {
        return utilDateToSqlTime(stringToDate(strDate, srcDateFormat, dstDateFormat));
    }

    public static Timestamp stringToSqlTimestamp(String strDate) {
        return stringToSqlTimestamp(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp stringToSqlTimestamp(String strDate, String srcDateFormat) {
        return stringToSqlTimestamp(strDate, srcDateFormat, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp stringToSqlTimestamp(String strDate, String srcDateFormat, String dstDateFormat) {
        return utilDateToSqlTimestamp(stringToDate(strDate, srcDateFormat, dstDateFormat));
    }

    public static String toTimeString(int hour, int minute, int second) {
        String hourStr;
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = "" + hour;
        }

        String minuteStr;
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }

        String secondStr;
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }

        return second == 0 ? hourStr + ":" + minuteStr : hourStr + ":" + minuteStr + ":" + secondStr;
    }

    public static java.sql.Date getDayStart(java.sql.Date date) {
        return getDayStart(date, 0);
    }

    public static java.sql.Date getDayStart(java.sql.Date date, int daysLater) {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(new Date(date.getTime()));
        tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.get(5), 0, 0, 0);
        tempCal.add(5, daysLater);
        return new java.sql.Date(tempCal.getTime().getTime());
    }

    public static java.sql.Date getNextDayStart(java.sql.Date date) {
        return getDayStart(date, 1);
    }

    public static java.sql.Date getDayEnd(java.sql.Date stamp) {
        return getDayEnd(stamp, 0);
    }

    public static java.sql.Date getDayEnd(java.sql.Date stamp, int daysLater) {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(new Date(stamp.getTime()));
        tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.get(5), 23, 59, 59);
        tempCal.add(5, daysLater);
        return new java.sql.Date(tempCal.getTime().getTime());
    }

    public static java.sql.Date monthBegin() {
        Calendar mth = Calendar.getInstance();
        mth.set(5, 1);
        mth.set(11, 0);
        mth.set(12, 0);
        mth.set(13, 0);
        mth.set(9, 0);
        return new java.sql.Date(mth.getTime().getTime());
    }

    public static String getDateTimeDisp(String datetime) {
        if (datetime != null && !datetime.equals("")) {
            DateFormat formatter = DateFormat.getDateTimeInstance(2, 2);
            long datel = Long.parseLong(datetime);
            return formatter.format(new Date(datel));
        } else {
            return "";
        }
    }

    public static boolean isYear(String year) {
        return StringUtil.isNumeric(year) && year != null && year.length() == 4;
    }

    public static boolean isMonth(String month) {
        if (month == null) {
            return false;
        } else if (month.length() != 1 && month.length() != 2) {
            return false;
        } else if (!StringUtil.isNumeric(month)) {
            return false;
        } else {
            int iMonth = Integer.parseInt(month);
            return iMonth >= 1 && iMonth <= 12;
        }
    }

    public static boolean isDay(String day) {
        if (day == null) {
            return false;
        } else if (day.length() != 1 && day.length() != 2) {
            return false;
        } else if (!StringUtil.isNumeric(day)) {
            return false;
        } else {
            int iDay = Integer.parseInt(day);
            return iDay >= 1 && iDay <= 31;
        }
    }

    public static boolean isHour(String hour) {
        if (hour == null) {
            return false;
        } else if (hour.length() != 1 && hour.length() != 2) {
            return false;
        } else if (!StringUtil.isNumeric(hour)) {
            return false;
        } else {
            int iHour = Integer.parseInt(hour);
            return iHour >= 0 && iHour <= 23;
        }
    }

    public static boolean isMinute(String minute) {
        if (minute == null) {
            return false;
        } else if (minute.length() != 1 && minute.length() != 2) {
            return false;
        } else if (!StringUtil.isNumeric(minute)) {
            return false;
        } else {
            int iMinute = Integer.parseInt(minute);
            return iMinute >= 0 && iMinute <= 59;
        }
    }

    public static boolean isSecond(String second) {
        if (second == null) {
            return false;
        } else if (second.length() != 1 && second.length() != 2) {
            return false;
        } else if (!StringUtil.isNumeric(second)) {
            return false;
        } else {
            int iSecond = Integer.parseInt(second);
            return iSecond >= 0 && iSecond <= 59;
        }
    }

    public static boolean isDate(String year, String month, String day) {
        if (!isYear(year)) {
            return false;
        } else if (!isMonth(month)) {
            return false;
        } else if (!isDay(day)) {
            return false;
        } else {
            int iYear = Integer.parseInt(year);
            int iMonth = Integer.parseInt(month);
            int iDay = Integer.parseInt(day);
            return verityDate(iYear, iMonth, iDay);
        }
    }

    public static boolean isDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return false;
        } else {
            char divide = 45;
            int index = date.indexOf(divide);
            if (index < 0) {
                return false;
            } else {
                String year = date.substring(0, index);
                int index2 = date.indexOf(divide, index + 1);
                if (index2 < 0) {
                    return false;
                } else {
                    String month = date.substring(index + 1, index2);
                    String day = date.substring(index2 + 1);
                    return isDate(year, month, day);
                }
            }
        }
    }

    private static int compute(String arg1, String arg2, int computeFlag, String format, boolean bExact) {
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat sdf = null;
        if (format != null && format.trim().length() > 0) {
            sdf = new SimpleDateFormat(format);
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }

        try {
            date1 = sdf.parse(arg1);
            date2 = sdf.parse(arg2);
            return compute(date1, date2, computeFlag, bExact);
        } catch (Exception var9) {
            throw new SysLevelException(var9.getMessage());
        }
    }

    private static int compute(Date arg1, Date arg2, int computeFlag, boolean bExact) {
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(arg1);
        ca1.set(10, 0);
        ca1.set(12, 0);
        ca1.set(13, 0);
        ca1.set(14, 0);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(arg2);
        ca2.set(10, 0);
        ca2.set(12, 0);
        ca2.set(13, 0);
        ca2.set(14, 0);
        int elapsed = 0;
        if (ca1.after(ca2)) {
            while (ca1.after(ca2)) {
                ca1.add(computeFlag, -1);
                --elapsed;
            }

            if (bExact) {
                if (2 == computeFlag && ca1.get(5) != ca2.get(5)) {
                    ++elapsed;
                }

                if (1 == computeFlag) {
                    if (ca1.get(2) != ca2.get(2)) {
                        ++elapsed;
                    } else if (ca1.get(5) != ca2.get(5)) {
                        ++elapsed;
                    }
                }
            }

            return -elapsed;
        } else if (!ca1.before(ca2)) {
            return 0;
        } else {
            while (ca1.before(ca2)) {
                ca1.add(computeFlag, 1);
                ++elapsed;
            }

            if (bExact) {
                if (2 == computeFlag && ca1.get(5) != ca2.get(5)) {
                    --elapsed;
                }

                if (1 == computeFlag) {
                    if (ca1.get(2) > ca2.get(2)) {
                        --elapsed;
                    } else if (ca1.get(5) != ca2.get(5)) {
                        --elapsed;
                    }
                }
            }

            return -elapsed;
        }
    }

    public static int computeYear(String arg1, String arg2, String format, boolean bExact) {
        return compute(arg1, arg2, 1, format, bExact);
    }

    public static int computeYear(Date arg1, Date arg2, boolean bExact) {
        return compute(arg1, arg2, 1, bExact);
    }

    public static int computeMonth(String arg1, String arg2, String format, boolean bExact) {
        return compute(arg1, arg2, 2, format, bExact);
    }

    public static int computeMonth(Date arg1, Date arg2, boolean bExact) {
        return compute(arg1, arg2, 2, bExact);
    }

    public static int computeDay(String arg1, String arg2, String format) {
        return compute(arg1, arg2, 5, format, true);
    }

    public static int computeDay(Date arg1, Date arg2) {
        return compute(arg1, arg2, 5, true);
    }

    public static String fnGetStr4Y2M(String szStr) {
        String szRst = szStr.replaceAll("[ \\|\\-:\\.]", "");
        szRst = szRst.substring(0, 6);
        return szRst;
    }

    public static int getIntervalMonth(String startDate, String endDate) {
        if (startDate.length() > 6) {
            startDate = fnGetStr4Y2M(startDate);
        }

        if (endDate.length() > 6) {
            endDate = fnGetStr4Y2M(endDate);
        }

        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int startMonth;
        if (startDate.substring(4, 5).equals("0")) {
            startMonth = Integer.parseInt(startDate.substring(5));
        }

        startMonth = Integer.parseInt(startDate.substring(4, 6));
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        int endMonth;
        if (endDate.substring(4, 5).equals("0")) {
            endMonth = Integer.parseInt(endDate.substring(5));
        }

        endMonth = Integer.parseInt(endDate.substring(4, 6));
        int intervalMonth = endYear * 12 + endMonth - (startYear * 12 + startMonth);
        return intervalMonth;
    }

    public static int getIntervalMonth(Date startDate, Date endDate) {
        return computeMonthOnly(startDate, endDate);
    }

    public static int getIntervalDay(java.sql.Date startDate, java.sql.Date endDate) {
        long startdate = startDate.getTime();
        long enddate = endDate.getTime();
        long interval = enddate - startdate;
        int intervalday = (int) (interval / 86400000L);
        return intervalday;
    }

    public static int getIntervalDay(Timestamp startDate, Timestamp endDate) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(startDate.getTime());
        calendar2.setTimeInMillis(endDate.getTime());
        calendar1.set(calendar1.get(1), calendar1.get(2), calendar1.get(5), 0, 0, 0);
        calendar2.set(calendar2.get(1), calendar2.get(2), calendar2.get(5), 0, 0, 0);
        long startdate = calendar1.getTimeInMillis();
        long enddate = calendar2.getTimeInMillis();
        long interval = enddate - startdate;
        int intervalday = (int) (interval / 86400000L);
        return intervalday;
    }

    public static int computeMonthOnly(Date dateBegin, Date dateEnd) {
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(dateBegin);
        ca1.set(5, 1);
        ca1.set(10, 0);
        ca1.set(12, 0);
        ca1.set(13, 0);
        ca1.set(14, 0);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(dateEnd);
        ca2.set(5, 1);
        ca2.set(10, 0);
        ca2.set(12, 0);
        ca2.set(13, 0);
        ca2.set(14, 0);
        return compute(ca1.getTime(), ca2.getTime(), 2, true);
    }

    public static int computeMonthOnly(Date dateBegin, Date dateEnd, boolean bWith) {
        int value = computeMonthOnly(dateBegin, dateEnd);
        if (bWith) {
            if (dateBegin.after(dateEnd)) {
                ++value;
            } else if (dateBegin.before(dateEnd)) {
                --value;
            } else {
                value = 1;
            }
        }

        return value;
    }

    public static int computeMonthOnly(String dateBegin, String dateEnd, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Calendar ca1 = Calendar.getInstance();
            ca1.setTime(sdf.parse(dateBegin));
            ca1.set(5, 1);
            ca1.set(10, 0);
            ca1.set(12, 0);
            ca1.set(13, 0);
            ca1.set(14, 0);
            Calendar ca2 = Calendar.getInstance();
            ca2.setTime(sdf.parse(dateEnd));
            ca2.set(5, 1);
            ca2.set(10, 0);
            ca2.set(12, 0);
            ca2.set(13, 0);
            ca2.set(14, 0);
            return compute(ca1.getTime(), ca2.getTime(), 2, true);
        } catch (Exception var6) {
            throw new SysLevelException(var6.getMessage());
        }
    }

    public static int computeMonthOnly(String dateBegin, String dateEnd, String format, boolean bWith) {
        int value = computeMonthOnly(dateBegin, dateEnd, format);
        if (bWith) {
            Date begin = stringToDate(dateBegin, format);
            Date end = stringToDate(dateEnd, format);
            if (begin.after(end)) {
                ++value;
            } else if (begin.before(end)) {
                --value;
            } else {
                value = 1;
            }
        }

        return value;
    }

    public static int computeYearOnly(Date dateBegin, Date dateEnd) {
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(dateBegin);
        ca1.set(5, 1);
        ca1.set(2, 1);
        ca1.set(10, 0);
        ca1.set(12, 0);
        ca1.set(13, 0);
        ca1.set(14, 0);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(dateEnd);
        ca2.set(5, 1);
        ca2.set(2, 1);
        ca2.set(10, 0);
        ca2.set(12, 0);
        ca2.set(13, 0);
        ca2.set(14, 0);
        return compute(ca1.getTime(), ca2.getTime(), 1, true);
    }

    public static int computeYearOnly(Date dateBegin, Date dateEnd, boolean bWith) {
        int value = computeYearOnly(dateBegin, dateEnd);
        if (bWith) {
            if (dateBegin.after(dateEnd)) {
                ++value;
            } else if (dateBegin.before(dateEnd)) {
                --value;
            } else {
                value = 1;
            }
        }

        return value;
    }

    public static int computeYearOnly(String dateBegin, String dateEnd, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Calendar ca1 = Calendar.getInstance();
            ca1.setTime(sdf.parse(dateBegin));
            ca1.set(5, 1);
            ca1.set(2, 1);
            ca1.set(10, 0);
            ca1.set(12, 0);
            ca1.set(13, 0);
            ca1.set(14, 0);
            Calendar ca2 = Calendar.getInstance();
            ca2.setTime(sdf.parse(dateEnd));
            ca2.set(5, 1);
            ca2.set(2, 1);
            ca2.set(10, 0);
            ca2.set(12, 0);
            ca2.set(13, 0);
            ca2.set(14, 0);
            return compute(ca1.getTime(), ca2.getTime(), 1, true);
        } catch (Exception var6) {
            throw new SysLevelException(var6.getMessage());
        }
    }

    public static int computeYearOnly(String dateBegin, String dateEnd, String format, boolean bWith) {
        int value = computeYearOnly(dateBegin, dateEnd, format);
        if (bWith) {
            Date begin = stringToDate(dateBegin, format);
            Date end = stringToDate(dateEnd, format);
            if (begin.after(end)) {
                ++value;
            } else if (begin.before(end)) {
                --value;
            } else {
                value = 1;
            }
        }

        return value;
    }

    public static int computeDateOnly(Date dateBegin, Date dateEnd) {
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(dateBegin);
        ca1.set(10, 0);
        ca1.set(12, 0);
        ca1.set(13, 0);
        ca1.set(14, 0);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(dateEnd);
        ca2.set(10, 0);
        ca2.set(12, 0);
        ca2.set(13, 0);
        ca2.set(14, 0);
        return compute(ca1.getTime(), ca2.getTime(), 5, true);
    }

    public static int computeDateOnly(Date dateBegin, Date dateEnd, boolean bWith) {
        int value = computeDateOnly(dateBegin, dateEnd);
        if (bWith) {
            if (dateBegin.after(dateEnd)) {
                ++value;
            } else if (dateBegin.before(dateEnd)) {
                --value;
            } else {
                value = 1;
            }
        }

        return value;
    }

    public static int computeDateOnly(String dateBegin, String dateEnd, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Calendar ca1 = Calendar.getInstance();
            ca1.setTime(sdf.parse(dateBegin));
            ca1.set(10, 0);
            ca1.set(12, 0);
            ca1.set(13, 0);
            ca1.set(14, 0);
            Calendar ca2 = Calendar.getInstance();
            ca2.setTime(sdf.parse(dateEnd));
            ca2.set(10, 0);
            ca2.set(12, 0);
            ca2.set(13, 0);
            ca2.set(14, 0);
            return compute(ca1.getTime(), ca2.getTime(), 5, true);
        } catch (Exception var6) {
            throw new SysLevelException(var6.getMessage());
        }
    }

    public static int computeDateOnly(String dateBegin, String dateEnd, String format, boolean bWith) {
        int value = computeDateOnly(dateBegin, dateEnd, format);
        if (bWith) {
            Date begin = stringToDate(dateBegin, format);
            Date end = stringToDate(dateEnd, format);
            if (begin.after(end)) {
                ++value;
            } else if (begin.before(end)) {
                --value;
            } else {
                value = 1;
            }
        }

        return value;
    }

    public static Date nowDate() {
        return new Date();
    }

    public static Date toDate(int month, int day, int year, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.set(year, month - 1, day, hour, minute, second);
        } catch (Exception var8) {
            return null;
        }

        return new Date(calendar.getTime().getTime());
    }

    public static Date toDate(String monthStr, String dayStr, String yearStr, String hourStr, String minuteStr, String secondStr) {
        int month;
        int day;
        int year;
        int hour;
        int minute;
        int second;
        try {
            month = Integer.parseInt(monthStr);
            day = Integer.parseInt(dayStr);
            year = Integer.parseInt(yearStr);
            hour = Integer.parseInt(hourStr);
            minute = Integer.parseInt(minuteStr);
            second = Integer.parseInt(secondStr);
        } catch (Exception var13) {
            return null;
        }

        return toDate(month, day, year, hour, minute, second);
    }

    public static int getDaysInMonth(Calendar cal) {
        return getDaysInMonth(cal.get(2), cal.get(1));
    }

    public static int getDaysInMonth(int month, int year) {
        if (month != 1 && month != 3 && month != 5 && month != 7 && month != 8 && month != 10 && month != 12) {
            if (month != 4 && month != 6 && month != 9 && month != 11) {
                return (year % 4 != 0 || year % 100 == 0) && year % 400 != 0 ? 28 : 29;
            } else {
                return 30;
            }
        } else {
            return 31;
        }
    }

    public static int getLastDayOfWeek(Calendar cal) {
        int firstDayOfWeek = cal.getFirstDayOfWeek();
        if (firstDayOfWeek == 1) {
            return 7;
        } else if (firstDayOfWeek == 2) {
            return 1;
        } else if (firstDayOfWeek == 3) {
            return 2;
        } else if (firstDayOfWeek == 4) {
            return 3;
        } else if (firstDayOfWeek == 5) {
            return 4;
        } else {
            return firstDayOfWeek == 6 ? 5 : 6;
        }
    }

    public static boolean isGregorianDate(int month, int day, int year) {
        if (month >= 0 && month <= 11) {
            int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (month != 1) {
                if (day < 0 || day > months[month]) {
                    return false;
                }
            } else {
                int febMax = 28;
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    febMax = 29;
                }

                if (day < 0 || day > febMax) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isJulianDate(int month, int day, int year) {
        if (month >= 0 && month <= 11) {
            int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (month == 1) {
                int febMax = 28;
                if (year % 4 == 0) {
                    febMax = 29;
                }

                if (day < 0 || day > febMax) {
                    return false;
                }
            } else if (day < 0 || day > months[month]) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
}