package swingUi;

public class ScoreboardPresenter {
	private Score score = Score.ab(0, 0);
	private Team selectedTeam = Team.NONE;
	private ScoreboardView view = ScoreboardView.NULL;
	private final GameTimeService timeService;

	public ScoreboardPresenter(GameTimeService timeService) {
		this.timeService = timeService;
	}

	public Score getScore() {
		return score;
	}

	public void teamAClicked() {
		selectedTeam = Team.A;
	}

	public void teamBClicked() {
		selectedTeam = Team.B;
	}

	public void score1Clicked() {
		score(1);
	}

	public void score2Clicked() {
		score(2);
	}

	public void score3Clicked() {
		score(3);
	}

	private void score(int points) {
		if (selectedTeam == Team.A) {
			score = score.incABy(points);
		}
		if (selectedTeam == Team.B) {
			score = score.incBBy(points);
		}
		selectedTeam = Team.NONE;
		notifyScoreChanged();
	}

	public void registerViewer(ScoreboardView view) {
		this.view = view;
		notifyScoreChanged();
	}

	private void notifyScoreChanged() {
		view.displayScore(score);
	}

	public void triggerTimeCheck() {
		int minutes = timeService.getRemainingMinutes();
		int seconds = timeService.getRemainingSeconds();
		String timeString = minutes + ":" + seconds;
		view.displayGameTime(timeString);
	}
}
