package de.dhbw.p2pchat.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;

public class ClientConnection {
	private static ClientSocketHandler clientSocketHandler = new ClientSocketHandler();

	public static CommandResult connect(String ip, int port, String username) {
		clientSocketHandler.connect(ip, port);
		clientSocketHandler.getSocketHandler().addListener(new ClientPacketHandler());
		registerClient(port, username);

		return CommandResult.success("Verbindung erfolgreich hergestellt");
	}

	public static CommandResult autoConnect(String username) {
		// TODO not implemented yet
		return connect("localhost", 1337, username);
	}

	public static CommandResult disconnect() {
		if (clientSocketHandler.getSocketHandler() != null) {
			clientSocketHandler.disconnect();
			return CommandResult.success("Verbindung erfolgreich getrennt");
		}
		return CommandResult.error("Keine Verbindung vorhanden");
	}

	public static CommandResult showUsers() {
		if (clientSocketHandler.getSocketHandler() != null) {
			clientSocketHandler.getSocketHandler().sendPacket(new ClientListRequestPacket());
			return CommandResult.success("UserList erfolgreich angefragt");
		}
		return CommandResult.error("Nicht verbunden");
	}

	public static CommandResult status() {
		if (clientSocketHandler.getSocketHandler() != null) {
			return CommandResult.success("VERBUNDEN");
		}
		return CommandResult.success("NICHT VERBUNDEN");
	}

	private static void registerClient(int port, String username) {
		String ownIP = getOwnIp();
		ClientIsReadyToChatPacket registerPacket = new ClientIsReadyToChatPacket(ownIP, port, username);
		clientSocketHandler.getSocketHandler().sendPacket(registerPacket);
	}

	private static String getOwnIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}
}
