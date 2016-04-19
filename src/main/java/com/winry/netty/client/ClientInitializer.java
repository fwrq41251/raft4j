package com.winry.netty.client;

import com.winry.message.LeaderHeartbeatProto;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

	private final Client client;

	public ClientInitializer(Client client) {
		super();
		this.client = client;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// Decoders
		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
		pipeline.addLast("protobufDecoder",
				new ProtobufDecoder(LeaderHeartbeatProto.LeaderHeartbeat.getDefaultInstance()));

		// Encoder
		pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeline.addLast("protobufEncoder", new ProtobufEncoder());

		// handler
		pipeline.addLast("heartBeatHandler", new ClientHandler(client));

	}

}
