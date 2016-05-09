package com.winry.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	private final int port;

	public Server(int port) {
		super();
		this.port = port;
	}

	public ChannelFuture start() {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ServerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Start the server.
			ChannelFuture f = b.bind(port).sync();
			return f;
		} catch (InterruptedException e) {
			LOGGER.debug("client start interupted", e);
			throw new RuntimeException(e);
		}
	}

}
