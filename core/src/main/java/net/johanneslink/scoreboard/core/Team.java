package net.johanneslink.scoreboard.core;

public enum Team {
    NONE {
        @Override
        public Score calculateScore(final Points points, final Score score) {
            return score;
        }
    },
    A {
        @Override
        public Score calculateScore(final Points points, final Score score) {
            return score.incTeamABy(points);
        }
    },
    B {
        @Override
        public Score calculateScore(final Points points, final Score score) {
            return score.incTeamBBy(points);
        }
    };

    public abstract Score calculateScore(final Points points, final Score score);
}
