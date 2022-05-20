package de.dhbw.p2pchat.client.userinput;

import java.util.List;
import java.util.Set;

public class CommandResult {

	private boolean successful;
	private String additionalInfo;

	protected CommandResult(boolean successful) {
		super();
		this.successful = successful;
	}

	protected CommandResult(boolean successful, String additionalInfo) {
		this(successful);
		this.additionalInfo = additionalInfo;
	}

	public static CommandResult success(String additionalInfo) {
		return new CommandResult(true, additionalInfo);
	}

	public static CommandResult error(String additionalInfo) {
		return new CommandResult(false, additionalInfo);
	}

	public static CommandResult usage(String cmd, List<String> input) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("incomplete command: ");
		stringBuilder.append(cmd);
		stringBuilder.append(' ');
		for (String part : input) {
			stringBuilder.append('<');
			stringBuilder.append(part);
			stringBuilder.append("> ");
		}
		return error(stringBuilder.toString().trim());
	}

	public static CommandResult usage(String cmd, Set<String> possibilities) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("incomplete command: ");
		stringBuilder.append(cmd);
		if (cmd.length() != 0) {
			stringBuilder.append(' ');
		}
		stringBuilder.append('<');
		stringBuilder.append(String.join("|", possibilities));
		stringBuilder.append('>');

		return error(stringBuilder.toString());
	}

	public boolean isSuccessful() {
		return successful;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		if (successful) {
			stringBuilder.append("SUCCESS");
		} else {
			stringBuilder.append("ERROR");
		}

		if (additionalInfo != null) {
			stringBuilder.append(": ");
			stringBuilder.append(additionalInfo);
		}

		return stringBuilder.toString();
	}

}