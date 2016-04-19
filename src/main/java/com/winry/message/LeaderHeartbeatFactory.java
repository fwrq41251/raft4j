package com.winry.message;

import com.winry.message.LeaderHeartbeatProto.LeaderHeartbeat;

public class LeaderHeartbeatFactory {

	public static LeaderHeartbeat build() {
		return LeaderHeartbeat.newBuilder().setVersion(1.0f).build();
	}
}
