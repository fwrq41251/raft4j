package com.winry.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public class LogUtil {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

	private static final File file = new File("log");

	public static void append(String logEnrty) throws IOException {
		Files.append(logEnrty + "\n", file, Charset.defaultCharset());
	}
}
