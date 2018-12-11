package net.johanneslink.scoreboard.core;

public class Score {

	private int teamA;
	private int teamB;

	private Score(int teamA, int teamB) {
		this.teamA = teamA;
		this.teamB = teamB;
	}

	public static Score ab(int teamA, int teamB) {

		if (teamA < 0) {
			return new Score(0, teamB);
		}

		if (teamB < 0) {
			return new Score(teamA, 0);
		}
		return new Score(teamA, teamB);
	}

	@Override
	public int hashCode() {
		return teamA + teamB >> 8;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		Score other = (Score) obj;
		return teamA == other.teamA && teamB == other.teamB;
	}

	@Override
	public String toString() {
		return a() + ":" + b();
	}

	private String a() {
		return formatScore(teamA);
	}

	private String b() {
		return formatScore(teamB);
	}

	private String formatScore(int score) {
		return String.format("%03d", score);
	}

	// TODO: Replace inc/decrTeamXBy with changeTeamXBy

	Score incTeamABy(Points points) {
		return ab(points.useToInc(teamA), teamB);
	}

	Score incTeamBBy(Points points) {
		return ab(teamA, points.useToInc(teamB));
	}

	Score decrTeamABy(Points points) {
		return ab(points.useToDecr(teamA), teamB);
	}

	Score decrTeamBBy(Points points) {
		return ab(teamA, points.useToDecr(teamB));
	}

}
