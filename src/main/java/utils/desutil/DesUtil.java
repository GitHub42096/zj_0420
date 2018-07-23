package utils.desutil;
import java.nio.charset.Charset;
import java.text.ParseException;

public class DesUtil {

    /**
     * 加密
     * @param srcStr
     * @param charset
     * @param sKey
     * @return
     */
    public static String encrypt(String srcStr, Charset charset, String sKey) {
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = Des.encrypt(src, sKey);
        return Des.parseByte2HexStr(buf);
    }

    /**
     * 解密
     *
     * @param hexStr
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String hexStr, Charset charset, String sKey) throws Exception {
        byte[] src = Des.parseHexStr2Byte(hexStr);
        byte[] buf = Des.decrypt(src, sKey);
        return new String(buf, charset);
    }



    public static void main(String[] args) throws ParseException {
        final String  SKEY    = "key12345";
        final Charset CHARSET = Charset.forName("UTF-8");
        // 待加密内容
        String str = "我是加密内容";
        String encryptResult = DesUtil.encrypt(str, CHARSET, SKEY);
        System.out.println(encryptResult);
        // 直接将如上内容解密
        StringBuilder decryResult = new StringBuilder("");
        try {
            decryResult.append(DesUtil.decrypt(encryptResult, CHARSET, SKEY));
            System.out.println(decryResult);

        } catch (Exception e) {
            System.out.println("失败");
            e.printStackTrace();
        }
    }
}