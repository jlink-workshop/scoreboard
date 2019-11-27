package net.johanneslink.scoreboard.console;

import net.johanneslink.scoreboard.core.*;
import org.junit.jupiter.api.*;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

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

		InOrder parsingOrder = inOrder(interpreter);
		parsingOrder.verify(interpreter).parse("line1");
		parsingOrder.verify(interpreter).parse("line2");
		parsingOrder.verify(interpreter).parse("quit");
	}

	@Nested
	class Actions {

		@Test
		void actionQuitStopsAppLoop() {
			when(console.readLine()).thenReturn("line1", "line2");
			when(interpreter.parse("line1")).thenReturn(Action.QUIT);

			app.run(presenter, interpreter);
			verify(interpreter).parse("line1");
			verify(interpreter, never()).parse("line2");
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
		void actionMinusCallsMinusOnPresenter() {
			when(interpreter.parse(anyString())).thenReturn(Action.MINUS, Action.QUIT);
			app.run(presenter, interpreter);
			verify(presenter).minus();
		}

		@Test
		void actionUndoCallsUndoLastActionPresenter() {
			when(interpreter.parse(anyString())).thenReturn(Action.SCORE_3, Action.UNDO, Action.QUIT);
			app.run(presenter, interpreter);
			verify(presenter).undoLastAction();
		}

		@Test
		void actionHelpDisplaysListOfCommands() {
			when(interpreter.parse(anyString())).thenReturn(Action.HELP, Action.QUIT);
			app.run(presenter, interpreter);
			verify(console).println("Possible commands:");
			verify(console).println(startsWith("Select [A] -"));
			verify(console).println(startsWith("Select [B] -"));
			verify(console).println(startsWith("Score [1] -"));
			verify(console).println(startsWith("Score [2] -"));
			verify(console).println(startsWith("Score [3] -"));
			verify(console).println(startsWith("Minus [-] -"));
			verify(console).println(startsWith("[U]ndo -"));
			verify(console).println(startsWith("[Q]uit -"));
			verify(console).println(startsWith("[?|H]elp -"));
		}
	}

	@Nested
	class ViewCallbacks {
		@Test
		void displayScoreIsPrintedOnConsole() {
			app.displayScore(Score.ab(42, 107));
			verify(console).println("042:107");
		}

		@Test
		void displaySelectedTeamAIsPrintedOnConsole() {
			app.displaySelectedTeam(Team.A);
			verify(console).println("Team A selected");
		}

		@Test
		void displaySelectedTeamBIsPrintedOnConsole() {
			app.displaySelectedTeam(Team.B);
			verify(console).println("Team B selected");
		}

		@Test
		void displaySelectedTeamNoneIsNotPrintedOnConsole() {
			app.displaySelectedTeam(Team.NONE);
			verify(console, never()).println(anyString());
		}
	}


}
