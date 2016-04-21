package com.winry.netty;

import com.winry.message.RaftMessage.VoteRequest;
import com.winry.message.RaftMessage.VoteResponse;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class ProtobugInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// Decoders
		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
		pipeline.addLast("protobufDecoder", new ProtobufDecoder(VoteRequest.getDefaultInstance()));
		pipeline.addLast("protobufDecoder", new ProtobufDecoder(VoteResponse.getDefaultInstance()));

		// Encoder
		pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeline.addLast("protobufEncoder", new ProtobufEncoder());
	}

}
