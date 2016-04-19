package com.winry.context;

import java.util.concurrent.atomic.AtomicInteger;

public class StateContext {

	private static State state = State.follower;

	private static AtomicInteger termId = new AtomicInteger(0);

	enum State {
		follower, candidate, leader;
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
