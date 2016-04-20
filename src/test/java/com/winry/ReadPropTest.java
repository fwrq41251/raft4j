package com.winry;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.winry.util.ConfigUtil;

public class ReadPropTest {

	@Test
	public void read() throws IOException {
		String value = ConfigUtil.get("nodes");
		Assert.assertEquals("127.0.0.1", value);
	}

}
