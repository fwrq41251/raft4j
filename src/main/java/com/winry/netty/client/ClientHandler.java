package com.winry.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.context.ClientsContext;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

	private final Client client;

	public ClientHandler(Client client) {
		super();
		this.client = client;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		client.setChannel(ctx.channel());
		ClientsContext.add(client);
		super.channelActive(ctx);
	}

}
