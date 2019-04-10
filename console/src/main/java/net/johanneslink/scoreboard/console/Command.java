package net.johanneslink.scoreboard.console;

public class Command {

    private final Action action;
    private final String[] inputs;
    private final String help;

    public String[] getInputs() {
	return inputs;
    }

    public Command(final Action action, final String help, final String... inputs) {
	this.action = action;
	this.help = help;
	this.inputs = inputs;
    }

    public Action getAction() {
	return action;
    }

    public boolean handles(final String line) {
	for (final String input : inputs) {
	    if (input.equals(line)) {
		return true;
	    }
	}
	return false;
    }

    public String getCommandHelp() {
	return help;
    }
}
