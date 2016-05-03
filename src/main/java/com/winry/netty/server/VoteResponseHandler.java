package com.winry.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.context.StateContext;
import com.winry.message.MessageSender;
import com.winry.message.RaftMessage.VoteResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class VoteResponseHandler extends SimpleChannelInboundHandler<VoteResponse> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoteResponseHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, VoteResponse msg) throws Exception {
		LOGGER.debug("recive leader hearbeat");
		MessageSender.remove(msg.getIndex());
		StateContext.restartWaitElectionTask();
		if (msg.getAgree()) {
			StateContext.increceVote();
			if (StateContext.isWin()) {
				StateContext.becomeLeader();
			}
		}
	}

}
