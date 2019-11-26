package net.johanneslink.scoreboard.core;

public class ScoreboardPresenter {

	private ScoreboardView view;
	private Score currentScore = Score.ab(0, 0);
	Team currentSelectedTeam = Team.NONE;

	public void register(ScoreboardView view) {
		this.view = view;
		displayCurrentScore();
		displaySelectedTeam();
	}

	public void select(Team team) {
		currentSelectedTeam = team;
		displaySelectedTeam();
	}

	public void score(Points points, Team currentSelectedTeam) {
		// TODO Replace conditional with polymorphism
		setScore(currentSelectedTeam.getScore(points, currentScore));
		select(Team.NONE);
	}



	public void setScore(Score newScore) {
		if (!currentScore.equals(newScore)) {
			currentScore = newScore;
			displayCurrentScore();
		}
	}

	private void displayCurrentScore() {
		view.displayScore(currentScore);
	}

	private void displaySelectedTeam() {
		view.displaySelectedTeam(currentSelectedTeam);
	}

}
