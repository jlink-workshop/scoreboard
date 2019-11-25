package net.johanneslink.scoreboard.console;

import net.johanneslink.scoreboard.core.*;

public class ScoreboardConsoleApp implements ScoreboardView {

	private Console console;
	private ScoreboardPresenter presenter;

	public ScoreboardConsoleApp(Console console) {
		this.console = console;
	}

	public void run(ScoreboardPresenter presenter, CommandInterpreter interpreter) {
		presenter.register(this);
		this.presenter = presenter;
		loop(interpreter);
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
				presenter.score(Points.One);
				break;
			case SCORE_2:
				presenter.score(Points.Two);
				break;
			case SCORE_3:
				presenter.score(Points.Three);
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
		console.println("[Q]uit - Terminate the Scoreboard app");
		console.println("[?|H]elp - Print this message");
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
