package de.dhbw.p2pchat.client;

import de.dhbw.p2pchat.client.commandparser.BaseCommandParser;
import de.dhbw.p2pchat.client.userinput.TerminalHandler;

public class ClientApp {

	public static void main(String[] args) {
		ClientApp clientApp = new ClientApp();
		clientApp.start();
	}

	private void start() {
		BaseCommandParser commandParser = new BaseCommandParser();
		TerminalHandler terminalHandler = new TerminalHandler(commandParser);
		terminalHandler.start();
	}

}