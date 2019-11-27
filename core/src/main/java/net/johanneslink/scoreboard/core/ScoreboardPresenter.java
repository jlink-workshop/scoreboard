package net.johanneslink.scoreboard.core;

public class ScoreboardPresenter {

	private ScoreboardView view;
	private Score currentScore = Score.ab(0, 0);
	private Score oldScore = Score.ab(0, 0);
	private Team currentSelectedTeam = Team.NONE;
	private ScoreSaver scoreSaver = ScoreSaver.NULL;

	public void register(ScoreboardView view) {
		this.view = view;
		displayCurrentScore();
		displaySelectedTeam();
	}

	public void register(ScoreSaver scoreSaver) {
		this.scoreSaver = scoreSaver;
		setScore(this.scoreSaver.load());

	}

	public void select(Team team) {
		currentSelectedTeam = team;
		displaySelectedTeam();
	}

	public void score(Points points) {
		setScore(currentSelectedTeam.incrementScore(points, this.currentScore));
		select(Team.NONE);
	}

	public void undoLastAction() {
		Score newScore = oldScore;
		oldScore = currentScore;
		setScore(newScore);
	}

	public void setScore(Score newScore) {
		if (!currentScore.equals(newScore)) {
			oldScore = currentScore;
			currentScore = newScore;
			scoreSaver.save(currentScore);
		}
		displayCurrentScore();
	}

	private void displayCurrentScore() {
		view.displayScore(currentScore);
	}

	private void displaySelectedTeam() {
		view.displaySelectedTeam(currentSelectedTeam);
	}

	public void minus() {
		setScore(currentSelectedTeam.decrementScore(Points.One, this.currentScore));
	}

}
