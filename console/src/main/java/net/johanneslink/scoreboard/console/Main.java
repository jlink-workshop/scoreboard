package net.johanneslink.scoreboard.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

import net.johanneslink.scoreboard.core.ScoreboardPresenter;

public class Main {

    public static void main(final String args[]) {
	Locale locale = Locale.ENGLISH;
	if (args.length > 0 && "deutsch".equalsIgnoreCase(args[0])) {
	    locale = Locale.GERMAN;
	}
	final ResourceBundle bundle = ResourceBundle.getBundle("ScoreBoardMessages", locale);
	final TranslationService translationService = new TranslationService(bundle);
	final Console console = new SystemConsole();
	final ScoreboardConsoleApp app = new ScoreboardConsoleApp(console);
	final CommandInterpreter interpreter = new DefaultCommandInterpreter(translationService);
	app.run(new ScoreboardPresenter(), interpreter);
    }

    private static class SystemConsole implements Console {

	private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public void println(final String line) {
	    System.out.println(line);
	}

	@Override
	public String readLine() {
	    try {
		return reader.readLine();
	    } catch (final IOException shouldNeverHappen) {
		throw new RuntimeException(shouldNeverHappen);
	    }
	}

    }
}
