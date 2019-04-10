package net.johanneslink.scoreboard.console;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCommandInterpreter implements CommandInterpreter {
    private final List<Command> commands;

    public DefaultCommandInterpreter() {
	commands = new ArrayList<>();

	commands.add(new Command(Action.SELECT_A, "Select [A] - Select team A for scoring", "select a", "a"));
	commands.add(new Command(Action.SELECT_B, "Select [B] - Select team B for scoring", "select b", "b"));
	commands.add(new Command(Action.SCORE_1, "Score [1] - Score 1 point for selected team", "score 1", "1"));
	commands.add(new Command(Action.SCORE_2, "Score [2] - Score 2 points for selected team", "score 2", "2"));
	commands.add(new Command(Action.SCORE_3, "Score [3] - Score 3 points for selected team", "score 3", "3"));
	commands.add(new Command(Action.INCREMENT, "Increment [+] - Increment score by 1 point for selected team",
		"increment", "+"));
	commands.add(new Command(Action.DECREMENT, "Decrement [-] - Decrement score by 1 point for selected team",
		"decrement", "-"));
	commands.add(new Command(Action.QUIT, "[Q]uit - Terminate the Scoreboard app", "quit", "q"));
	commands.add(new Command(Action.HELP, "[?|H]elp - Print this message", "help", "h", "?"));
    }

    @Override
    public Action parse(final String line) {

	for (final Command command : commands) {
	    if (command.handles(line.toLowerCase())) {
		return command.getAction();
	    }
	}

	return Action.UNKNOWN;
    }

    @Override
    public List<String> getCommandHelp() {
	return commands.stream().map(Command::getCommandHelp).collect(Collectors.toList());
    }

}
