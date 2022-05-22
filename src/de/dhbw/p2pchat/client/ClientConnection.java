package de.dhbw.p2pchat.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.PrimitiveIterator;
import java.util.Scanner;

import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;
import de.dhbw.p2pchat.packets.Message;
import de.dhbw.p2pchat.packets.Packet;

public class ClientConnection {
	private static ClientSocketHandler clientSocketHandler = new ClientSocketHandler();
	private static ClientPacketHandler clientPacketHandler;
	private static int ownPort;
	public static CommandResult connect(String ip, int port, String username) {
		clientPacketHandler = new ClientPacketHandler();
		clientSocketHandler.connect(ip, port);
		clientSocketHandler.getSocketHandler().addListener(clientPacketHandler);
		registerClient(ownPort,username);
		return CommandResult.success("Verbindung erfolgreich hergestellt");
	}
	public static CommandResult connectToClient(String ip, int port, String username) {
		clientPacketHandler = new ClientPacketHandler();
		clientSocketHandler.connect(ip, port);
		clientSocketHandler.getSocketHandler().addListener(clientPacketHandler);
		registerClient(ownPort,username);
		boolean isConntected = true;
		while (isConntected) {
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			if (!input.equals("DISCONNECT")) {
				clientSocketHandler.getSocketHandler().sendPacket(new Message(input));
			} else {
				clientSocketHandler.disconnect();
				isConntected = false;
			}
		}
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

	public static ClientPacketHandler getClientPacketHandler() {
		return clientPacketHandler;
	}

	public static int startOwnServer() {
		ownPort = getFreePort();
		ServerSocketHandler serverSocketHandler = new ServerSocketHandler();
		serverSocketHandler.start(ownPort);
		ServerPacketHandler packetReceiver = new ServerPacketHandler(serverSocketHandler);

		serverSocketHandler.addListener(new SocketMessageListener() {
			@Override
			public void onRecieve(Packet packet) {
				packetReceiver.onRecieve(packet);
			}

			@Override
			public void onDisconnect(Communicator communicator) {
				packetReceiver.onDisconnect(communicator);
			}
		});
		return ownPort;
	}

	private static int getFreePort(){
		int port = 0;
		try ( ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			port = socket.getLocalPort();
		} catch (IOException ignored) {
		}
		if (port > 0) {
			return port;
		}
		throw new RuntimeException("Could not find a free port");
	}
}
