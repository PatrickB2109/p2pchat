package de.dhbw.p2pchat.client;

import de.dhbw.p2pchat.client.commandparser.BaseCommandParser;
import de.dhbw.p2pchat.client.userinput.TerminalHandler;

public class ClientApp {
	static TerminalHandler terminalHandler;
	static boolean isConnectedToOtherClient;
	public static void main(String[] args) {
		ClientApp clientApp = new ClientApp();
		clientApp.start();
	}

	private void start() {
		ClientConnection.startOwnServer();
		BaseCommandParser commandParser = new BaseCommandParser();
		terminalHandler = new TerminalHandler(commandParser);
		terminalHandler.start();
	}

	public static TerminalHandler getTerminalHandler() {
		return terminalHandler;
	}

	public static boolean isIsConnectedToOtherClient() {
		return isConnectedToOtherClient;
	}

	public static void setIsConnectedToOtherClient(boolean isConnectedToOtherClient) {
		ClientApp.isConnectedToOtherClient = isConnectedToOtherClient;
	}
}