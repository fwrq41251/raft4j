package com.winry.context;

import java.util.concurrent.atomic.AtomicInteger;

public class StateContext {

	private static StateContext context = new StateContext(State.follower);

	private State state;

	private AtomicInteger termId = new AtomicInteger(0);

	enum State {
		follower, candidate, leader;
	}

	public StateContext(State state) {
		super();
		this.state = state;
	}

	public static StateContext getStateContext() {
		return context;
	}

}
