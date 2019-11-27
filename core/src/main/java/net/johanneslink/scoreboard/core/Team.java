package net.johanneslink.scoreboard.core;

public enum Team {
	NONE,
	A {
		Score incrementScore(Points points, Score currentScore) {
			return currentScore.incTeamABy(points);
		}
		Score decrementScore(Points points, Score currentScore) {
			return currentScore.decTeamABy(points);
		}
	},
	B {
		Score incrementScore(Points points, Score currentScore) {
			return currentScore.incTeamBBy(points);
		}
		Score decrementScore(Points points, Score currentScore) {
			return currentScore.decTeamBBy(points);
		}
	};

	Score incrementScore(Points points, Score currentScore) {
		return currentScore;
	}

	Score decrementScore(Points points, Score currentScore) {
		return currentScore;
	}
}
