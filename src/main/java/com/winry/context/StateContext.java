package com.winry.context;

public class StateContext {

	private static StateContext context = new StateContext(State.follower);

	private State state;

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
