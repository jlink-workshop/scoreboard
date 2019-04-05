package net.johanneslink.scoreboard.console;

import java.io.*;

import net.johanneslink.scoreboard.core.ScoreboardPresenter;

public class Main {

	public static void main(String args[]) {
		Console console = new SystemConsole();
		ScoreboardConsoleApp app = new ScoreboardConsoleApp(console);
		CommandInterpreter interpreter = new DefaultCommandInterpreter();
		app.run(new ScoreboardPresenter(), interpreter);
	}

	private static class SystemConsole implements Console {

		private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		@Override
		public void println(String line) {
			System.out.println(line);
		}

		@Override
		public String readLine() {
			try {
				return reader.readLine();
			} catch (IOException shouldNeverHappen) {
				throw new RuntimeException(shouldNeverHappen);
			}
		}

	}
}
