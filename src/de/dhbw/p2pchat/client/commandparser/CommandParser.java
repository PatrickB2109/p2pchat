package de.dhbw.p2pchat.client.commandparser;

import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

import java.io.IOException;

public abstract class CommandParser {

	public abstract CommandResult execute(SplittedCommand command);

}