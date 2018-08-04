package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 时间字符串转时间戳（秒）方法1
     */
    public static String getTimeStamp(String strTime, String pattern) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d;
        try {
            return String.valueOf(sdf.parse(strTime).getTime()/1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 时间字符串转时间戳（秒）方法2
     */
    public static String getTimeStamp2(String strTime, String pattern) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d;
        try {
            d = sdf.parse(strTime);
            long l = d.getTime();
            String str = String.valueOf(l);
            time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 计算两个日期之间相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String[] args){
        System.out.println(DateUtil.getTimeStamp("2018-08-03 17:20:12", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.getTimeStamp2("2018-08-03 17:20:12", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(System.currentTimeMillis()/1000); // 毫秒除以1000为秒
    }
}
