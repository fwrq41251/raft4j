package com.winry;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.winry.netty.client.Client;
import com.winry.netty.server.Server;
import com.winry.util.ConfigUtil;

import io.netty.channel.ChannelFuture;

public class App {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		int port = ConfigUtil.getInt("port");
		Server server = new Server(port);
		ChannelFuture f = server.start();
		f.addListener(futrue -> startClient());
	}

	private static void startClient() {
		String nodes = ConfigUtil.get("nodes");
		final Map<String, String> nodeMap = Splitter.on(",").trimResults().withKeyValueSeparator(":").split(nodes);
		nodeMap.forEach((key, value) -> {
			int port = Integer.valueOf(value);
			Client client = new Client(key, port);
			client.start(null);
		});
	}

}
