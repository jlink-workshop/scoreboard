package net.johanneslink.scoreboard.console;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DefaultCommandInterpreterTest {

    private final CommandInterpreter interpreter = new DefaultCommandInterpreter();

    @Test
    void interpretQuit() {
	assertEquals(Action.QUIT, interpreter.parse("quit"));
	assertEquals(Action.QUIT, interpreter.parse("QUIT"));
	assertEquals(Action.QUIT, interpreter.parse("q"));
	assertEquals(Action.QUIT, interpreter.parse("Q"));
    }

    @Test
    void interpretTeamASelection() {
	assertEquals(Action.SELECT_A, interpreter.parse("a"));
	assertEquals(Action.SELECT_A, interpreter.parse("select a"));
    }

    @Test
    void interpretTeamBSelection() {
	assertEquals(Action.SELECT_B, interpreter.parse("b"));
	assertEquals(Action.SELECT_B, interpreter.parse("select b"));
    }

    @Test
    void interpretScore1() {
	assertEquals(Action.SCORE_1, interpreter.parse("1"));
	assertEquals(Action.SCORE_1, interpreter.parse("score 1"));
    }

    @Test
    void interpretScore2() {
	assertEquals(Action.SCORE_2, interpreter.parse("2"));
	assertEquals(Action.SCORE_2, interpreter.parse("score 2"));
    }

    @Test
    void interpretScore3() {
	assertEquals(Action.SCORE_3, interpreter.parse("3"));
	assertEquals(Action.SCORE_3, interpreter.parse("score 3"));
    }

    @Test
    void interpretHelp() {
	assertEquals(Action.HELP, interpreter.parse("?"));
	assertEquals(Action.HELP, interpreter.parse("h"));
	assertEquals(Action.HELP, interpreter.parse("help"));
    }

    @Test
    void interpretUnknownCommands() {
	assertEquals(Action.UNKNOWN, interpreter.parse("c"));
	assertEquals(Action.UNKNOWN, interpreter.parse("xxx"));
    }
}
