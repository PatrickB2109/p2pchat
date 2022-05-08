package de.dhbw.p2pchat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketHandler;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.Packet;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

public class ServerSocketHandler {

	private ServerSocket serverSocket;
	private ArrayList<SocketHandler> socketHandlers = new ArrayList<>();
	private List<SocketMessageListener> listeners = new ArrayList<>();

	public void start(int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(() -> {

			while (true) {
				Socket socket = null;
				try {
					socket = serverSocket.accept();

					String newUUID = UUID.randomUUID().toString();
					SocketHandler socketHandler = new SocketHandler(new Communicator(newUUID), socket);
					Logger.log("Neuer Client (" + socketHandler.getCommunicator().getUuid() + ") wurde erkannt.",
							LogSource.SERVER);

					socketHandler.addListener(new SocketMessageListener() {

						@Override
						public void onRecieve(Packet packet) {
							for (SocketMessageListener listener : listeners) {
								listener.onRecieve(packet);
							}
						}

						@Override
						public void onDisconnect(Communicator communicator) {
							for (SocketMessageListener listener : listeners) {
								listener.onDisconnect(communicator);
							}
						}
					});
					socketHandlers.add(socketHandler);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		});
		thread.start();
	}

	public void sendPacket(Packet packet, Communicator communicator) {
		List<Communicator> one = new ArrayList<>();
		one.add(communicator);

		sendToMultiple(packet, one);
	}

	public void sendToMultiple(Packet packet, List<Communicator> communicatorList) {
		for (Communicator communicator : communicatorList) {
			for (SocketHandler all : socketHandlers) {
				if (all.getCommunicator().equals(communicator)) {
					all.sendPacket(packet);
				}
			}
		}
	}

	public void sendToAll(Packet packet) {
		for (SocketHandler all : socketHandlers) {
			all.sendPacket(packet);
		}
	}

	public void addListener(SocketMessageListener listener) {
		listeners.add(listener);
	}

	public int getPort() {
		return serverSocket.getLocalPort();
	}

}