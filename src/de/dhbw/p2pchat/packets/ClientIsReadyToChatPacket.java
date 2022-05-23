package de.dhbw.p2pchat.packets;

public class ClientIsReadyToChatPacket extends Packet {
	private String ip;
	private int port;
	private String username;

	public ClientIsReadyToChatPacket(String ip, int port, String username) {
		this.ip = ip;
		this.port = port;
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}
}
