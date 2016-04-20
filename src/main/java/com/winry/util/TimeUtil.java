package com.winry.util;

import java.util.concurrent.ThreadLocalRandom;

public class TimeUtil {

	/**
	 * get election timeout between 150ms ~ 300ms
	 * 
	 * @return
	 */
	public static int getElectionTimeout() {
		return ThreadLocalRandom.current().nextInt(150, 301);
	}
}
