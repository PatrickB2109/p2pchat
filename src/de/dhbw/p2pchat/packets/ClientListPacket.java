package de.dhbw.p2pchat.packets;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.p2pchat.network.Communicator;

public class ClientListPacket extends Packet {
	List<Communicator> clients = new ArrayList<>();

	public ClientListPacket(List<Communicator> clients) {
		this.clients = clients;
	}

	public List<Communicator> getClients() {
		return clients;
	}
}