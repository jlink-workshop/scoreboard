package net.johanneslink.scoreboard.core;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardPresenterTest implements ScoreboardView {

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

	@Test
	void initialScoreIs0to0() {
		assertEquals(Score.ab(0, 0), lastDisplayedScore);
	}

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

	@Test
	void scoreInitialPointForTeamA() {
		presenter.select(Team.A);
		presenter.score(Points.ONE);
		assertEquals(Score.ab(1, 0), lastDisplayedScore);
	}

	@Test
	void afterScoringNoTeamIsSelected() {
		presenter.select(Team.A);
		presenter.score(Points.ONE);
		assertEquals(Team.NONE, lastSelectedTeam);
	}

	// TODO: Add test for team selection after inc / dec

	@Test
	void score2PointsForTeamA() {
		presenter.select(Team.A);
		presenter.score(Points.ONE);
		presenter.select(Team.A);
		presenter.score(Points.TWO);
		assertEquals(Score.ab(3, 0), lastDisplayedScore);
	}

	@Test
	void score3PointsForTeamA() {
		presenter.select(Team.A);
		presenter.score(Points.TWO);
		presenter.select(Team.A);
		presenter.score(Points.THREE);
		assertEquals(Score.ab(5, 0), lastDisplayedScore);
	}

	@Test
	void deScorePointsForTeamA() {
		presenter.select(Team.A);
		presenter.score(Points.TWO);
		presenter.select(Team.A);
		presenter.deScore();
		assertEquals(Score.ab(1, 0), lastDisplayedScore);
		presenter.deScore();
		assertEquals(Score.ab(0, 0), lastDisplayedScore);
	}

	@Test
	void scoreInitialPointForTeamB() {
		presenter.select(Team.B);
		presenter.score(Points.THREE);
		assertEquals(Score.ab(0, 3), lastDisplayedScore);
	}

	@Test
	void scorePointsForTeamBLater() {
		presenter.setScore(Score.ab(22, 33));
		presenter.select(Team.B);
		presenter.score(Points.ONE);
		assertEquals(Score.ab(22, 34), lastDisplayedScore);
		presenter.select(Team.B);
		presenter.score(Points.TWO);
		assertEquals(Score.ab(22, 36), lastDisplayedScore);
	}

	@Test
	void deScorePointsForTeamB() {
		presenter.select(Team.B);
		presenter.score(Points.TWO);
		presenter.select(Team.B);
		presenter.deScore();
		assertEquals(Score.ab(0, 1), lastDisplayedScore);
		presenter.deScore();
		assertEquals(Score.ab(0, 0), lastDisplayedScore);
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
		presenter.score(Points.ONE);
		presenter.score(Points.TWO);
		presenter.score(Points.THREE);
		assertNull(lastDisplayedScore);
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
