package net.johanneslink.scoreboard.console;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class DefaultCommandInterpreterTest {

    private final CommandInterpreter interpreter = new DefaultCommandInterpreter();

    @Test
    void interpretQuit() {
	assertActionsInterpreted(Action.QUIT, "quit", "Quit", "q", "Q");
    }

    private void assertActionsInterpreted(final Action action, final String... inputs) {
	for (final String input : inputs) {
	    assertEquals(action, interpreter.parse(input));
	}
    }

    @Test
    void interpretTeamASelection() {
	assertActionsInterpreted(Action.SELECT_A, "a", "select a");
    }

    @Test
    void interpretTeamBSelection() {
	assertActionsInterpreted(Action.SELECT_B, "b", "select b");
    }

    @Test
    void interpretScore1() {
	assertActionsInterpreted(Action.SCORE_1, "1", "score 1");
    }

    @Test
    void interpretScore2() {
	assertActionsInterpreted(Action.SCORE_2, "2", "score 2");
    }

    @Test
    void interpretScore3() {
	assertActionsInterpreted(Action.SCORE_3, "3", "score 3");
    }

    @Test
    void interpretHelp() {
	assertActionsInterpreted(Action.HELP, "?", "h", "help");
    }

    @Test
    void interpretUnknownCommands() {
	assertActionsInterpreted(Action.UNKNOWN, "c", "xxx");
    }

    @Test
    void commandHelp() {
	final List<String> commandHelp = interpreter.getCommandHelp();
	assertEquals(commandHelp.get(0), "Select [A] - Select team A for scoring");
    }
}
