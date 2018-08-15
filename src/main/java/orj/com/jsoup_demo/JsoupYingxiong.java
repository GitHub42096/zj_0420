package orj.com.jsoup_demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 爬取app数据：英雄一起来飞车
 * 参考：https://blog.csdn.net/liuty66/article/details/80626324
 * github: https://github.com/ty33123/spider_learn/blob/master/shixisheng/src/shixisheng/shixiseng.java
 */
public class JsoupYingxiong {
    public static void main(String[] args){
        try {
            URL url = new URL("https://hplsdk.yingxiong.com/dc6/sys/mod/conf");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Host", "hplsdk.yingxiong.com");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);//设置是否向connection输出
            conn.setDoInput(true);//Read from the connection. Default is true.

            conn.setRequestProperty("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.0; Meizu S6 Build/NRD90M)");
            String postData = "mod=color&vsn=20180125&vo=7.1.1&vg=2.1.1&cid=130142001232&vl=0&plat=1&vc=85&ts=1534328870&gid=6&imei=866935036477073&lang=zh_cn&vs=41&devNum=d6b69ac6-d6f5-36a7-afef-8e4fb20f95ab&pkg=com.wyd.hero.yqlfc.cb1.vivo&sign=73debc6b34789c067ef4fee0cd5673d1";

            conn.getOutputStream().write(postData.getBytes());
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String jsonString = sb.toString();
            analyse(jsonString);
            System.out.println(jsonString);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *         String s = "{\"color\":{\"color\":\"\",\"position\":\"bottom\"},\"r_msg\":\"SUCCESS\",\"refresh\":{\"time\":0,\"open\":0},\"r_code\":0,\"colors\":[{\"field\":\"spread\",\"color\":\"#272f3f\",\"position\":\"bottom\"},{\"field\":\"tactic\",\"color\":\"#272f3f\",\"position\":\"left\"},{\"field\":\"club\",\"color\":\"#272f3f\",\"position\":\"bottom\"},{\"field\":\"pray\",\"color\":\"#272f3f\",\"position\":\"bottom\"},{\"field\":\"shop\",\"color\":\"#272f3f\",\"position\":\"bottom\"},{\"field\":\"social\",\"color\":\"#272f3f\",\"position\":\"left\"}],\"spreadConfs\":[{\"appId\":\"wxfa8dbee10d4f3be4\",\"channel\":\"1\",\"appSecret\":\"33d288579170615be4f6d2b30528c687\",\"type\":1,\"appstoreName\":\"vivo\"},{\"appId\":\"1105811177\",\"channel\":\"3\",\"appSecret\":\"FkzXtf8kwdsgC5VS\",\"type\":1}]}\n";
     * @param jsonString
     */
    private static void analyse(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
       JSONObject color = jsonObject.getJSONObject("color");
        System.out.println(color);

    }
}
