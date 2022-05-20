package de.dhbw.p2pchat.client.commands;

import java.util.Arrays;

import de.dhbw.p2pchat.client.ClientConnection;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

public class ConnectServerCommand extends CommandParser {

	private static final int COMMAND_HELP_CUT = 2;
	private static final int ARG_IP = 2;
	private static final int ARG_PORT = 3;
	private static final int ARG_USERNAME = 4;
	private static final int EXPECTED_LENGTH = 5;
	
	public ConnectServerCommand() {
		super();
	}

	@Override
	public CommandResult execute(SplittedCommand command) {
		if (command.argsSize() == EXPECTED_LENGTH) {
			String ip = command.getStringAt(ARG_IP);
			String port = command.getStringAt(ARG_PORT);
			String username = command.getStringAt(ARG_USERNAME);
			return ClientConnection.connect(ip, Integer.parseInt(port), username);
		}
		return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList("ip", "port", "username"));
	}
}