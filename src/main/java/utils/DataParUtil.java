package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataParUtil {
    /**
     * 时间转换
     * 转为 n秒前   n分钟前   n小时前  日期
     * @param time
     * @param format  很长时间前显示的日期格式
     * @return
     */
    public static String dataLongToSNS(long time, String format){
        long now = System.currentTimeMillis();

        long diff = now - time;
        diff = diff / 1000;// 秒

        if(diff < 0){
            return dateLongToString(time, format);
        }

        if(diff < 30){ // 30秒
            return "刚刚";
        }

        if(diff < 60){
            return String.format("%s秒前", diff);
        }

        if(diff < 3600){
            return String.format("%s分钟前", diff / 60);
        }
        //获取今天凌晨时间
        long todayStart = getMoring(new Date()).getTime();

        if(time >= todayStart){// 今天
            return String.format("%s小时前", diff / 3600);
        }

        if(time<todayStart && time >= todayStart - 86400000){
            return "昨天 " + dateLongToString(time, "HH:mm");
        }

        if(diff < 30L*60*60*24){
            return diff/(60*60*24) +"天前";
        }

        if(diff < 365L*60*60*24){
            return diff/(30L*60*60*24) +"个月前";
        }

        if(diff >= 365L*60*60*24){
            return diff/(365L*60*60*24) +"年前";
        }

        return dateLongToString(time, format);
    }


    //获取今天凌晨的时间
    private static Date getMoring(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String dateLongToString(long time){
        return dateLongToString(time,null);
    }

    public static String dateLongToString(long time, String format){
        if(time <= 0){
            return "Empty";
        }
        DateFormat format2 = new SimpleDateFormat(format);
        String dateString = format2.format(new Date(time));
        return dateString;
    }

    public static void main(String[] args){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse("2018-01-12 11:52:35");
            System.out.println( dataLongToSNS(date.getTime(), "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
