package net.johanneslink.scoreboard.core;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

	@Test
	void scoreIsFormattedWith3Digits() {
		assertEquals("001:009", Score.ab(1, 9).toString());
		assertEquals("011:099", Score.ab(11, 99).toString());
		assertEquals("111:999", Score.ab(111, 999).toString());
	}

	@Test
	void incrementScoreOfTeamA() {
		Score score = Score.ab(12, 33);
		assertEquals(Score.ab(13, 33), score.modifyScoreTeamA(Points.ONE, ModifyScore.INC));
		assertEquals(Score.ab(14, 33), score.modifyScoreTeamA(Points.TWO, ModifyScore.INC));
		assertEquals(Score.ab(15, 33), score.modifyScoreTeamA(Points.THREE, ModifyScore.INC));
	}

	@Test
	void incrementScoreOfTeamB() {
		Score score = Score.ab(44, 99);
		assertEquals(Score.ab(44, 100), score.modifyScoreTeamB(Points.ONE, ModifyScore.INC));
		assertEquals(Score.ab(44, 101), score.modifyScoreTeamB(Points.TWO, ModifyScore.INC));
		assertEquals(Score.ab(44, 102), score.modifyScoreTeamB(Points.THREE, ModifyScore.INC));
	}

	@Test
	void decrementScoreOfTeamA() {
		Score score = Score.ab(44, 99);
		assertEquals(Score.ab(43, 99), score.modifyScoreTeamA(Points.ONE, ModifyScore.DEC));
	}

	@Test
	void decrementScoreOfTeamB() {
		Score score = Score.ab(44, 99);
		assertEquals(Score.ab(44, 98), score.modifyScoreTeamB(Points.ONE, ModifyScore.DEC));
	}

	@Test
	void decrementScoreOfTeamA_whenScoreWas0_shouldStay0() {
		Score score = Score.ab(0, 99);
		assertEquals(Score.ab(0, 99), score.modifyScoreTeamA(Points.ONE, ModifyScore.DEC));
	}

	@Test
	void decrementScoreOfTeamB_whenScoreWas0_shouldStay0() {
		Score score = Score.ab(55, 0);
		assertEquals(Score.ab(55, 0), score.modifyScoreTeamB(Points.ONE, ModifyScore.DEC));
	}
}
