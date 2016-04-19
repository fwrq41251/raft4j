package com.winry.netty.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

public class ReconnectionListener implements ChannelFutureListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

	private Client client;

	public ReconnectionListener(Client client) {
	     this.client = client;  
	   }

	@Override
	public void operationComplete(ChannelFuture channelFuture) throws Exception {
		if (!channelFuture.isSuccess()) {
			LOGGER.debug("try to reconnect");
			final EventLoop loop = channelFuture.channel().eventLoop();
			loop.schedule(new Runnable() {
				@Override
				public void run() {
					client.start(loop);
				}
			}, 1L, TimeUnit.SECONDS);
		}
	}
}
