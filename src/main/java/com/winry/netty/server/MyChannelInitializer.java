package com.winry.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

public class MyChannelInitializer extends ChannelInitializer<Channel> {
	@Override
	public void initChannel(Channel channel) {
		channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(0, 30, 0));
		channel.pipeline().addLast("heartBeatHandler", new HeartBeatHandler());
	}
}
