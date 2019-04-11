package net.johanneslink.scoreboard.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreTests {

    @Test
    void negativeScoreForTeamAIsNotAllowed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Score.create(-1, 1);
        });
    }

    @Test
    void negativeScoreForTeamBIsNotAllowed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Score.create(1, -1);
        });
    }

    @Test
    void scoreIsFormattedWith3Digits() {
        assertEquals("001:009", Score.create(1, 9).toString());
        assertEquals("011:099", Score.create(11, 99).toString());
        assertEquals("111:999", Score.create(111, 999).toString());
    }

    @Test
    void incrementScoreOfTeamA() {
        final Score score = Score.create(12, 33);
        assertEquals(Score.create(13, 33), score.incTeamABy(Points.One));
        assertEquals(Score.create(14, 33), score.incTeamABy(Points.Two));
        assertEquals(Score.create(15, 33), score.incTeamABy(Points.Three));
    }

    @Test
    void incrementScoreOfTeamB() {
        final Score score = Score.create(44, 99);
        assertEquals(Score.create(44, 100), score.incTeamBBy(Points.One));
        assertEquals(Score.create(44, 101), score.incTeamBBy(Points.Two));
        assertEquals(Score.create(44, 102), score.incTeamBBy(Points.Three));
    }

    @Test
    void decrementScoreOfTeamB() {
        final Score score = Score.create(0, 1);
        assertEquals(Score.create(0, 0), score.decrement(Team.B));
    }

    @Test
    void decrementOfZeroScoreLeavesScoreAtZero() {
        final Score score = Score.create(0, 0);
        assertEquals(Score.create(0, 0), score.decrement(Team.A));
        assertEquals(Score.create(0, 0), score.decrement(Team.B));
    }
}
