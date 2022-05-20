package de.dhbw.p2pchat.client.commandparser;

import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

public abstract class CommandParser {

	public CommandParser() {
		super();
	}

	public abstract CommandResult execute(SplittedCommand command);

}