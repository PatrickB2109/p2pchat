package de.dhbw.p2pchat.client.userinput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.dhbw.p2pchat.client.commandparser.UppercaseCommandParser;

public class TerminalHandler {

	private static final String SEPERATOR = " ";

	private UppercaseCommandParser commandParser;
	private Thread thread;

	public TerminalHandler(UppercaseCommandParser commandParser) {
		this.commandParser = commandParser;
	}

	public void start() {
		thread = new Thread(() -> {
			while (!Thread.interrupted()) {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					String input = reader.readLine();

					SplittedCommand splittedCommand = new SplittedCommand(input, SEPERATOR);
					CommandResult result = commandParser.execute(splittedCommand);
					printResult(result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	private void printResult(CommandResult result) {
		System.out.println(result.toString());
	}

	public void stopThread() {
		thread.interrupt();
	}

}