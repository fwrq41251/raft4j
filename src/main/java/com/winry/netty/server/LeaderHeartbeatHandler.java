package com.winry.netty.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.message.LeaderHeartbeatProto.LeaderHeartbeat;
import com.winry.netty.event.LeaderHeartbeatTimeoutEvent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutor;

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

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		initialize(ctx);
		super.channelActive(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof LeaderHeartbeatTimeoutEvent) {
			LeaderHeartbeatTimeoutEvent e = (LeaderHeartbeatTimeoutEvent) evt;
			// TODO start a leader election
		}
	}

	private void initialize(ChannelHandlerContext ctx) {
		EventExecutor loop = ctx.executor();
		loop.schedule(new LeaderHearBeatTimeoutTask(ctx), heartBeatIntervalNanos, TimeUnit.SECONDS);
	}

	private final class LeaderHearBeatTimeoutTask implements Runnable {

		private final ChannelHandlerContext ctx;

		public LeaderHearBeatTimeoutTask(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public void run() {
			if (timeout())
				ctx.fireUserEventTriggered(new LeaderHeartbeatTimeoutEvent());
		}

		private boolean timeout() {
			long now = System.currentTimeMillis();
			return (now - lastHearbeatTime) > heartBeatIntervalNanos;
		}

	}

}
