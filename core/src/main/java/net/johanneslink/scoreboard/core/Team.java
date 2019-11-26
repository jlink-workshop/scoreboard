package net.johanneslink.scoreboard.core;

public enum Team {
	NONE,
	A {
		Score getScore(Points points, Score currentScore) {
			return currentScore.incTeamABy(points);
		}
	},
	B {
		Score getScore(Points points, Score currentScore) {
			return currentScore.incTeamBBy(points);
		}
	};

	Score getScore(Points points, Score currentScore) {
		return currentScore;
	}
}
