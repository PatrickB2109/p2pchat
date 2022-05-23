package de.dhbw.p2pchat.client.commandparser;

import de.dhbw.p2pchat.client.commands.ConnectClientCommand;
import de.dhbw.p2pchat.client.commands.StartChatCommand;

public class ClientCommandParser extends UppercaseCommandParser {
	private static final int HIERARCHY_LEVEL = 1;

	public ClientCommandParser() {
		super(HIERARCHY_LEVEL);
		commands.put("CONNECT", new ConnectClientCommand());
		commands.put("CHAT", new StartChatCommand());
	}
}
