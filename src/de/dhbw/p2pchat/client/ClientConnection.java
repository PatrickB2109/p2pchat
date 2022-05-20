package de.dhbw.p2pchat.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;

public class ClientConnection {

	public static CommandResult connect(String ip, int port, String username) {
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
