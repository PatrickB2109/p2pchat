package de.dhbw.p2pchat.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;

public class ClientApp {

	private ClientSocketHandler clientSocketHandler;

	public static void main(String[] args) {
		ClientApp clientApp = new ClientApp();
		clientApp.start();
	}

	private void start() {
		String ownIP = null;
		int port = 1337;
		try {
			ownIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		clientSocketHandler = new ClientSocketHandler();
		clientSocketHandler.connect("localhost", port);

		ClientIsReadyToChatPacket registerPacket = new ClientIsReadyToChatPacket(ownIP, port);
		clientSocketHandler.getSocketHandler().sendPacket(registerPacket);

		clientSocketHandler.getSocketHandler().sendPacket(new ClientListRequestPacket());

		clientSocketHandler.getSocketHandler().addListener(new ClientPacketHandler());
	}

	public ClientSocketHandler getClientSocketHandler() {
		return clientSocketHandler;
	}
}