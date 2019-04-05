package swingUi;

import javax.swing.*;

import org.junit.jupiter.api.*;
import org.netbeans.jemmy.operators.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleGUITest {

	private int tsMinutes = 0;
	private int tsSeconds = 0;

	class GameTimeServiceStub implements GameTimeService {
		@Override
		public int getRemainingMinutes() {
			return tsMinutes;
		}

		@Override
		public int getRemainingSeconds() {
			return tsSeconds;
		}
	}

	private JFrameOperator mainFrame;
	private ScoreboardPresenter scoreboard;

	@BeforeEach
	void init() {
		GameTimeService timeService = new GameTimeServiceStub();
		scoreboard = new ScoreboardPresenter(timeService);
		new ScoreboardSwingApp(scoreboard).setVisible(true);
		mainFrame = new JFrameOperator("Basketball Console");
	}

	@AfterEach
	void release() {
		mainFrame.close();
	}

	private void slowDown() {
		sleep(100);
	}

	@Test
	void remainingGameTimeIsDisplayedWhenTriggered() throws Exception {
		tsMinutes = 8;
		tsSeconds = 21;
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				scoreboard.triggerTimeCheck();
			}
		});
		String remainingTimeString = new JLabelOperator(mainFrame, 0).getText();
		assertEquals("8:21", remainingTimeString);
	}

	@Test
	void teamAScores1() throws Exception {
		click(getTeamAButton());
		click(getScore1Button());
		assertScore(1, 0);
	}

	@Test
	void teamBScores3() throws Exception {
		click(getTeamBButton());
		click(getScore3Button());
		assertScore(0, 3);
	}

	@Test
	void bothTeamsScoreTwice() throws Exception {
		click(getTeamBButton());
		click(getScore3Button());
		click(getTeamAButton());
		click(getScore2Button());
		click(getTeamAButton());
		click(getScore1Button());
		click(getTeamBButton());
		click(getScore3Button());
		assertScore(3, 6);
	}

	@Test
	void pressingScoreButtonsWithoutTeamButtonsDoesNotChangeScore() throws Exception {
		click(getTeamAButton());
		click(getScore1Button());
		click(getScore1Button());
		click(getScore2Button());
		click(getScore3Button());
		assertScore(1, 0);
	}

	@Test
	void lastPressedTeamButtonCounts() throws Exception {
		click(getTeamAButton());
		click(getTeamBButton());
		click(getScore1Button());
		assertScore(0, 1);
	}

	private void assertScore(int scoreA, int scoreB) {
		assertEquals(getTeamAScore(), Integer.toString(scoreA), "score team A");
		assertEquals(getTeamBScore(), Integer.toString(scoreB), "score team B");
	}

	private void click(JButtonOperator button) {
		button.clickMouse();
		slowDown();
	}

	private String getTeamAScore() {
		return new JLabelOperator(mainFrame, 1).getText();
	}

	private String getTeamBScore() {
		return new JLabelOperator(mainFrame, 3).getText();
	}

	private JButtonOperator getScore1Button() {
		return new JButtonOperator(mainFrame, "1");
	}

	private JButtonOperator getScore2Button() {
		return new JButtonOperator(mainFrame, "2");
	}

	private JButtonOperator getScore3Button() {
		return new JButtonOperator(mainFrame, "3");
	}

	private JButtonOperator getTeamAButton() {
		return new JButtonOperator(mainFrame, "Team A");
	}

	private JButtonOperator getTeamBButton() {
		return new JButtonOperator(mainFrame, "Team B");
	}

	private void sleep(long msecs) {
		try {
			Thread.sleep(msecs);
		} catch (InterruptedException ignore) {
		}
	}

}
