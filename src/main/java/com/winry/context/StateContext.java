package com.winry.context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winry.message.MessageBuilder;
import com.winry.message.RaftMessage.AppendEntriesRequest;
import com.winry.message.RaftMessage.VoteRequest;
import com.winry.util.TimeUtil;

public class StateContext {

	private static volatile State state = State.follower;

	private static AtomicInteger termId = new AtomicInteger(0);

	private static AtomicInteger votes;

	private static Future<?> waitElectionTask = null;

	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private final static ExecutorService executer = Executors.newFixedThreadPool(1);

	static {
		startLeaderHeartbeatTask();
		startWaitElectionTask();
	}

	private static void startLeaderHeartbeatTask() {
		scheduler.scheduleAtFixedRate(() -> {
			if (state == State.leader) {
				AppendEntriesRequest heartbeat = MessageBuilder.buildLeaderHeartbeat();
				ClientsContext.sendToAll(heartbeat);
			}
		}, 0, 150, TimeUnit.MICROSECONDS);
	}

	private static void startWaitElectionTask() {
		waitElectionTask = executer.submit(new ElectionTask());
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

	public static boolean isWin() {
		return votes.get() > ClientsContext.size() / 2;
	}

	public static void increceVote() {
		votes.incrementAndGet();
	}

	private static class ElectionTask implements Runnable {

		private static final Logger LOGGER = LoggerFactory.getLogger(ElectionTask.class);

		@Override
		public void run() {
			while (true) {
				int timeout = TimeUtil.getElectionTimeout();
				try {
					Thread.sleep(timeout);
					if (StateContext.isFollower()) {
						StateContext.becomeCandidate();
						VoteRequest voteRequest = MessageBuilder.buildVoteRequest();
						ClientsContext.sendToAll(voteRequest);
					}
				} catch (InterruptedException ex) {
					LOGGER.debug("election task interrupted");
				}
			}
		}
	}

}
