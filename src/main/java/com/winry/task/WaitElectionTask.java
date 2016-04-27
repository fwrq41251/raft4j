package com.winry.task;

import com.winry.context.ClientsContext;
import com.winry.context.StateContext;
import com.winry.message.MessageBuilder;
import com.winry.message.RaftMessage.VoteRequest;
import com.winry.util.TimeUtil;

public class WaitElectionTask implements Runnable {

	public boolean result = false;

	@Override
	public void run() {
		while (true) {
			int timeout = TimeUtil.getElectionTimeout();
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			StateContext.becomeCandidate();
			VoteRequest voteRequest = MessageBuilder.buildVoteRequest();
			ClientsContext.sendToAll(voteRequest);
		}
	}

}
