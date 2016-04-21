package com.winry.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.context.StateContext;
import com.winry.message.RaftMessage.AppendEntriesRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class AppendEntriesRequestHandler extends SimpleChannelInboundHandler<AppendEntriesRequest> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppendEntriesRequestHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AppendEntriesRequest msg) throws Exception {
		int termId = msg.getTermId();
		if (termId >= StateContext.getTermId()) {
			StateContext.becomeFollower(termId);
		}
		StateContext.restartWaitElectionTask(ctx.channel().eventLoop());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

}
