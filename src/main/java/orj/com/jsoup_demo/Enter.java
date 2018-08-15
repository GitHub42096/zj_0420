package orj.com.jsoup_demo;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * jsoup 爬取网站数据
 */
public class Enter {
    public static void main(String[] args) {
        Document doc = null;
        try {
            String url = "https://www.taptap.com/mobile";
            Connection conn = Jsoup.connect(url).timeout(5000);
            conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.header("Accept-Encoding", "gzip, deflate, sdch");
            conn.header("Accept-Language", "zh-CN,zh;q=0.8");
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            doc = conn.get();
            Elements elements = doc.getElementsByTag("body");
            for (Element result : elements) {
                Elements links = result.getElementsByTag("a");
                for (Element link : links) {
                    //必要的筛选
                    String linkHref = link.attr("href");
                    String linkText = link.text();
                    System.out.println(linkHref);
                    System.out.println(linkText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
