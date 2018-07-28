package utils.desutil;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密处理 (MD5,SHA-1,SHA-256)
 */
public class IncludeMd5CryptUtils {

	public final static String SHA_256 = "SHA-256";
	public final static String SHA_1 = "SHA-1";
	public final static String MD5 = "MD5";

	/**
	 * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
	 * 
	 * @param encName
	 *            加密类型
	 * @param strSrc
	 *            要加密的字符串
	 * @return
	 */
	public static String encrypt(String encName, Object... strSrc) {
		String strDes = null;
		if (strSrc != null) {
			try {
				if (encName == null || encName.length() == 0) {
					encName = SHA_256;
				}
				MessageDigest md = MessageDigest.getInstance(encName);
				for (Object o : strSrc) {
					if (o instanceof byte[]) {
						md.update((byte[]) o);
					} else if (o instanceof String) {
						md.update(((String) o).getBytes());
					} else if (o != null) {
						md.update(String.valueOf(o).getBytes());
					}
				}
				byte[] d = md.digest();
				strDes = bytes2Hex(d); // to HexString
			} catch (NoSuchAlgorithmException e) {
				// e.printStackTrace();
			}
		}
		return strDes;
	}

	/**
	 * MD5值计算
	 * <p>
	 * MD5的算法在RFC1321 中定义: 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
	 * <ol>
	 * <li>MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
	 * <li>MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
	 * <li>MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
	 * <li>MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
	 * <li>MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
	 * </ol>
	 * 
	 * @param strSrc
	 *            源字符串
	 * @return md5值
	 */
	public static String md5(String strSrc) {
		return encrypt(IncludeMd5CryptUtils.MD5, strSrc);
	}

	/* ------------------------------------------------------- */

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * rsa 签名验证
	 */
	public static boolean rsaCheck(String content, String PublicKey, String sign) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decodeBase64(PublicKey.getBytes());
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes("UTF-8"));
			boolean bverify = signature.verify(Base64.decodeBase64(sign.getBytes()));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/* ------------------------------------------------------- */

	protected static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
	protected static final char[] HEX_DIGITS_UPPER = "0123456789ABCDEF".toCharArray();

	private static String _bytes2Hex(byte[] bts, char[] hexDigits) {
		char[] o = new char[bts.length + bts.length];
		for (int i = 0, j = 0; i < bts.length; i++) {
			byte b = bts[i];
			o[j++] = hexDigits[(b & 0xf0) >> 4];
			o[j++] = hexDigits[b & 0x0f];
		}
		return new String(o);
	}

	public static String bytes2Hex(byte[] bts) {
		return _bytes2Hex(bts, HEX_DIGITS);
	}

	public static String bytes2HexUpper(byte[] bts) {
		return _bytes2Hex(bts, HEX_DIGITS);
	}
	
	/* ------------------------------------------------------- */

	public static void main(String[] args) {
		String test[] = { "", "a", "abc", "message digest", "abcdefghijklmnopqrstuvwxyz" };
		for (String s : test) {
			System.out.println("\"" + s + "\" = " + md5(s));
		}
	}
}
