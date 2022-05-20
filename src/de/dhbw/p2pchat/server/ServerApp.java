package de.dhbw.p2pchat.server;

import de.dhbw.p2pchat.network.Communicator;
import de.dhbw.p2pchat.network.SocketMessageListener;
import de.dhbw.p2pchat.packets.Packet;
import de.dhbw.p2pchat.util.LogSource;
import de.dhbw.p2pchat.util.Logger;

public class ServerApp {

	private static ServerApp serverApp;
	private ServerSocketHandler serverSocketHandler;

	public static void main(String[] args) {
		ServerApp.getInstance().startServerApplication();
	}

	public static ServerApp getInstance() {
		if (serverApp == null) {
			serverApp = new ServerApp();
		}
		return serverApp;
	}

	public void startServerApplication() {
		Logger.log("Der Server wird gestartet.", LogSource.SERVER);
		serverSocketHandler = new ServerSocketHandler();
		serverSocketHandler.start(1337);
		Logger.log("Der Server wurde gestartet auf Port " + serverSocketHandler.getPort() + ".", LogSource.SERVER);

		ServerPacketHandler packetReceiver = new ServerPacketHandler(serverSocketHandler);
		
		serverSocketHandler.addListener(new SocketMessageListener() {
			@Override
			public void onRecieve(Packet packet) {
				packetReceiver.onRecieve(packet);
			}

			@Override
			public void onDisconnect(Communicator communicator) {
				packetReceiver.onDisconnect(communicator);
			}
		});
	}

}