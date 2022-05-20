package de.dhbw.p2pchat.server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.ClientListPacket;
import de.dhbw.p2pchat.packets.ClientListRequestPacket;
import de.dhbw.p2pchat.packets.Packet;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

public class ServerPacketHandler implements SocketMessageListener {
	private ServerSocketHandler serverSocketHandler;
	private ArrayList<Communicator> lobby = new ArrayList<>();

	public ServerPacketHandler(ServerSocketHandler serverSocketHandler) {
		this.serverSocketHandler = serverSocketHandler;
	}

	public synchronized void onRecieve(Packet packet) {
		if (packet instanceof ClientIsReadyToChatPacket) {
			ClientIsReadyToChatPacket clientRegisterPacket = (ClientIsReadyToChatPacket) packet;
			Communicator sender = clientRegisterPacket.getSender();
			sender.setIp(clientRegisterPacket.getIp());
			sender.setPort(clientRegisterPacket.getPort());
			sender.setUsername(clientRegisterPacket.getUsername());
			lobby.add(sender);
			Logger.log("Client hat sich registriert. IP: " + sender.getIp() + "; Port: " + sender.getPort()
					+ "; Username: " + sender.getUsername() + ";", LogSource.SERVER);
		} else if (packet instanceof ClientListRequestPacket) {
			ClientListRequestPacket requestPacket = (ClientListRequestPacket) packet;
			serverSocketHandler.sendPacket(new ClientListPacket(lobby.stream()
					.filter(e -> e.getUuid() != requestPacket.getSender().getUuid()).collect(Collectors.toList())),
					requestPacket.getSender());
			Logger.log("Client " + requestPacket.getSender().getUuid() + " hat die Nutzerliste angefordert",
					LogSource.SERVER);
		}
	}

	public synchronized void onDisconnect(Communicator communicator) {
		lobby.remove(communicator);
	}

}
