package de.dhbw.p2pchat.client.commands;

import de.dhbw.p2pchat.client.ClientConnection;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;
import de.dhbw.p2pchat.network.Communicator;

import java.util.Arrays;

public class ConnectClientCommand extends CommandParser {

    private static final int COMMAND_HELP_CUT = 2;
    private static final int EXPECTED_LENGTH = 3;
    private static final int ARG_CLIENT_UUID = 2;

    @Override
    public CommandResult execute(SplittedCommand command) {
        if (command.argsSize() == EXPECTED_LENGTH) {
            String clientUuid = command.getStringAt(ARG_CLIENT_UUID);
            return ClientConnection.connectToClient(clientUuid);
        }
        return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList("client uuid"));
    }
}
