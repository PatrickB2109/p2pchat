package de.dhbw.p2pchat.server;

import java.util.ArrayList;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;
import de.dhbw.p2pchat.packets.Packet;

public class ServerPacketHandler {
	private ServerSocketHandler serverSocketHandler;
	private ArrayList<Communicator> lobby = new ArrayList<>();

	public ServerPacketHandler(ServerSocketHandler serverSocketHandler) {
		this.serverSocketHandler = serverSocketHandler;
	}

	public synchronized void onPacketRecieved(Packet packet) {
		if (packet instanceof ClientIsReadyToChatPacket) {
			ClientIsReadyToChatPacket clientRegisterPacket = (ClientIsReadyToChatPacket) packet;
			lobby.add(clientRegisterPacket.getSender());
		} else if (packet instanceof ClientListRequestPacket) {
			ClientListRequestPacket requestPacket = (ClientListRequestPacket) packet;
			serverSocketHandler.sendPacket(new ClientListPacket(lobby), requestPacket.getSender());
		}
	}

	public synchronized void onDisconnect(Communicator communicator) {
		lobby.remove(communicator);
	}

}
