package com.winry;

import java.io.IOException;
import java.text.MessageFormat;

import org.junit.Assert;
import org.junit.Test;

import com.winry.util.ConfigUtil;

public class ReadPropTest {

	@Test
	public void read() throws IOException {
		String value = ConfigUtil.get("nodes");
		Assert.assertEquals("127.0.0.1", value);
	}

	@Test
	public void format() {
		final String domain = MessageFormat.format("{0}:{1}", "127.0.0.1", "8964");
		System.out.println(domain);
	}

}
