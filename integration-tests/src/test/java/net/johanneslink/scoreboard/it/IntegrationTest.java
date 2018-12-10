package net.johanneslink.scoreboard.it;

import java.io.*;
import java.util.*;

import net.johanneslink.scoreboard.console.Main;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

	private ByteArrayOutputStream stdout;
	private PrintStream originalStdout;

	@BeforeEach
	void init() {
		stdout = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(stdout);
		originalStdout = System.out;
		System.setOut(ps);
	}

	@AfterEach
	void reset() {
		System.setOut(originalStdout);
	}

	@Test
	void startingConsoleAppDisplaysInitialScore() throws Exception {
		startConsoleApp();
		List<String> stdoutLines = stdoutLines();
		assertEquals(stdoutLines.size(), 1);
		assertEquals(stdoutLines.get(0), "000:000");
	}

	@Test
	void consoleAppInterpretsInputLineByLineAndCanBeQuit() {
		// Todo: Redirect stdin to accept input from tests
		fail("Not implemented yet");
	}

	private void startConsoleApp(final String... args) throws InterruptedException {
		Thread mainThread = new Thread(() -> Main.main(args));
		mainThread.start();
		mainThread.join(2000);
	}

	private List<String> stdoutLines() throws IOException {
		stdout.flush();
		String[] lines = stdout.toString().trim().split(System.getProperty("line.separator"));
		return Arrays.asList(lines);
	}
}
