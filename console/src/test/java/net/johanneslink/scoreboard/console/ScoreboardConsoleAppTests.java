package net.johanneslink.scoreboard.console;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import net.johanneslink.scoreboard.core.Points;
import net.johanneslink.scoreboard.core.Score;
import net.johanneslink.scoreboard.core.ScoreboardPresenter;
import net.johanneslink.scoreboard.core.Team;

class ScoreboardConsoleAppTests {

    private Console console;
    private ScoreboardConsoleApp app;
    private ScoreboardPresenter presenter;
    private CommandInterpreter interpreter;

    @BeforeEach
    void initialize() {
	console = mock(Console.class);
	app = new ScoreboardConsoleApp(console);
	presenter = mock(ScoreboardPresenter.class);
	interpreter = mock(CommandInterpreter.class);

	// Default behavior of console and interpreter:
	when(console.readLine()).thenReturn("any input");
	when(interpreter.parse(anyString())).thenReturn(Action.QUIT);
    }

    @Test
    void runningAppRegistersItselfAsScoreboardView() {
	app.run(presenter, interpreter);
	verify(presenter).register(app);
    }

    @Test
    void appDelegatesInputLinesToInterpreter() {
	when(console.readLine()).thenReturn("line1", "line2", "quit");
	when(interpreter.parse(anyString())).thenReturn(Action.UNKNOWN, Action.UNKNOWN, Action.QUIT);

	app.run(presenter, interpreter);

	final InOrder parsingOrder = inOrder(interpreter);
	parsingOrder.verify(interpreter).parse("line1");
	parsingOrder.verify(interpreter).parse("line2");
	parsingOrder.verify(interpreter).parse("quit");
    }

    @Nested
    class Actions {

	@Test
	void actionQuitStopsAppLoop() throws Exception {
	    when(console.readLine()).thenReturn("line1", "line2");
	    when(interpreter.parse("line1")).thenReturn(Action.QUIT);

	    app.run(presenter, interpreter);
	    verify(interpreter).parse("line1");
	    verify(interpreter, never()).parse("line2");
	}

	@Test
	void actionQuitPrintsGoodbyeMessage() throws Exception {
	    when(console.readLine()).thenReturn("line1");
	    when(interpreter.parse("line1")).thenReturn(Action.QUIT);

	    app.run(presenter, interpreter);
	    verify(console).println("Thanx for using Deutsche Board");
	}

	@Test
	void unknownActionResultsInErrorMessage() {
	    when(console.readLine()).thenReturn("xxx", "quit");
	    when(interpreter.parse(anyString())).thenReturn(Action.UNKNOWN, Action.QUIT);

	    app.run(presenter, interpreter);
	    verify(console).println("Unknown command 'xxx'");
	}

	@Test
	void actionSelectACallsSelectTeamAOnPresenter() {
	    when(interpreter.parse(anyString())).thenReturn(Action.SELECT_A, Action.QUIT);
	    app.run(presenter, interpreter);
	    verify(presenter).select(Team.A);
	}

	@Test
	void actionSelectBCallsSelectTeamBOnPresenter() {
	    when(interpreter.parse(anyString())).thenReturn(Action.SELECT_B, Action.QUIT);
	    app.run(presenter, interpreter);
	    verify(presenter).select(Team.B);
	}

	@Test
	void actionScore1CallsScorePoints1OnPresenter() {
	    when(interpreter.parse(anyString())).thenReturn(Action.SCORE_1, Action.QUIT);
	    app.run(presenter, interpreter);
	    verify(presenter).score(Points.One);
	}

	@Test
	void actionScore2CallsScorePoints2OnPresenter() {
	    when(interpreter.parse(anyString())).thenReturn(Action.SCORE_2, Action.QUIT);
	    app.run(presenter, interpreter);
	    verify(presenter).score(Points.Two);
	}

	@Test
	void actionScore3CallsScorePoints3OnPresenter() {
	    when(interpreter.parse(anyString())).thenReturn(Action.SCORE_3, Action.QUIT);
	    app.run(presenter, interpreter);
	    verify(presenter).score(Points.Three);
	}

	@Test
	void actionHelpDisplaysListOfCommands() {
	    when(interpreter.parse(anyString())).thenReturn(Action.HELP, Action.QUIT);
	    final String firstHelp = "Select [A]";
	    final String secondHelp = "Select [B]";
	    when(interpreter.getCommandHelp()).thenReturn(Arrays.asList(new String[] { firstHelp, secondHelp }));
	    app.run(presenter, interpreter);
	    verify(console).println("Possible commands:");
	    verify(console).println(startsWith(firstHelp));
	    verify(console).println(startsWith(secondHelp));
	}
    }

    @Nested
    class ViewCallbacks {
	@Test
	void displayScoreIsPrintedOnConsole() throws Exception {
	    app.displayScore(Score.create(42, 107));
	    verify(console).println("042:107");
	}

	@Test
	void displaySelectedTeamAIsPrintedOnConsole() throws Exception {
	    app.displaySelectedTeam(Team.A);
	    verify(console).println("Team A selected");
	}

	@Test
	void displaySelectedTeamBIsPrintedOnConsole() throws Exception {
	    app.displaySelectedTeam(Team.B);
	    verify(console).println("Team B selected");
	}

	@Test
	void displaySelectedTeamNoneIsNotPrintedOnConsole() throws Exception {
	    app.displaySelectedTeam(Team.NONE);
	    verify(console, never()).println(anyString());
	}
    }

}
