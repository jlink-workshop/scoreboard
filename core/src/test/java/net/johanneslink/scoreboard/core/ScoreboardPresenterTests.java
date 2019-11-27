package net.johanneslink.scoreboard.core;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreboardPresenterTests implements ScoreboardView {

	private ScoreboardPresenter presenter;
	private Team lastSelectedTeam;
	private Score lastDisplayedScore;
	private ScoreSaver scoreSaver;

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
		void saveOnScoreChange() {
			scoreSaver = Mockito.mock(ScoreSaver.class);
			when(scoreSaver.load()).thenReturn(Score.ab(0, 0));
			presenter.register(scoreSaver);
			Score ab = Score.ab(3, 0);
			presenter.setScore(ab);
			verify(scoreSaver).save(ab);
		}

		@Test
		void loadOnScoreSaverRegistration() {
			scoreSaver = Mockito.mock(ScoreSaver.class);
			Score toBeLoaded = Score.ab(3, 4);
			when(scoreSaver.load()).thenReturn(toBeLoaded);
			presenter.register(scoreSaver);
			assertEquals(toBeLoaded, lastDisplayedScore);
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
		void minusForTeamA() {
			presenter.setScore(Score.ab(2, 4));
			presenter.select(Team.A);
			presenter.minus();
			assertEquals(Score.ab(1, 4), lastDisplayedScore);
		}

		@Test
		void minusForTeamB() {
			presenter.setScore(Score.ab(2, 4));
			presenter.select(Team.B);
			presenter.minus();
			assertEquals(Score.ab(2, 3), lastDisplayedScore);
		}

		@Test
		void minusCanNotBecomeNegative() {
			presenter.setScore(Score.ab(0, 0));
			presenter.select(Team.A);
			presenter.minus();
			assertEquals(Score.ab(0, 0), lastDisplayedScore);
		}

		@Test
		void minusTwiceWithoutTeamReselection() {
			presenter.setScore(Score.ab(5, 3));
			presenter.select(Team.A);
			presenter.minus();
			presenter.minus();
			assertEquals(Score.ab(3, 3), lastDisplayedScore);
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
		void undoActionInitially() {
			lastDisplayedScore = null;
			presenter.undoLastAction();
			assertEquals(Score.ab(0, 0), lastDisplayedScore);
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
			assertEquals(Score.ab(22, 33), lastDisplayedScore);
		}
	}

	@Nested class TeamSelection {

		@Test
		void initiallyNoTeamIsSelected() {
			assertEquals(Team.NONE, lastSelectedTeam);
		}

		@Test
		void selectingTeamADisplaysSelection() {
			presenter.select(Team.A);
			assertEquals(Team.A, lastSelectedTeam);
		}

		@Test
		void selectingTeamBDisplaysSelection() {
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
