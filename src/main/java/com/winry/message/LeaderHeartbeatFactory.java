package com.winry.message;

import com.winry.message.LeaderHeartbeatProto.LeaderHeartbeat;

public class LeaderHeartbeatFactory {

	public static LeaderHeartbeat build() {
		LeaderHeartbeat.Builder builder = LeaderHeartbeat.newBuilder();
		builder.setVersion(1.0f);
		return builder.build();
	}
}
