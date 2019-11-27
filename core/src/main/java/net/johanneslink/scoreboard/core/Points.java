package net.johanneslink.scoreboard.core;

public enum Points {
	Two(2), Three(3), One(1);

	private final int value;

	Points(int value) {
		this.value = value;
	}

	public int useToInc(int teamScore) {
		return teamScore + value;
	}

	public int useToDec(int teamScore) {
		if (teamScore < value) {
			return teamScore;
		} else {
			return teamScore - value;
		}
	}
}
