package com.winry.context;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.winry.message.MessageBuilder;
import com.winry.message.RaftMessage.AppendEntriesRequest;
import com.winry.message.RaftMessage.VoteRequest;
import com.winry.task.WaitElectionTask;

import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;

public class StateContext {

	private static volatile State state = State.follower;

	private static AtomicInteger termId = new AtomicInteger(0);

	private static int votes;

	private static Future<?> waitElectionTask = null;

	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public enum State {
		follower, candidate, leader;
	}

	public static void startLeaderHeartbeatTask() {
		scheduler.scheduleAtFixedRate(() -> {
			if (state == State.leader) {
				AppendEntriesRequest heartbeat = MessageBuilder.buildLeaderHeartbeat();
				ClientsContext.sendToAll(heartbeat);
			}
		}, 0, 150, TimeUnit.MICROSECONDS);
	}

	public static boolean isFollower() {
		return state == State.follower;
	}

	public synchronized static void becomeCandidate() {
		termId.incrementAndGet();
		state = State.candidate;
		votes = votes + 1;
	}

	public synchronized static void becomeFollower(int newTermId) {
		termId.set(newTermId);
		state = State.follower;
	}

	public static void startWaitElectionTask(EventLoopGroup eventLoopGroup) {
		synchronized (waitElectionTask) {
			waitElectionTask = eventLoopGroup.submit(new WaitElectionTask());
			waitElectionTask.addListener(futrue -> {
				VoteRequest voteRequest = MessageBuilder.buildVoteRequest();
				ClientsContext.sendToAll(voteRequest);
			});
		}
	}

	public static void restartWaitElectionTask(EventLoopGroup eventLoopGroup) {
		synchronized (waitElectionTask) {
			waitElectionTask.cancel(true);
			startWaitElectionTask(eventLoopGroup);
		}
	}

	public static State getState() {
		return state;
	}

	public static void setState(State state) {
		StateContext.state = state;
	}

	public static int getTermId() {
		return termId.get();
	}

	public static void increceTermId() {
		termId.incrementAndGet();
	}

}
