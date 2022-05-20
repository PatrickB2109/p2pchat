package de.dhbw.p2pchat.client.commands;

import java.util.Arrays;

import de.dhbw.p2pchat.client.ClientConnection;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

public class ListUsersCommand extends CommandParser {

	private static final int COMMAND_HELP_CUT = 2;
	private static final int EXPECTED_LENGTH = 2;
	
	@Override
	public CommandResult execute(SplittedCommand command) {
		if (command.argsSize() == EXPECTED_LENGTH) {
			return ClientConnection.showUsers();
		}
		return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList());
	}
}