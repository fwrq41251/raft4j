package com.winry.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.message.LeaderHeartbeatProto.LeaderHeartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LeaderHeartbeatHandler extends SimpleChannelInboundHandler<LeaderHeartbeat> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LeaderHeartbeatHandler.class);

	private final long heartBeatIntervalNanos;

	private long lastHearbeatTime;

	public LeaderHeartbeatHandler(int heartBeatInterval) {
		super();
		this.heartBeatIntervalNanos = heartBeatInterval * 1000;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LeaderHeartbeat msg) throws Exception {
		LOGGER.debug("recive leader hearbeat");
		lastHearbeatTime = System.currentTimeMillis();
	}

}
