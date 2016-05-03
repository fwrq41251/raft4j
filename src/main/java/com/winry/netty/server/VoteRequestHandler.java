package com.winry.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.context.StateContext;
import com.winry.message.Message;
import com.winry.message.MessageBuilder;
import com.winry.message.MessageSender;
import com.winry.message.RaftMessage.VoteRequest;
import com.winry.message.RaftMessage.VoteResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class VoteRequestHandler extends SimpleChannelInboundHandler<VoteRequest> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoteRequestHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, VoteRequest msg) throws Exception {
		LOGGER.debug("recive vote request");
		boolean agree = msg.getTermId() >= StateContext.getTermId();
		VoteResponse voteResponse = MessageBuilder.buildVoteResponse(agree);
		MessageSender.put(new Message(voteResponse, ctx.channel()));
	}

}
