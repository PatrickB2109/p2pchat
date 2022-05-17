package de.dhbw.p2pchat.client;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.ClientListPacket;
import de.dhbw.p2pchat.packets.Packet;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

public class ClientPacketHandler implements SocketMessageListener {

	@Override
	public void onRecieve(Packet packet) {

		if (packet instanceof ClientListPacket) {
			ClientListPacket listPacket = (ClientListPacket) packet;
			System.out.println(listPacket.getClients().get(0).getUsername());
		}

	}

	@Override
	public void onDisconnect(Communicator communicator) {
		Logger.log("Die Verbindung zum Server wurde verloren.", LogSource.CLIENT);
	}

}
