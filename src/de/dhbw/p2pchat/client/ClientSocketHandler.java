package de.dhbw.p2pchat.client;

import java.io.IOException;
import java.net.Socket;

import de.dhbw.p2pchat.network.SocketHandler;

public class ClientSocketHandler {

	private SocketHandler socketHandler;

	public void connect(String ip, int port) {
		try {
			Socket socket = new Socket(ip, port);
			socketHandler = new SocketHandler(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		socketHandler = null;
	}

	public SocketHandler getSocketHandler() {
		return socketHandler;
	}
}