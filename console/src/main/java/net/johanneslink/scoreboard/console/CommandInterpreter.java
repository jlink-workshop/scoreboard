package net.johanneslink.scoreboard.console;

public interface CommandInterpreter {
	Action parse(String line);
}
