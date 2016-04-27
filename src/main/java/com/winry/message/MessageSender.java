package com.winry.message;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Queues;
import com.winry.util.OrderedMap;

import io.netty.channel.Channel;

public class MessageSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

	private final static BlockingQueue<Message> toSend = Queues.newLinkedBlockingQueue();

	private final static OrderedMap<Long, Message> sended = new OrderedMap<>();

	private final static ExecutorService executer = Executors.newFixedThreadPool(6);

	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	static {
		scheduler.scheduleAtFixedRate(() -> {
			List<Message> sendedMsgs = sended.getList();
			for (Message m : sendedMsgs) {
				try {
					toSend.put(m);
				} catch (InterruptedException e) {
					LOGGER.warn("failed to reput message", e);
				}
			}
		}, 0, 10, TimeUnit.SECONDS);
		executer.submit(() -> {
			while (true) {
				Message message = toSend.take();
				Channel channel = message.getClient().getChannel();
				executer.submit(() -> channel.writeAndFlush(message));
				sended.put(message.getIndex(), message);
			}
		});
	}

	public static void put(Message message) {
		toSend.add(message);
	}

	public static void remove(long index) {
		sended.remove(index);
	}
}
