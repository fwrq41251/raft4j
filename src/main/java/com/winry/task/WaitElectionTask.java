package com.winry.task;

import com.winry.context.StateContext;
import com.winry.util.TimeUtil;

public class WaitElectionTask implements Runnable {

	public boolean result = false;

	@Override
	public void run() {
		int timeout = TimeUtil.getElectionTimeout();
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		StateContext.becomeCandidate();
	}

}
