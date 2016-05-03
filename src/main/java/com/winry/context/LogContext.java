package com.winry.context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LogContext {

	private Map<String, String> uncommitedMessage = new HashMap<>();

	private static AtomicInteger index = new AtomicInteger(0);

	public static AtomicInteger getIndex() {
		return index;
	}

}
