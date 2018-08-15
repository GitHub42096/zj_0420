package orj.com.jsoup_demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 爬取实习僧app数据
 * 参考：https://blog.csdn.net/liuty66/article/details/80626324
 * github: https://github.com/ty33123/spider_learn/blob/master/shixisheng/src/shixisheng/shixiseng.java
 */
public class JsoupShixiseng {
    public static void main(String[] args){
        try {
            URL url = new URL("https://androidapi.shixiseng.com/app/interns/search?k=java&c=%E5%85%A8%E5%9B%BD&s=-0&d=&page="+1+"&m=&x=&z=&st=intern&ft=&t=zj");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
            conn.setRequestProperty("Host", "androidapi.shixiseng.com");
            conn.setRequestProperty("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.0; Meizu S6 Build/NRD90M)");
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String html = sb.toString();
            System.out.println(html);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
