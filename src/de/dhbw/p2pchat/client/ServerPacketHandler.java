package de.dhbw.p2pchat.client;

import java.util.ArrayList;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.ClientIsReadyToChatPacket;
import de.dhbw.p2pchat.packets.Message;
import de.dhbw.p2pchat.packets.Packet;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

public class ServerPacketHandler implements SocketMessageListener {
	private ArrayList<Communicator> lobby = new ArrayList<>();

	@Override
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
		} else if (packet instanceof Message) {
			Message message = (Message) packet;
			System.out.println(message.getDateSent() + ":" + message.getSenderName() + ":" + message.getContent());
		}
	}

	@Override
	public synchronized void onDisconnect(Communicator communicator) {
		lobby.remove(communicator);
	}

}
