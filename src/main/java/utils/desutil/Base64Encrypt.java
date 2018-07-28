package utils.desutil;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Base64 自定义处理
 */
public class Base64Encrypt {

	static final boolean DEBUG = true; // ZZSDKConfig.DEBUG;

	/** 码表 */
	private static final String ENCODE_CHAR_BASE = "" +
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			"abcdefghijklmnopqrstuvwxyz" +
			"0123456789+/" +
			"=\n\r\t ";

	static final HashMap<String, Base64Encrypt> s_instance = new HashMap<String, Base64Encrypt>();

	public static final Base64Encrypt sBASE = instance(ENCODE_CHAR_BASE);
	public static final Base64Encrypt sENC = instance("GxU3wLPAzOpEbDH5mKRaVZfSNgs07.q8Y4I2CMnoW-6dtke9XvijFyl1BcQrhTJu");
	public static final Base64Encrypt sROLE = instance("JRps7QAydqSkYFN-T4wnBDhKLr8H3.u69mG2WjPgOxco0avZE5IUflMVbtzeX1iC");


	public static synchronized Base64Encrypt instance(String tableKey) {
		Base64Encrypt encrypt = s_instance.get(tableKey);
		if (encrypt == null) {
			encrypt = new Base64Encrypt(tableKey);
			s_instance.put(tableKey, encrypt);
		}
		return encrypt;
	}

	private Base64Encrypt(String tableKey) {
		if (tableKey == null || tableKey.length() < 64) {
			throw new IllegalArgumentException("tableKey.length!=64");
		}

		encodingTable = tableKey.getBytes();

		decodingTable = new byte[128];
		for (int i = 0; i < 128; i++) {
			decodingTable[i] = (byte) -1;
		}
		for (int i = 0; i < encodingTable.length; i++) {
			decodingTable[encodingTable[i]] = i >= 64 ? -2 : (byte) i;
		}

	}


	private final byte[] encodingTable;
	private final byte[] decodingTable;

	// 打乱
	public static final String randKey(String tableKey, long seed) {
		final byte tt[] = tableKey.getBytes();
		final int c = tt.length;
		tt[c - 2] = '-';
		tt[c - 1] = '.';
		final java.util.Random r = new java.util.Random(seed == 0 ? 0X12F5D08 : seed);
		for (int i = 1; i < c; i++) {
			int o = c - i, p = r.nextInt(o);
			byte t = tt[p];
			tt[p] = tt[o];
			tt[o] = t;
		}
		String key = new String(tt);
		if (DEBUG)
			System.out.println(key);
		return key;
	}

	public byte[] encode(byte[] data) {
		byte[] bytes = new byte[(4 * data.length + 2) / 3];
		int bits = 0, pos = 0, window = 0;
		for (int i = 0; i < data.length; i++, bits <<= 8) {
			bits |= (data[i] & 0xff);
			window += 2;
			bytes[pos++] = encodingTable[(bits >> window) & 0x3f];
			if (window == 6) {
				window = 0;
				bytes[pos++] = encodingTable[bits & 0x3f];
			}
		}
		if (window > 0)
			bytes[pos++] = encodingTable[(bits >> (window + 2)) & 0x3f];
		return bytes;
	}

	public byte[] decode(byte[] data) {
		// check...
		for (byte b : data) {
			if (b >= 0 && b < 128 && decodingTable[b] != -1) {
			} else {
				return null;
			}
		}

		byte[] bytes;
		bytes = new byte[data.length * 3 / 4];
		int bits = 0, pos = 0, window = 0;
		for (int i = 0; i < data.length; i++) {
			byte b = decodingTable[data[i]];
			if (b == -2) continue;
			bits |= b;
			if (window >= 2) {
				bytes[pos++] = (byte) ((bits >> (window -= 2)) & 0xff);
			} else {
				window += 6;
			}
			bits <<= 6;
		}
		if (pos < bytes.length) {
			byte[] tmp = new byte[pos];
			System.arraycopy(bytes, 0, tmp, 0, pos);
			bytes = tmp;
		}
		return bytes;
	}

	public byte[] decode(String data) {
		return decode(data.getBytes());
	}

	public static byte[] encodeBase64(byte[] bytes) {
		return sBASE.encode(bytes);
	}

	public static byte[] decodeBase64(String s) {
		return sBASE.decode(s);
	}


	public static void main(String[] args) {
		if (DEBUG) {
			String needDecData = "MDEyQWJDRCwtQCMrPeS4reWbvSDllYrjgILmsYkuLuWtly0t5ZGi77yfLi5BYg";
			try {
				String getData = new String(Base64Encrypt.decodeBase64(needDecData), "UTF-8");
				System.out.println(getData);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

}
