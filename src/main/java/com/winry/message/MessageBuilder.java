package com.winry.message;

import com.winry.consts.Constants;
import com.winry.context.StateContext;
import com.winry.message.RaftMessage.AppendEntriesRequest;
import com.winry.message.RaftMessage.AppendEntriesResponse;
import com.winry.message.RaftMessage.VoteRequest;

public class MessageBuilder {

	public static VoteRequest buildVoteRequest() {
		return VoteRequest.newBuilder().setVersion(Constants.VERSION).setTermId(StateContext.getTermId()).build();
	}

	public static AppendEntriesRequest buildLeaderHeartbeat() {
		return AppendEntriesRequest.newBuilder().setVersion(Constants.VERSION).setTermId(StateContext.getTermId())
				.build();
	}

	public static AppendEntriesResponse buildAppendEntriesResponse(String log) {
		return AppendEntriesResponse.newBuilder().setVersion(Constants.VERSION).setTermId(StateContext.getTermId())
				.setLog(log).build();
	}

}
