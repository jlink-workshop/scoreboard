package net.johanneslink.scoreboard.core;

import java.util.Optional;

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

	public void score(Points points, Boolean... keepTeam) {
		// TODO Replace conditional with polymorphism
		Score newScore = currentScore;
		if (currentSelectedTeam == Team.A) {
			newScore = currentScore.incTeamABy(points);
		}
		if (currentSelectedTeam == Team.B) {
			newScore = currentScore.incTeamBBy(points);
		}
		setScore(newScore);

		Boolean keep = keepTeam != null && keepTeam.length > 0 ? keepTeam[0] : false;

		if (!keep) {
			select(Team.NONE);
		}
	}

	public void deScore() {
		Score newScore = currentScore;
		if (currentSelectedTeam == Team.A) {
			newScore = currentScore.decrTeamABy(Points.ONE);
		}
		if (currentSelectedTeam == Team.B) {
			newScore = currentScore.decrTeamBBy(Points.ONE);
		}
		setScore(newScore);
	}

	public void setScore(Score newScore) {
		if (!currentScore.equals(newScore)) {
			currentScore = newScore;
			displayCurrentScore();
		} else {
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
