package de.dhbw.p2pchat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;

import de.dhbw.p2pchat.client.userinput.CommandResult;
import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;
import de.dhbw.p2pchat.packets.Message;
import de.dhbw.p2pchat.packets.Packet;

public class ClientConnection {
	private static HashMap<String, ClientSocketHandler> clientSocketHandlerByUuid = new HashMap<>();
	private static ClientSocketHandler clientSocketToMainServerHandler = new ClientSocketHandler();
	private static ClientPacketHandler clientPacketHandler;
	private static int ownPort;
	private static String myUsername;

	public static CommandResult connect(String ip, int port, String username) {
		clientPacketHandler = new ClientPacketHandler();
		clientSocketToMainServerHandler.connect(ip, port);
		clientSocketToMainServerHandler.getSocketHandler().addListener(clientPacketHandler);
		myUsername = username;
		registerClient(ownPort, username);
		return CommandResult.success("Verbindung erfolgreich hergestellt");
	}

	public static CommandResult connectToClient(String clientUuid) {
		Communicator targetClient = ClientConnection.getClientPacketHandler().getClientsByUUid().get(clientUuid);
		if (targetClient != null) {

			ClientPacketHandler clientPacketHandler = new ClientPacketHandler();
			ClientSocketHandler clientSocketHandler = new ClientSocketHandler();
			clientSocketHandler.connect(targetClient.getIp(), targetClient.getPort());
			clientSocketHandler.getSocketHandler().addListener(clientPacketHandler);
			clientSocketHandlerByUuid.put(clientUuid, clientSocketHandler);
			registerClient(ownPort, myUsername);
			return CommandResult.success("Verbindung erfolgreich hergestellt");
		} else {
			return CommandResult.error("client not found. Get client list (Command:SERVER LISTUSERS)");
		}

	}

	public static CommandResult autoConnect(String username) {
		return connect("localhost", 1337, username);
	}

	public static CommandResult disconnect() {
		if (clientSocketToMainServerHandler.getSocketHandler() != null) {
			clientSocketToMainServerHandler.disconnect();
			return CommandResult.success("Verbindung erfolgreich getrennt");
		}
		return CommandResult.error("Keine Verbindung vorhanden");
	}

	public static CommandResult showUsers() {
		if (clientSocketToMainServerHandler.getSocketHandler() != null) {
			clientSocketToMainServerHandler.getSocketHandler().sendPacket(new ClientListRequestPacket());
			return CommandResult.success("UserList erfolgreich angefragt");
		}
		return CommandResult.error("Nicht verbunden");
	}

	public static CommandResult status() {
		if (clientSocketToMainServerHandler.getSocketHandler() != null) {
			return CommandResult.success("VERBUNDEN");
		}
		return CommandResult.success("NICHT VERBUNDEN");
	}

	private static void registerClient(int port, String username) {
		String ownIP = getOwnIp();
		ClientIsReadyToChatPacket registerPacket = new ClientIsReadyToChatPacket(ownIP, port, username);
		clientSocketToMainServerHandler.getSocketHandler().sendPacket(registerPacket);
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
		ServerPacketHandler packetReceiver = new ServerPacketHandler();

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

	private static int getFreePort() {
		int port = 0;
		try (ServerSocket socket = new ServerSocket(0)) {
			socket.setReuseAddress(true);
			port = socket.getLocalPort();
		} catch (IOException ignored) {
		}
		if (port > 0) {
			return port;
		}
		throw new RuntimeException("Could not find a free port");
	}

	public static CommandResult startChat(String recepientUuid) {
		if (clientSocketHandlerByUuid.get(recepientUuid) != null) {

			boolean isConntected = true;
			System.out.println("Started chat:");
			while (isConntected) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String input = null;
				try {
					input = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (!input.equals("LEAVE")) {
					clientSocketHandlerByUuid.get(recepientUuid).getSocketHandler()
							.sendPacket(new Message(input, myUsername));
				} else {
					clientSocketHandlerByUuid.get(recepientUuid).disconnect();
					clientSocketHandlerByUuid.remove(recepientUuid);
					isConntected = false;
				}
			}
			return CommandResult.success("Left Chat");
		} else {
			return CommandResult.error("not connected to this client yet");
		}
	}
}
