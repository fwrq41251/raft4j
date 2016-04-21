package com.winry.netty.server;

import com.winry.netty.ProtobugInitializer;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerInitializer extends ProtobugInitializer {
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("voteRequestHandler", new VoteRequestHandler());
		pipeline.addLast("appendEntriesRequestHandler", new AppendEntriesRequestHandler());
		pipeline.addLast("appendEntriesResponseHandler", new AppendEntriesResponseHandler());
		super.initChannel(ch);
	}
}
