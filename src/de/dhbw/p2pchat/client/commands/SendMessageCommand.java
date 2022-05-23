package de.dhbw.p2pchat.client.commands;

import de.dhbw.p2pchat.client.ClientConnection;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;

import java.util.Arrays;

public class SendMessageCommand extends CommandParser {

    private static final int COMMAND_HELP_CUT = 2;
    private static final int ARG_RECIPIENT = 2;
    private static final int ARG_MESSAGE = 3;
    private static final int EXPECTED_LENGTH = 4;

    @Override
    public CommandResult execute(SplittedCommand command) {
        if (command.argsSize() == EXPECTED_LENGTH) {
            String recipientUuid = command.getStringAt(ARG_RECIPIENT);
            String message = command.getStringAt(ARG_MESSAGE);
            return ClientConnection.sendMessage(recipientUuid,message);
        }
        return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList("client uuid", "message"));
    }
}
