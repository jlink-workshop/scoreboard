package net.johanneslink.scoreboard.core;

public class Score {

    public static Score create(final int teamA, final int teamB) {
        if (teamA < 0 || teamB < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        return new Score(teamA, teamB);
    }

    private final int teamA;
    private final int teamB;

    private Score(final int teamA, final int teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    @Override
    public int hashCode() {
        return teamA + teamB >> 8;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Score other = (Score) obj;
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

    private String formatScore(final int score) {
        return String.format("%03d", score);
    }

    Score incTeamABy(final Points points) {
        return create(points.useToInc(teamA), teamB);
    }

    Score incTeamBBy(final Points points) {
        return create(teamA, points.useToInc(teamB));
    }

    Score decrement(final Team team) {
        if (team == Team.A) {
            return Score.create(Math.max(0, teamA - 1), teamB);
        } else {
            return Score.create(teamA, Math.max(0, teamB - 1));
        }
    }
}
