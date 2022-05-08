package de.dhbw.p2pchat.packets;

public class ClientIsReadyToChatPacket extends Packet {
	private String ip;
	private int port;
	
	public ClientIsReadyToChatPacket(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
}
