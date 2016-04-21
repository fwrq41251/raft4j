package com.winry;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

import com.winry.util.LogUtil;
import com.winry.util.TimeUtil;

public class CommonTest {

	@Test
	public void format() {
		final String domain = String.format("%s:%d", "127.0.0.1", 5986);
		System.out.println(domain);
	}

	@Test
	public void randomTimeout() {
		final int timeout = TimeUtil.getElectionTimeout();
		System.out.println(timeout);
	}

	@Test
	public void appendLog() throws IOException {
		LogUtil.append(UUID.randomUUID().toString());
	}
}
