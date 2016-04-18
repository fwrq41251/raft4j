package com.winry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.netty.Server;

public class App {


	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		Server server = new Server(8082);
		try {
			server.start();
		} catch (InterruptedException e) {
			LOGGER.error("failed to start server", e);
		}
	}
}
