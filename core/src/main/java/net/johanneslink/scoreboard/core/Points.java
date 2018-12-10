package net.johanneslink.scoreboard.core;

public enum Points {
	ONE(1), TWO(2), THREE(3);

	private final int value;

	Points(int value) {
		this.value = value;
	}

	public int useToInc(int number) {
		return number + value;
	}
}
