package utils;

import java.util.Random;

public class AutoGenerateStrUtil {

    /**
     * TODO:参数改为boolean型
     * 生成length长度的随机字符串
     * @param lowercase 参数示例：true
     * @param uppercase 参数示例：true
     * @param digit 参数示例：true
     * @param othercase 参数示例：true
     * @param length 50
     * @return
     */
    public static String getRandomString(String lowercase, String uppercase, String digit, String othercase, int length){
        StringBuilder str = new StringBuilder();
        String lowercaseStr = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digitStr = "0123456789";
        String othercaseStr = "!$%@#";
        String data[] = {lowercase, uppercase, digit, othercase};
        String data2[] = {lowercaseStr, uppercaseStr, digitStr, othercaseStr};
        for (int i = 0; i < data.length; i++){
            if ("true".equals(data[i])){
                str.append(data2[i]);
            }
        }
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int strLength = str.length();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(strLength);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args){
    String s = getRandomString("true","true","true","true",50);
    System.out.println(s);
    }

}
