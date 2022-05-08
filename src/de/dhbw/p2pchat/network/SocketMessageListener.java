package de.dhbw.p2pchat.network;

import de.dhbw.p2pchat.packets.Packet;

public interface SocketMessageListener {

	public void onRecieve(Packet packet);

	public void onDisconnect(Communicator communicator);

}
