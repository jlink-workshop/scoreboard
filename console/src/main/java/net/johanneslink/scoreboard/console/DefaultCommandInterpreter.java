package net.johanneslink.scoreboard.console;

import java.util.*;

public class DefaultCommandInterpreter implements CommandInterpreter {

	private List<Command> commandsList = new ArrayList<>();

	public DefaultCommandInterpreter() {
		commandsList.add(new Command(Arrays.asList("q", "quit"), "[Q]uit - Terminate the Scoreboard app", Action.QUIT));
		commandsList.add(new Command(Arrays.asList("a", "select a"), "Select [A] - Select team A for scoring", Action.SELECT_A));
		commandsList.add(new Command(Arrays.asList("b", "select b"), "Select [B] - Select team B for scoring", Action.SELECT_B));
		commandsList.add(new Command(Arrays.asList("1", "score 1"), "Score [1] - Score 1 point for selected team", Action.SCORE_1));
		commandsList.add(new Command(Arrays.asList("2", "score 2"), "Score [2] - Score 2 points for selected team", Action.SCORE_2));
		commandsList.add(new Command(Arrays.asList("3", "score 3"), "Score [3] - Score 3 points for selected team", Action.SCORE_3));
		commandsList.add(new Command(Arrays.asList("inc", "+"), "Inc [+] - Increase by 1 point for selected team keeping team", Action.PLUS));
		commandsList.add(new Command(Arrays.asList("dec", "-"), "Dec [-] - Decrement by 1 point for selected team keeping team", Action.MINUS));
		commandsList.add(new Command(Arrays.asList("r", "reset","?"), "[R]eset - Resets the score to 000:000", Action.RESET));
		commandsList.add(new Command(Arrays.asList("h", "help","?"), "[?|H]elp - Print this message", Action.HELP));
	}

	public List<Command> getCommandsList() {
		return this.commandsList;
	}


	@Override
	public Action parse(String line) {

		switch (line.toLowerCase()) {
			case "quit":
			case "q":
				return Action.QUIT;
			case "select a":
			case "a":
				return Action.SELECT_A;
			case "select b":
			case "b":
				return Action.SELECT_B;
			case "score 1":
			case "1":
				return Action.SCORE_1;
			case "score 2":
			case "2":
				return Action.SCORE_2;
			case "score 3":
			case "3":
				return Action.SCORE_3;
			case "dec":
			case "-":
				return Action.MINUS;
			case "+":
			case "inc":
				return Action.PLUS;
			case "r":
			case "reset":
			case "Reset":
			case "R":
				return Action.RESET;
			case "help":
			case "h":
			case "?":
				return Action.HELP;
			default:
				return Action.UNKNOWN;
		}
	}
}
