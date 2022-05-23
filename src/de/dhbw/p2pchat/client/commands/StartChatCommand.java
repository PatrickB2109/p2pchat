package de.dhbw.p2pchat.client.commands;

import de.dhbw.p2pchat.client.ClientConnection;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

import java.util.Arrays;

public class StartChatCommand extends CommandParser {

    private static final int COMMAND_HELP_CUT = 2;
    private static final int ARG_RECIPIENT = 2;
    private static final int EXPECTED_LENGTH = 3;

    @Override
    public CommandResult execute(SplittedCommand command) {
        if (command.argsSize() == EXPECTED_LENGTH) {
            String recipientUuid = command.getStringAt(ARG_RECIPIENT);
            return ClientConnection.startChat(recipientUuid);
        }
        return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList("client uuid"));
    }
}
