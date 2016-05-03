package com.winry.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.winry.message.Message;
import com.winry.message.MessageSender;
import com.winry.netty.client.Client;
import com.winry.util.ConfigUtil;

public class ClientsContext {

	private static List<Client> clients = new ArrayList<Client>();

	private static Client leaderClient;

	public static void add(Client client) {
		synchronized (clients) {
			clients.add(client);
		}
	}

	public static void startClients() {
		final Map<String, String> nodeMap = readNodesConfig();
		nodeMap.forEach((key, value) -> {
			int port = Integer.valueOf(value);
			Client client = new Client(key, port);
			client.start();
		});
	}

	public static void sendToAll(Object message) {
		clients.forEach(client -> MessageSender.put(new Message(message, client.getChannel())));
	}

	public static int size() {
		return clients.size();
	}

	private static Map<String, String> readNodesConfig() {
		String nodes = ConfigUtil.get("nodes");
		final Map<String, String> nodeMap = Splitter.on(",").trimResults().withKeyValueSeparator(":").split(nodes);
		return nodeMap;
	}

}
