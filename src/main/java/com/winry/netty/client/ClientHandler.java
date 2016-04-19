package com.winry.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.message.LeaderHeartbeatFactory;
import com.winry.message.LeaderHeartbeatProto.LeaderHeartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<LeaderHeartbeat> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		final Channel channel = ctx.channel();
		LeaderHeartbeat leaderHeartbeat = LeaderHeartbeatFactory.build();
		channel.writeAndFlush(leaderHeartbeat);
		super.channelActive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LeaderHeartbeat msg) throws Exception {
		LOGGER.debug(msg.toString());
	}

}
