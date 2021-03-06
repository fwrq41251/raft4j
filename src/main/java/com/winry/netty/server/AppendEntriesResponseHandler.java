package com.winry.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.message.MessageSender;
import com.winry.message.RaftMessage.AppendEntriesResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class AppendEntriesResponseHandler extends SimpleChannelInboundHandler<AppendEntriesResponse> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AppendEntriesResponseHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AppendEntriesResponse msg) throws Exception {
		MessageSender.remove(msg.getIndex());
		String log = msg.getLog();
		// TODO append log
	}

}
