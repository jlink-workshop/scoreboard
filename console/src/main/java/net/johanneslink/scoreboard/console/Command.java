package net.johanneslink.scoreboard.console;

import java.util.*;

public class Command {

	private List<String> commandStrings;
	private String helpText;
	private Action action;


	public Command(List<String> commandStrings, String helpText, Action action) {
		this.commandStrings = commandStrings;
		this.helpText = helpText;
		this.action = action;
	}

	public boolean isCommandKnown(String command) {
		return true;
	}
}
