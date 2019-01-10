package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDateUtil {
    /**
     * 锁对象
     */
    private static final Object lockObj = new Object();

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();


    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    System.out.println("put new sdf of pattern " + pattern + " to map");

                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {

                        @Override
                        protected SimpleDateFormat initialValue() {
                            System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }

        return tl.get();
    }

    /**
     * 使用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     * 如果新的线程中没有SimpleDateFormat，才会new一个
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }



    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(){
            @Override
            public void run() {
                LocalDateUtil.format(new Date(), "yyyy-MM-dd");
            };
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                LocalDateUtil.format(new Date(), "yyyy-MM-dd");
            };
        };

        Thread t3 = new Thread(){
            @Override
            public void run() {
                LocalDateUtil.format(new Date(), "yyyy-MM-dd");
            };
        };

        Thread t4 = new Thread(){
            @Override
            public void run() {
                try{
                    LocalDateUtil.parse("2017-06-10 12:00:01", "yyyy-MM-dd HH:mm:ss");
                }catch(ParseException e){
                    e.printStackTrace();
                }

            };
        };

        Thread t5 = new Thread(){
            @Override
            public void run() {
                try{
                    LocalDateUtil.parse("2017-06-10 12:00:01", "yyyy-MM-dd HH:mm:ss");
                }catch(ParseException e){
                    e.printStackTrace();
                }

            };
        };


        System.out.println("单线程执行：");
        ExecutorService exec1 = Executors.newFixedThreadPool(1);
        exec1.execute(t1);
        exec1.execute(t2);
        exec1.execute(t3);
        exec1.execute(t4);
        exec1.execute(t5);
        exec1.shutdown();

        Thread.sleep(1000);

        System.out.println("双线程执行：");
        ExecutorService exec2 = Executors.newFixedThreadPool(2);
        exec2.execute(t1);
        exec2.execute(t2);
        exec2.execute(t3);
        exec2.execute(t4);
        exec2.execute(t5);
        exec2.shutdown();

    }
}
