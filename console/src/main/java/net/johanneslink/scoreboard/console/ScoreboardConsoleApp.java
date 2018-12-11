package net.johanneslink.scoreboard.console;

import net.johanneslink.scoreboard.core.*;

public class ScoreboardConsoleApp implements ScoreboardView {

	private Console console;
	private ScoreboardPresenter presenter;
	private DefaultCommandInterpreter defaultCommandInterpreter;

	public ScoreboardConsoleApp(Console console) {
		this.console = console;
	}

	public void run(ScoreboardPresenter presenter, DefaultCommandInterpreter defaultCommandInterpreter) {
		presenter.register(this);
		this.presenter = presenter;
		this.defaultCommandInterpreter = defaultCommandInterpreter;
		loop(defaultCommandInterpreter);
	}

	private void loop(CommandInterpreter interpreter) {
		while (true) {
			String line = console.readLine();
			Action action = interpreter.parse(line);
			if (action == Action.QUIT) break;
			handleAction(action, line);
		}
	}

	private void handleAction(Action action, String input) {
		switch (action) {
			case SELECT_A:
				presenter.select(Team.A);
				break;
			case SELECT_B:
				presenter.select(Team.B);
				break;
			case SCORE_1:
				presenter.score(Points.ONE, ModifyScore.INC);
				break;
			case SCORE_2:
				presenter.score(Points.TWO, ModifyScore.INC);
				break;
			case SCORE_3:
				presenter.score(Points.THREE, ModifyScore.INC);
				break;
			case PLUS:
				presenter.score(Points.ONE, ModifyScore.INC, true);
				break;
			case MINUS:
				presenter.score(Points.ONE, ModifyScore.DEC, true);
				break;
			case RESET:
				presenter.reset();
				break;
			case HELP:
				printHelpString();
				break;
			default:
				console.println("Unknown command '" + input + "'");
		}
	}

	private void printHelpString() {
		console.println("Possible commands:");
		console.println("Select [A] - Select team A for scoring");
		console.println("Select [B] - Select team B for scoring");
		console.println("Score [1] - Score 1 point for selected team");
		console.println("Score [2] - Score 2 points for selected team");
		console.println("Score [3] - Score 3 points for selected team");
		console.println("Dec [-] - Decrement by 1 point for selected team keeping team");
		console.println("Inc [+] - Increase by 1 point for selected team keeping team");
		console.println("[R]eset - Resets the score to 000:000");
		console.println("[Q]uit - Terminate the Scoreboard app");
		console.println("[?|H]elp - Print this message");
		defaultCommandInterpreter.getCommandsList();
	}

	@Override
	public void displaySelectedTeam(Team team) {
		if (team == Team.NONE) return;
		console.println("Team " + team.name() + " selected");
	}

	@Override
	public void displayScore(Score score) {
		console.println(score.toString());
	}
}
