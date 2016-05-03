package com.winry.context;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.winry.message.MessageBuilder;
import com.winry.message.RaftMessage.AppendEntriesRequest;
import com.winry.task.ElectionTask;

import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;

public class StateContext {

	private static volatile State state = State.follower;

	private static AtomicInteger termId = new AtomicInteger(0);

	private static AtomicInteger votes;

	private static Future<?> waitElectionTask = null;

	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private static EventLoopGroup workerGroup;

	static {
		// leader heartbeat
		scheduler.scheduleAtFixedRate(() -> {
			if (state == State.leader) {
				AppendEntriesRequest heartbeat = MessageBuilder.buildLeaderHeartbeat();
				ClientsContext.sendToAll(heartbeat);
			}
		}, 0, 150, TimeUnit.MICROSECONDS);
		startWaitElectionTask();
	}

	public enum State {
		follower, candidate, leader;
	}

	public static boolean isFollower() {
		return state == State.follower;
	}

	public synchronized static void becomeCandidate() {
		termId.incrementAndGet();
		state = State.candidate;
		votes = new AtomicInteger(1);
	}

	public synchronized static void becomeFollower(int newTermId) {
		termId.set(newTermId);
		state = State.follower;
	}

	public synchronized static void becomeLeader() {
		state = State.leader;
	}

	private static void startWaitElectionTask() {
		waitElectionTask = workerGroup.submit(new ElectionTask());
	}

	public static void restartWaitElectionTask() {
		synchronized (waitElectionTask) {
			waitElectionTask.cancel(true);
			startWaitElectionTask();
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

	public static void setWorkerGroup(EventLoopGroup workerGroup) {
		StateContext.workerGroup = workerGroup;
	}

	public static boolean isWin() {
		return votes.get() > ClientsContext.size() / 2;
	}

	public static void increceVote() {
		votes.incrementAndGet();
	}

}
