package orj.com.jsoup_demo;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class A {
    private static final String DEFAULT_ENCODING          = "utf-8";
    private static CloseableHttpClient httpClient;
    private static Logger log = LoggerFactory.getLogger(A.class);
    public static void main(String[] args){



        if (StringUtils.isNotBlank("511523199601186781")) {

            StringBuffer sbIdCard = new StringBuffer("");

            sbIdCard.append("511523199601186781");
            if (sbIdCard.length() > 14) {
                sbIdCard.replace(0, 14, "**************");
            }
            System.out.println(sbIdCard.toString());
            System.out.println("**************".length());
        }

        String url ="http://47.100.76.43:18002/send.do?uid=000348&pw=909432&mb=18483679592&ms=【英雄互娱】亲爱的用户 testwsw090505 ，您找回密码的验证码是9668，30分钟内有效&tm=20180907114041&dm=";
        System.out.println(url.replace(" ", ""));
        System.out.println(url.replace("，", ","));

        String phone = "U104";
        // 处理账号 隐藏部分
        StringBuffer sb = new StringBuffer("");
        sb.append(phone);

            // 隐藏中间四位
             String Pentagram = "";
                sb.replace(0, sb.length()-4, "*******");

            System.out.println(sb);

            Byte b = 1;
            System.out.println(b);

        try {
             url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URI uri = URI.create(url);
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader(HttpHeaders.CONTENT_ENCODING, DEFAULT_ENCODING);
        try {
            executeMethod(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String executeMethod(HttpUriRequest method) throws Exception {
        CloseableHttpResponse resp = null;
        try {
            resp = httpClient.execute(method);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_ENCODING);
            }
            log.info("http_status_code-->" + statusCode);
        } catch (Exception e) {
            StringBuilder errLog = new StringBuilder();
            errLog.append("httpRequestError-->{")
                    .append("uri=")
                    .append(method.getURI())
                    .append(", method=")
                    .append(method.getMethod())
                    .append("}");
            log.error(errLog.toString(), e);
            throw new Exception(e);
        } finally {
            if (resp != null) {
                try {
                    resp.close();
                } catch (IOException e) {
                    log.error("close response failed", e);
                }
            }
        }
        return null;
    }

}
