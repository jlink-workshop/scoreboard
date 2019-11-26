package net.johanneslink.scoreboard.it;

import java.io.*;
import java.util.*;

import net.johanneslink.scoreboard.console.Main;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTests {

	private ByteArrayOutputStream stdout;
	private PrintStream originalStdout;
	private InputStream originalStdin;

	@BeforeEach
	void init() {
		stdout = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(stdout);
		originalStdout = System.out;
		originalStdin = System.in;
		System.setOut(ps);
	}

	@AfterEach
	void reset() {
		System.setOut(originalStdout);
		System.setIn(originalStdin);
	}

	@Test
	void startingConsoleAppDisplaysInitialScore() throws Exception {
		startConsoleApp();
		List<String> stdoutLines = stdoutLines();
		assertEquals(stdoutLines.size(), 1);
		assertEquals(stdoutLines.get(0), "000:000");
	}

	@Test
	void consoleAppInterpretsInputLineByLineAndCanBeQuit() throws Exception {
		InputStream stdin = new ByteArrayInputStream((String.format("a%n1%nb%n3%nq").getBytes()));
		System.setIn(stdin);
		startConsoleApp();
		List<String> stdoutLines = stdoutLines();
		assertEquals(5, stdoutLines.size());
		assertEquals(stdoutLines.get(4), "001:003");
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
