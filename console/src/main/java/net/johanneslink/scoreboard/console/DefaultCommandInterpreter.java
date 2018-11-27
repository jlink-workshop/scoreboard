package net.johanneslink.scoreboard.console;

public class DefaultCommandInterpreter implements CommandInterpreter {

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
			case "help":
			case "h":
			case "?":
				return Action.HELP;
		}
		return Action.UNKNOWN;
	}

}
