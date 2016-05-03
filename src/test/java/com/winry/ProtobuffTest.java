package com.winry;

import org.junit.Test;

import com.winry.message.MessageBuilder;
import com.winry.message.RaftMessage.AppendEntriesRequest;

public class ProtobuffTest {

	@Test
	public void AppendEntriesRequest() {
		AppendEntriesRequest request = MessageBuilder.buildLeaderHeartbeat();
		String log = request.getLog();
		System.out.println(log);
	}

}
