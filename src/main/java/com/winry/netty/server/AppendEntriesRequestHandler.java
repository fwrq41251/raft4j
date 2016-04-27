package com.winry.netty.server;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.context.StateContext;
import com.winry.message.MessageBuilder;
import com.winry.message.RaftMessage.AppendEntriesRequest;
import com.winry.message.RaftMessage.AppendEntriesResponse;
import com.winry.util.LogUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class AppendEntriesRequestHandler extends SimpleChannelInboundHandler<AppendEntriesRequest> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppendEntriesRequestHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AppendEntriesRequest msg) throws Exception {
		checkLeader(msg);
		appendLog(ctx, msg);
		StateContext.restartWaitElectionTask();
	}

	private void appendLog(ChannelHandlerContext ctx, AppendEntriesRequest msg) {
		String log = msg.getLog();
		if (StringUtils.isNotBlank(log)) {
			try {
				LogUtil.append(log);
				AppendEntriesResponse appendEntriesResponse = MessageBuilder.buildAppendEntriesResponse(log);
				ctx.writeAndFlush(appendEntriesResponse);
			} catch (IOException e) {
				LOGGER.error("failed to append log", e);
			}
		}
	}

	private void checkLeader(AppendEntriesRequest msg) {
		int termId = msg.getTermId();
		if (termId >= StateContext.getTermId()) {
			StateContext.becomeFollower(termId);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

}
