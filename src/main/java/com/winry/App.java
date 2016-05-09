package com.winry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.context.ClientsContext;
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
		f.addListener(futrue -> ClientsContext.startClients());
	}


}
