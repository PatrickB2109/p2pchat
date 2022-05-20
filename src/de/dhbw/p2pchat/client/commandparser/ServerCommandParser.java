package de.dhbw.p2pchat.client.commandparser;

import de.dhbw.p2pchat.client.commands.AutoConnectServerCommand;
import de.dhbw.p2pchat.client.commands.ConnectServerCommand;
import de.dhbw.p2pchat.client.commands.ListUsersCommand;

public class ServerCommandParser extends UppercaseCommandParser {

	private static final int HIERARCHY_LEVEL = 1;

	public ServerCommandParser() {
		super(HIERARCHY_LEVEL);
		commands.put("CONNECT", new ConnectServerCommand());
		commands.put("AUTOCONNECT", new AutoConnectServerCommand());
		commands.put("LISTUSERS", new ListUsersCommand());
	}

}