package net.johanneslink.scoreboard.core;

public enum Points {
	Two(2), Three(3), One(1);

	private final int value;

	Points(int value) {
		this.value = value;
	}

	public int useToInc(int number) {
		return number + value;
	}
}
