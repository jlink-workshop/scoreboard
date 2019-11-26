package net.johanneslink.scoreboard.core;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardPresenterTests implements ScoreboardView {

	private ScoreboardPresenter presenter;
	private Team lastSelectedTeam;
	private Score lastDisplayedScore;

	@BeforeEach
	void initialize() {
		presenter = new ScoreboardPresenter();
		lastSelectedTeam = null;
		lastDisplayedScore = null;
		presenter.register(this);
	}

	@Nested
	class Scoring {

		@Test
		void initialScoreIs0to0() {
			assertEquals(Score.ab(0, 0), lastDisplayedScore);
		}

		@Test
		void scoreInitialPointForTeamA() {
			presenter.select(Team.A);
			presenter.score(Points.One);
			assertEquals(Score.ab(1, 0), lastDisplayedScore);
		}

		@Test
		void afterScoringNoTeamIsSelected() {
			presenter.select(Team.A);
			presenter.score(Points.One);
			assertEquals(Team.NONE, lastSelectedTeam);
		}

		@Test
		void score2PointsForTeamA() {
			presenter.select(Team.A);
			presenter.score(Points.One);
			presenter.select(Team.A);
			presenter.score(Points.Two);
			assertEquals(Score.ab(3, 0), lastDisplayedScore);
		}

		@Test
		void score3PointsForTeamA() {
			presenter.select(Team.A);
			presenter.score(Points.Two);
			presenter.select(Team.A);
			presenter.score(Points.Three);
			assertEquals(Score.ab(5, 0), lastDisplayedScore);
		}

		@Test
		void undoAction() {
			presenter.select(Team.A);
			presenter.score(Points.Two);
			presenter.select(Team.A);
			presenter.score(Points.Three);
			presenter.undoLastAction();
			assertEquals(Score.ab(2, 0), lastDisplayedScore);
		}

		@Test
		void undoActionTwice() {
			presenter.select(Team.A);
			presenter.score(Points.Two);
			presenter.select(Team.A);
			presenter.score(Points.Three);
			presenter.undoLastAction();
			presenter.undoLastAction();
			assertEquals(Score.ab(5, 0), lastDisplayedScore);
		}

		@Test
		void undoActionAfterSeveralScores() {
			presenter.select(Team.A);
			presenter.score(Points.Two);
			presenter.select(Team.A);
			presenter.score(Points.Two);
			presenter.select(Team.A);
			presenter.select(Team.NONE);
			presenter.score(Points.One);
			presenter.select(Team.B);
			presenter.undoLastAction();
			assertEquals(Score.ab(2, 0), lastDisplayedScore);
			presenter.undoLastAction();
			assertEquals(Score.ab(4, 0), lastDisplayedScore);
		}

		@Test
		void scoreInitialPointForTeamB() {
			presenter.select(Team.B);
			presenter.score(Points.Three);
			assertEquals(Score.ab(0, 3), lastDisplayedScore);
		}

		@Test
		void scorePointsForTeamBLater() {
			presenter.setScore(Score.ab(22, 33));
			presenter.select(Team.B);
			presenter.score(Points.One);
			assertEquals(Score.ab(22, 34), lastDisplayedScore);
			presenter.select(Team.B);
			presenter.score(Points.Two);
			assertEquals(Score.ab(22, 36), lastDisplayedScore);
		}

		@Test
		void settingScoreWillTriggerDisplayScore() {
			presenter.setScore(Score.ab(22, 33));
			assertEquals(Score.ab(22, 33), lastDisplayedScore);
		}

		@Test
		void ignoreScoringAttemptWhenNoTeamIsSelected() {
			presenter.setScore(Score.ab(22, 33));
			lastDisplayedScore = null;
			presenter.score(Points.One);
			presenter.score(Points.Two);
			presenter.score(Points.Three);
			assertNull(lastDisplayedScore);
		}
	}

	@Nested class TeamSelection {

		@Test
		void initiallyNoTeamIsSelected() throws Exception {
			assertEquals(Team.NONE, lastSelectedTeam);
		}

		@Test
		void selectingTeamADisplaysSelection() throws Exception {
			presenter.select(Team.A);
			assertEquals(Team.A, lastSelectedTeam);
		}

		@Test
		void selectingTeamBDisplaysSelection() throws Exception {
			presenter.select(Team.B);
			assertEquals(Team.B, lastSelectedTeam);
		}

	}

	@Override
	public void displaySelectedTeam(Team team) {
		lastSelectedTeam = team;
	}

	@Override
	public void displayScore(Score score) {
		lastDisplayedScore = score;
	}
}
