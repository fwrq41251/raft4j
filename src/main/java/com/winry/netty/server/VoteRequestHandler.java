package com.winry.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.context.StateContext;
import com.winry.message.RaftMessage.VoteRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class VoteRequestHandler extends SimpleChannelInboundHandler<VoteRequest> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoteRequestHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, VoteRequest msg) throws Exception {
		LOGGER.debug("recive leader hearbeat");
		StateContext.restartWaitElectionTask(ctx.channel().eventLoop());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

}
