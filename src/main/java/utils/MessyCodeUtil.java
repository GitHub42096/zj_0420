package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessyCodeUtil {
    /**

     * 判断字符串是否乱码

     * @author yang.shen

     * @param strName

     * @return boolean

     */

    public static boolean isMessyCode(String strName) {

        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");

        Matcher m = p.matcher(strName);

        String after = m.replaceAll("");

        String temp = after.replaceAll("\\p{P}", "");

        char[] ch = temp.trim().toCharArray();

        float chLength = 0;

        float count = 0;

        for (int i = 0; i < ch.length; i++) {

            char c = ch[i];

            if (!Character.isLetterOrDigit(c)) {

                if (!isChinese(c)) {

                    count = count + 1;

                }
                chLength++;
            }
        }

        float result = count / chLength;

        if (result > 0.4) {

            return true;

        } else {

            return false;

        }

    }


    private static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;

        }

        return false;

    }

    public static void main(String[] args) {
        System.out.println(isMessyCode("Ã©Å¸Â©Ã©Â¡ÂºÃ¥Â¹Â³"));
        System.out.println(isMessyCode("集群02"));
    }
}
