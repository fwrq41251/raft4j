package com.winry.message;

import com.winry.consts.Constants;
import com.winry.context.StateContext;
import com.winry.message.RaftMessage.AppendEntriesRequest;
import com.winry.message.RaftMessage.AppendEntriesResponse;
import com.winry.message.RaftMessage.VoteRequest;
import com.winry.message.RaftMessage.VoteResponse;

public class MessageBuilder {

	public static VoteRequest buildVoteRequest() {
		return VoteRequest.newBuilder().setVersion(Constants.VERSION).setTermId(StateContext.getTermId()).build();
	}

	public static VoteResponse buildVoteResponse(boolean agree) {
		return VoteResponse.newBuilder().setVersion(Constants.VERSION).setTermId(StateContext.getTermId())
				.setAgree(agree).build();
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
