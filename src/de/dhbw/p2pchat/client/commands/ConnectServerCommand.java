package de.dhbw.p2pchat.client.commands;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import de.dhbw.p2pchat.client.ClientPacketHandler;
import de.dhbw.p2pchat.client.ClientSocketHandler;
import de.dhbw.p2pchat.client.commandparser.CommandParser;
import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.client.userinput.SplittedCommand;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;

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
			return connect(ip, Integer.parseInt(port), username);
		}
		return CommandResult.usage(command.getCommandUpToPos(COMMAND_HELP_CUT), Arrays.asList("ip", "port", "username"));
	}
	
	private CommandResult connect(String ip, int port, String username) {
		String ownIP = "";
		try {
			ownIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		ClientSocketHandler clientSocketHandler = new ClientSocketHandler();
		clientSocketHandler.connect(ip, port);

		ClientIsReadyToChatPacket registerPacket = new ClientIsReadyToChatPacket(ownIP, port, username);
		clientSocketHandler.getSocketHandler().sendPacket(registerPacket);

		clientSocketHandler.getSocketHandler().sendPacket(new ClientListRequestPacket());

		clientSocketHandler.getSocketHandler().addListener(new ClientPacketHandler());
		return CommandResult.success(null);
	}
}