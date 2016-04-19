package com.winry.context;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;

public class ChannelContext {

	private static Map<String, Channel> map = new HashMap<>();

	public static void put(String domain, Channel channel) {
		synchronized (map) {
			map.put(domain, channel);
		}
	}

}
