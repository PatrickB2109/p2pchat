package de.dhbw.p2pchat.client.commandparser;

public class BaseCommandParser extends UppercaseCommandParser {

	public BaseCommandParser() {
		super(0);
		commands.put("SERVER", new ServerCommandParser());
		commands.put("CLIENT", new ClientCommandParser());
	}

}
