package com.winry.context;

import java.util.concurrent.atomic.AtomicInteger;

import com.winry.task.WaitElectionTask;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;

public class StateContext {

	private static volatile State state = State.follower;

	private static AtomicInteger termId = new AtomicInteger(0);

	private static int votes;

	private static Future<?> waitElectionTask = null;

	enum State {
		follower, candidate, leader;
	}

	public static boolean isFollower() {
		return state == State.follower;
	}

	public synchronized static void becomeCandidate() {
		termId.incrementAndGet();
		state = State.candidate;
		votes = votes + 1;
	}

	public static void startWaitElectionTask(ChannelHandlerContext ctx) {
		synchronized (waitElectionTask) {
			waitElectionTask = ctx.channel().eventLoop().submit(new WaitElectionTask());
			waitElectionTask.addListener(futrue -> {
				// TODO send votes request to nodes.
			});
		}
	}

	public static void restartWaitElectionTask(ChannelHandlerContext ctx) {
		synchronized (waitElectionTask) {
			waitElectionTask.cancel(true);
			startWaitElectionTask(ctx);
		}
	}

	public static State getState() {
		return state;
	}

	public static void setState(State state) {
		StateContext.state = state;
	}

	public static AtomicInteger getTermId() {
		return termId;
	}

	public static void increceTermId() {
		termId.incrementAndGet();
	}

}
