package de.dhbw.p2pchat.client.commandparser;

import java.util.HashMap;
import java.util.Map;

import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

public abstract class UppercaseCommandParser extends CommandParser {

	private int position;
	protected Map<String, CommandParser> commands = new HashMap<>();

	public UppercaseCommandParser(int switchPos) {
		super();
		this.position = switchPos;
	}

	public CommandResult execute(SplittedCommand command) {
		CommandParser commandParser = commands.get(command.getStringUppercaseAt(position));
		if (commandParser != null) {
			return commandParser.execute(command);
		}
		return CommandResult.usage(command.getCommandUpToPos(position), commands.keySet());
	}

}