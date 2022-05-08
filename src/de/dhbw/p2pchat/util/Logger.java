package de.dhbw.p2pchat.util;

public class Logger {

	private Logger() {
	}

	public static void log(String msg, LogSource source) {
		System.out.println("[P2PChat // " + source.name() + "] " + msg);
	}

}