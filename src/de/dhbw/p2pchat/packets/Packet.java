package de.dhbw.p2pchat.packets;

import java.io.Serializable;
import java.util.UUID;

import de.dhbw.p2pchat.network.Communicator;

public abstract class Packet implements Serializable {

	private String uuid = null;
	private Communicator sender = null;

	public Packet() {
		uuid = UUID.randomUUID().toString();
	}

	public String getPacketUUID() {
		return uuid;
	}

	public void setSender(Communicator sender) {
		this.sender = sender;
	}

	public Communicator getSender() {
		return sender;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Packet) {
			Packet packet = (Packet) obj;

			return this.getPacketUUID().equals(packet.getPacketUUID());
		}
		return false;
	}
}
