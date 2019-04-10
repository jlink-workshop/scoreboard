package net.johanneslink.scoreboard.console;

import java.util.List;

import net.johanneslink.scoreboard.core.Points;
import net.johanneslink.scoreboard.core.Score;
import net.johanneslink.scoreboard.core.ScoreboardPresenter;
import net.johanneslink.scoreboard.core.ScoreboardView;
import net.johanneslink.scoreboard.core.Team;

public class ScoreboardConsoleApp implements ScoreboardView {

    private final Console console;
    private ScoreboardPresenter presenter;

    public ScoreboardConsoleApp(final Console console) {
	this.console = console;
    }

    public void run(final ScoreboardPresenter presenter, final CommandInterpreter interpreter) {
	presenter.register(this);
	this.presenter = presenter;
	loop(interpreter);
    }

    private void loop(final CommandInterpreter interpreter) {
	while (true) {
	    final String line = console.readLine();
	    final Action action = interpreter.parse(line);
	    if (action == Action.QUIT) {
		console.println("Thanx for using Deutsche Board");
		break;
	    }
	    handleAction(action, line, interpreter);
	}
    }

    private void handleAction(final Action action, final String input, final CommandInterpreter interpreter) {
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
	    printHelpString(interpreter);
	    break;
	default:
	    console.println("Unknown command '" + input + "'");
	}
    }

    private void printHelpString(final CommandInterpreter interpreter) {
	final List<String> commandHelps = interpreter.getCommandHelp();
	console.println("Possible commands:");
	for (final String commandHelp : commandHelps) {
	    console.println(commandHelp);
	}
    }

    @Override
    public void displaySelectedTeam(final Team team) {
	if (team == Team.NONE) {
	    return;
	}
	console.println("Team " + team.name() + " selected");
    }

    @Override
    public void displayScore(final Score score) {
	console.println(score.toString());
    }
}
