package de.dhbw.p2pchat.client.userinput;

public class SplittedCommand {

	private String[] splittedCommand;
	private String seperator;

	public SplittedCommand(String command, String seperator) {
		super();
		this.seperator = seperator;
		this.splittedCommand = command.split(seperator);
	}

	public String getStringAt(int pos) {
		if (splittedCommand.length > pos) {
			return splittedCommand[pos];
		} else {
			return null;
		}
	}


	public String getStringUppercaseAt(int pos) {
		String string = getStringAt(pos);
		if (string != null) {
			return string.toUpperCase();
		}
		return null;
	}

	public int getIntAt(int pos) throws NumberFormatException {
		return Integer.valueOf(getStringAt(pos));
	}

	public int argsSize() {
		return splittedCommand.length;
	}

	public String getCommandUpToPos(int position) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < position; i++) {
			stringBuilder.append(getStringAt(i) + seperator);
		}
		return stringBuilder.toString().trim();
	}

}