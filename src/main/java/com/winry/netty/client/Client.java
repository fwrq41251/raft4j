package com.winry.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.message.LeaderHeartbeatProto;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class Client {

	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

	private final String host;

	private final int port;

	public Client(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	/**
	 * 
	 * @param eventLoopGroup
	 *            set null if start from no loop
	 */
	public ChannelFuture start(EventLoopGroup eventLoopGroup) {
		if (null == eventLoopGroup) {
			eventLoopGroup = new NioEventLoopGroup();
		}
		try {
			ClientHandler handler = new ClientHandler(this);
			Bootstrap b = new Bootstrap();
			b.group(eventLoopGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					// Decoders
					pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
					pipeline.addLast("protobufDecoder",
							new ProtobufDecoder(LeaderHeartbeatProto.LeaderHeartbeat.getDefaultInstance()));

					// Encoder
					pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
					pipeline.addLast("protobufEncoder", new ProtobufEncoder());

					// handler
					pipeline.addLast("heartBeatHandler", handler);
				}
			});

			// Start the client.
			ChannelFuture f = b.connect(host, port).addListener(new ReconnectionListener(this)).sync();
			return f;
		} catch (InterruptedException e) {
			LOGGER.debug("client start interupted:{}:{}", host, port, e);
			throw new RuntimeException(e);
		}
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

}
