package orj.com.jsoup_demo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 获取汽车票余量信息
 * created by zj
 */
public class TestTmp {

    public static void main(String[] args) throws Exception {
    //运行该代码前需要生成证书文件放到jre的security目录下，具体操作参照
    //http://blog.csdn.net/faye0412/article/details/6883879/
    //站点库代码参照：https://kyfw.12306.cn/otn/resources/js/framework/station_name.js

    String trainDate = "2018-08-14"; //乘车日期
    String fromStation = "CDW"; //成都
    String toStation = "SHH"; //上海
    getHttp(trainDate, fromStation, toStation);
    }

    /**
     * @param trainDate   乘车日期
     * @param fromStation 起点站代码
     * @param toStation   终点站代码
     * @return void 无
     * @author liuyong
     * @serialData 2016年11月30日12:55:58
     */
    public static void getHttp(String trainDate, String fromStation, String toStation) throws Exception {

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        URL reqURL = new URL(
                "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+ trainDate+"&leftTicketDTO.from_station="+fromStation+"&leftTicketDTO.to_station="+toStation+"&purpose_codes=ADULT"); //
        HttpsURLConnection httpsConn = (HttpsURLConnection) reqURL.openConnection();
        InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream());
        String jsonstring = "";
        int respInt = insr.read();
        while (respInt != -1) {
            jsonstring += String.valueOf((char) respInt);
            //System.out.print(((char) respInt));
            respInt = insr.read();
        }
        jsonObject = JSONObject.fromObject(jsonstring);
        //System.out.println(formatJson(jsonstring));

        //System.out.println(jsonObject.get("data"));
        jsonArray = JSONArray.fromObject(jsonObject.get("data"));
        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject tmpJsonBoj = jsonArray.getJSONObject(i);
            JSONObject finalJsonObj = (JSONObject) tmpJsonBoj.get("map");
            String result = tmpJsonBoj.getString("result");
            //String trainCode = finalJsonObj.getString("station_train_code");
            String startStation = finalJsonObj.getString("CDW");
            String endStation = finalJsonObj.getString("SHH");
            String resultAgian[] = result.split(",");
            for (int j = 1; j < resultAgian.length; j++){

                String resultAttr[] = resultAgian[j].split("[|]");
                String trainCode = resultAttr[3];
                String satrtTime = resultAttr[8];
                String arrivalTime = resultAttr[9];
                String lishi = resultAttr[10];

                System.out.printf("车次：" + trainCode + "  ");
                System.out.printf("出发站：" + startStation + "  ");
                System.out.printf("终点站：" + endStation + "  ");
                System.out.printf("出发时间：" + satrtTime + "  ");
                System.out.printf("到达时间：" + arrivalTime + "  ");
                System.out.printf("历时：" + lishi);
                System.out.println();
                //剩下输出信息根据需要自己找吧
            }
        }
    }

    /**
     * 格式化json字符串，参照别人的代码，暂时没有用到
     *
     * @param jsonStr
     * @return
     * @author
     * @Date
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }


        return sb.toString();
    }


    /**
         * 添加space
         *
         * @param
         * @param
         * @author
         * @Date
         */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}