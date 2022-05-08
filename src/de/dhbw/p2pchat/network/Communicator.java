package de.dhbw.p2pchat.network;

import java.io.Serializable;

public class Communicator implements Serializable {

	private String uuid;
	private String ip;
	private int port;

	public Communicator() {
		this.uuid = "";
	}

	public Communicator(String uuid) {
		this.uuid = uuid;
	}

	public Communicator(String uuid, String ip, int port) {
		this.uuid = uuid;
		this.ip = ip;
		this.port = port;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Communicator) {
			Communicator sp = (Communicator) obj;

			if (sp.getUuid().equals(this.getUuid()))
				return true;
		}
		return false;
	}

}
