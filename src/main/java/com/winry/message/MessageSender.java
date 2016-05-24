package com.winry.message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Queues;

import io.netty.channel.Channel;

public class MessageSender {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

	private final static BlockingQueue<Message> toSend = Queues.newLinkedBlockingQueue();

	private final static Map<Long, Future<?>> sended = new HashMap<>();

	private final static ExecutorService executer = Executors.newFixedThreadPool(10);

	static {
		startSendTask();
	}

	private static void startSendTask() {
		executer.submit(() -> {
			while (true) {
				Message message = toSend.take();
				Channel channel = message.getChannel();
				executer.submit(() -> channel.writeAndFlush(message));
				Future<?> future = executer.submit(new ResendMessageTask(message));
				sended.put(message.getIndex(), future);
			}
		});
	}

	public static void put(Message message) {
		toSend.add(message);
	}

	public static void remove(long index) {
		sended.get(index).cancel(true);
		sended.remove(index);
	}

	private static class ResendMessageTask implements Runnable {

		private final Message message;

		private static final Logger LOGGER = LoggerFactory.getLogger(ResendMessageTask.class);

		public ResendMessageTask(Message message) {
			super();
			this.message = message;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				toSend.put(message);
			} catch (InterruptedException ex) {
				LOGGER.debug("message resend interrupted: {}", message.getMessage().toString());
			}
		}

	}
}
