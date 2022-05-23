package de.dhbw.p2pchat.client;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.*;
import de.dhbw.p2pchat.client.ServerSocketHandler;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
			Logger.log("Client hat sich mit dir verbunden. IP: " + sender.getIp() + "; Port: " + sender.getPort()
					+ "; Username: " + sender.getUsername() + ";", LogSource.SERVER);
		}else if (packet instanceof Message) {
			Message message = (Message) packet;
			System.out.println(message.getDateSent()+":"+message.getSenderName()+":"+message.getContent());
		}
	}

	public synchronized void onDisconnect(Communicator communicator) {
		lobby.remove(communicator);
	}

}
