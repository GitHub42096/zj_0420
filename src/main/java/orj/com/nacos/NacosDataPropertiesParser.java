package orj.com.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zkz
 */
public class NacosDataPropertiesParser {

	private static final Logger log = LoggerFactory
			.getLogger(NacosDataPropertiesParser.class);


	protected Map<String, Object> doParse(String data) throws IOException {
		Map<String, Object> result = new LinkedHashMap<>();

		try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				String dataLine = line.trim();
				if (StringUtils.isEmpty(dataLine) || dataLine.startsWith("#")) {
					continue;
				}
				int index = dataLine.indexOf("=");
				if (index == -1) {
					log.warn("the config data is invalid {}", dataLine);
					continue;
				}
				result.put(dataLine.substring(0, index), dataLine.substring(index + 1));
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		NacosDataPropertiesParser nacosDataPropertiesParser = new NacosDataPropertiesParser();
		String data = "server.address=dd\n" + "server.compression.enabled=false\n" + "#ddd\n" + "server.error.include-stacktrace=never";
		Map<String, Object> map = nacosDataPropertiesParser.doParse(data);
		System.out.println(map);
	}
}
