package com.winry.message;

import com.winry.consts.Constants;
import com.winry.context.StateContext;
import com.winry.message.RaftMessage.VoteRequest;

public class MessageBuilder {

	public static VoteRequest buildVoteRequest() {
		return VoteRequest.newBuilder().setVersion(Constants.VERSION).setTermId(StateContext.getTermId()).build();
	}
}
