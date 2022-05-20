package de.dhbw.p2pchat.client.commandparser;

import de.dhbw.p2pchat.client.commands.ConnectServerCommand;

public class ServerCommandParser extends UppercaseCommandParser {

	private static final int HIERARCHY_LEVEL = 1;

	public ServerCommandParser() {
		super(HIERARCHY_LEVEL);
		commands.put("CONNECT", new ConnectServerCommand());
	}

}