package de.dhbw.p2pchat.client.commands;

import de.dhbw.p2pchat.client.ClientConnection;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;
import de.dhbw.p2pchat.network.Communicator;

import java.util.Arrays;

public class ConnectClientCommand extends CommandParser {


    private static final int COMMAND_HELP_CUT = 2;
    private static final int EXPECTED_LENGTH = 4;
    private static final int ARG_CLIENT_NUMBER = 2;
    private static final int ARG_USERNAME = 3;

    @Override
    public CommandResult execute(SplittedCommand command) {
        if (command.argsSize() == EXPECTED_LENGTH) {
            int clientNumber = Integer.parseInt( command.getStringAt(ARG_CLIENT_NUMBER));
            String username = command.getStringAt(ARG_USERNAME);
            Communicator targetClient = ClientConnection.getClientPacketHandler().getListPacket().getClients().get(clientNumber);
            return ClientConnection.connectToClient(targetClient.getIp(), targetClient.getPort(), username);
        }
        return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList("client number","username"));
    }
}
