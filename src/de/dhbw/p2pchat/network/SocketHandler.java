package de.dhbw.p2pchat.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import de.dhbw.p2pchat.packets.Packet;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

public class SocketHandler {

	private Communicator communicator;
	private ObjectOutputStream objectOutputStream;
	private ArrayList<SocketMessageListener> socketMessageListener = new ArrayList<>();

	public SocketHandler(Socket socket) {
		this(new Communicator(), socket);
	}

	public SocketHandler(Communicator communicator, Socket socket) {
		this.communicator = communicator;

		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Thread thread = new Thread(() -> {
			ObjectInputStream stream = null;
			try {
				stream = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (true) {
				try {
					Object object = stream.readObject();
					Packet packet = (Packet) object;
					packet.setSender(communicator);

					for (SocketMessageListener all : socketMessageListener) {
						all.onRecieve(packet);
					}
				} catch (IOException | ClassNotFoundException e) {
					for (SocketMessageListener all : socketMessageListener) {
						all.onDisconnect(communicator);
					}
					break;
				}

			}
		});
		thread.start();
	}

	public void addListener(SocketMessageListener listener) {
		socketMessageListener.add(listener);
	}

	public void sendPacket(Packet packet) {
		packet.setSender(communicator);

		synchronized (objectOutputStream) {
			try {
				objectOutputStream.writeObject(packet);
				objectOutputStream.flush();
			} catch (IOException e) {
				Logger.log("Packet " + packet.getClass().getName() + " konnte nicht gesendet werden.",
						LogSource.NETWORK);
			}
		}
	}

	public Communicator getCommunicator() {
		return communicator;
	}
}
