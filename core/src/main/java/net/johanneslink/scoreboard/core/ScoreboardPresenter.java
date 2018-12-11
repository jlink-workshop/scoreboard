package net.johanneslink.scoreboard.core;

public class ScoreboardPresenter {

	private ScoreboardView view;
	private Score currentScore = Score.ab(0, 0);
	private Team currentSelectedTeam = Team.NONE;

	public void register(ScoreboardView view) {
		this.view = view;
		displayCurrentScore();
		displaySelectedTeam();
	}

	public void select(Team team) {
		currentSelectedTeam = team;
		displaySelectedTeam();
	}

	// TODO: Remove duplication with deScore
	// TODO: Boolean is not a type to use in Java (unless you have to)
	// TODO: varargs is not a way to allow SINGLE optional param
	public void score(Points points, ModifyScore action, Boolean... keepTeam) {
		// TODO Replace conditional with polymorphism
		Score newScore = currentScore;
		if (currentSelectedTeam == Team.A) {
			newScore = currentScore.modifyScoreTeamA(points, action);
		}
		if (currentSelectedTeam == Team.B) {
			newScore = currentScore.modifyScoreTeamB(points, action);
		}
		setScore(newScore);


		// TODO: keepTeam can never be null
		Boolean keep = keepTeam != null && keepTeam.length > 0 ? keepTeam[0] : false;

		if (!keep) {
			select(Team.NONE);
		}
	}

	public void setScore(Score newScore) {
		if (!currentScore.equals(newScore)) {
			currentScore = newScore;
			displayCurrentScore();
		} else {
			// TODO: Is that branch necessary? No test seems to be affected
			if (currentSelectedTeam != Team.NONE) {
				displayCurrentScore();
			}
		}
	}

	private void displayCurrentScore() {
		view.displayScore(currentScore);
	}

	private void displaySelectedTeam() {
		view.displaySelectedTeam(currentSelectedTeam);
	}

}
