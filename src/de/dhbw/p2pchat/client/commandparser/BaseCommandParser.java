package de.dhbw.p2pchat.client.commandparser;

import de.dhbw.p2pchat.client.commands.ConnectClientCommand;

public class BaseCommandParser extends UppercaseCommandParser {

	public BaseCommandParser() {
		super(0);
		commands.put("SERVER", new ServerCommandParser());
		commands.put("CLIENT", new ClientCommandParser() );
	}

}
