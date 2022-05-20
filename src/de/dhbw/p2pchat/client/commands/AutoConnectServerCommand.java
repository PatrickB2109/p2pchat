package de.dhbw.p2pchat.client.commands;

import java.util.Arrays;

import de.dhbw.p2pchat.client.ClientConnection;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

public class AutoConnectServerCommand extends CommandParser {

	private static final int COMMAND_HELP_CUT = 2;
	private static final int ARG_USERNAME = 2;
	private static final int EXPECTED_LENGTH = 3;
	
	@Override
	public CommandResult execute(SplittedCommand command) {
		if (command.argsSize() == EXPECTED_LENGTH) {
			String username = command.getStringAt(ARG_USERNAME);
			return ClientConnection.autoConnect(username);
		}
		return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList("username"));
	}
}