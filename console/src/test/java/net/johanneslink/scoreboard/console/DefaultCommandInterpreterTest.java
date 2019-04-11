package net.johanneslink.scoreboard.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DefaultCommandInterpreterTest {

    private static final String HELP_TEXT_SELECT_A = "HELP_TEXT_SELECT_A";

    private CommandInterpreter interpreter;

    @Mock
    private TranslationService translationServiceMock;

    @BeforeEach
    void setup() {
	MockitoAnnotations.initMocks(this);
	when(translationServiceMock.translate("help_select_a")).thenReturn(HELP_TEXT_SELECT_A);
	interpreter = new DefaultCommandInterpreter(translationServiceMock);
    }

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
    void interpretIncrement() {
	assertActionsInterpreted(Action.INCREMENT, "increment", "+");
    }

    @Test
    void interpretDecrement() {
	assertActionsInterpreted(Action.DECREMENT, "decrement", "-");
    }

    @Test
    void interpretUnknownCommands() {
	assertActionsInterpreted(Action.UNKNOWN, "c", "xxx");
    }

    @Test
    void commandHelpContainsCommandHelpText() {
	final List<String> commandHelp = interpreter.getCommandHelp();
	assertTrue(commandHelp.contains(HELP_TEXT_SELECT_A), HELP_TEXT_SELECT_A + " not found in " + commandHelp);
    }
}
