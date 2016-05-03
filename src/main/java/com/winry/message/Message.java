package com.winry.message;

import com.winry.context.LogContext;

import io.netty.channel.Channel;

public class Message {

	private long index;

	private Object message;

	private Channel channel;

	public Message(Object message, Channel channel) {
		this(LogContext.getIndex().get(), message, channel);
	}

	public Message(long index, Object message, Channel channel) {
		super();
		this.index = index;
		this.message = message;
		this.channel = channel;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
