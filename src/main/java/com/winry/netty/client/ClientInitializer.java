package com.winry.netty.client;

import com.winry.netty.ProtobugInitializer;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ClientInitializer extends ProtobugInitializer {

	private final Client client;

	public ClientInitializer(Client client) {
		super();
		this.client = client;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("heartBeatHandler", new ClientHandler(client));
		super.initChannel(ch);
	}

}
