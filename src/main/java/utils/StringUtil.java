package utils;

public class StringUtil {

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

    public static void main(String[] args){
    String s = getRandomPassword();
    System.out.println(s);
    }

}
