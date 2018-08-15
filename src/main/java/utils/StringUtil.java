package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    static Pattern PHONE = Pattern.compile("^1[0-9]{10}$");
//	static Pattern PHONE = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");

    /**
     * 返回交换后的字符串
     * @param str
     * @param oneIndex 开始位置：0、1····
     * @param anotherIndex
     * @return
     */
    public static String exchangeStr(String str, int oneIndex, int anotherIndex) {
        if (str != null && 0 < str.length() && 0 <= oneIndex && oneIndex < anotherIndex && anotherIndex < str.length()){
            StringBuilder sb = new StringBuilder(str);
            char temp = sb.charAt(oneIndex);
            sb.setCharAt(oneIndex, sb.charAt(anotherIndex));
            sb.setCharAt(anotherIndex, temp);
            return sb.toString();
        }
        return null;
    }

    /**
     * 产生随机数字串::
     */
    public static String genRandomCode(int len) {
        String rnd = String.valueOf(Math.random()).substring(2);
        while (rnd.length() < len) {
            rnd += String.valueOf(Math.random()).substring(2);
        }
        return rnd.substring(0, len);
    }

    /*
     * 产生6位随机数字串
     */
    public static String getRandomPassword() {
        return genRandomCode(6);
    }


    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb=new StringBuffer();
        Pattern pattern= Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
        return sb.toString();
    }

    public static String delHtmlMark(String s){
        if (s == null){
            return "";
        }
        String res = s.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", " ");
        return res;
    }

    // 是否是手机号
    public static boolean isMobileNum(String mobileNum) {
        if (mobileNum == null || mobileNum.length() == 0)
            return false;
        Matcher m = PHONE.matcher(mobileNum);
        return m.matches();
    }



    public static void main(String[] args){
        String s = getRandomPassword();
        System.out.println(s);

    }

}
