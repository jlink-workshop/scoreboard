package net.johanneslink.scoreboard.core;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTests {

    @Test
    void negativeScoreForTeamAIsNotAllowed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Score.ab(-1, 1);
        });
    }

    @Test
    void negativeScoreForTeamBIsNotAllowed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Score.ab(1, -1);
        });
    }

    @Test
    void scoreIsFormattedWith3Digits() {
        assertEquals("001:009", Score.ab(1, 9).toString());
        assertEquals("011:099", Score.ab(11, 99).toString());
        assertEquals("111:999", Score.ab(111, 999).toString());
    }

    @Test
    void incrementScoreOfTeamA() {
        Score score = Score.ab(12, 33);
        assertEquals(Score.ab(13, 33), score.incTeamABy(Points.One));
        assertEquals(Score.ab(14, 33), score.incTeamABy(Points.Two));
        assertEquals(Score.ab(15, 33), score.incTeamABy(Points.Three));
    }

    @Test
    void incrementScoreOfTeamB() {
        Score score = Score.ab(44, 99);
        assertEquals(Score.ab(44, 100), score.incTeamBBy(Points.One));
        assertEquals(Score.ab(44, 101), score.incTeamBBy(Points.Two));
        assertEquals(Score.ab(44, 102), score.incTeamBBy(Points.Three));
    }
}
