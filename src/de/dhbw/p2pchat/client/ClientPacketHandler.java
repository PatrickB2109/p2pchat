package de.dhbw.p2pchat.client;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.ClientListPacket;
import de.dhbw.p2pchat.packets.Message;
import de.dhbw.p2pchat.packets.Packet;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

public class ClientPacketHandler implements SocketMessageListener {

	private ClientListPacket listPacket;
	
	@Override
	public void onRecieve(Packet packet) {

		if (packet instanceof ClientListPacket) {
			listPacket = (ClientListPacket) packet;
			int index = 0;
			for (Communicator communicator : listPacket.getClients()) {
				System.out.println("Nr: " + index++ + ": UUID: " + communicator.getUuid() +"; IP:"+communicator.getIp()+"; Port:"+communicator.getPort() +"; Username: " + communicator.getUsername() + ";");
			}
		} else if (packet instanceof Message) {
			Message message = (Message) packet;
			System.out.println(message.getDateSent()+":"+message.getSender().getUsername()+":"+message.getContent());
		}

	}

	@Override
	public void onDisconnect(Communicator communicator) {
		Logger.log("Die Verbindung zum Server wurde verloren.", LogSource.CLIENT);
	}

	public ClientListPacket getListPacket() {
		return listPacket;
	}
}
