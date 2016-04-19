package com.winry.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

	private static Properties prop = new Properties();

	static {
		InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			LOGGER.error("error to read prop.", e);
		}
	}

	public static String get(String key) {
		return prop.getProperty(key);
	}

	public static int getInt(String key) {
		return Integer.valueOf(get(key));
	}

}
