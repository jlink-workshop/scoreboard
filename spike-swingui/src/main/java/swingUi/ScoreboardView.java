package swingUi;

public interface ScoreboardView {

	ScoreboardView NULL = new ScoreboardView() {
		@Override
		public void displayScore(Score score) {
		}

		@Override
		public void displayGameTime(String time) {
		}
	};

	void displayScore(Score score);

	void displayGameTime(String time);

}
