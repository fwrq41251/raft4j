package com.winry.netty.server;

import com.winry.message.LeaderHeartbeatProto;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class ServerInitializer extends ChannelInitializer<Channel> {
	@Override
	public void initChannel(Channel channel) {
		ChannelPipeline pipeline = channel.pipeline();
		// Decoders
		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
		pipeline.addLast("protobufDecoder",
				new ProtobufDecoder(LeaderHeartbeatProto.LeaderHeartbeat.getDefaultInstance()));

		// Encoder
		pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeline.addLast("protobufEncoder", new ProtobufEncoder());

		// handler
		pipeline.addLast("heartBeatHandler", new LeaderHeartbeatHandler(3));
	}
}