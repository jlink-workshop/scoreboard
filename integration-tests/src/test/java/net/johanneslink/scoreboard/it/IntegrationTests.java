package net.johanneslink.scoreboard.it;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
		System.setIn(simulateStdin(Arrays.asList("a", "1", "b", "3", "q")));
		startConsoleApp();
		List<String> stdoutLines = stdoutLines();
		assertEquals(5, stdoutLines.size());
		assertEquals("001:003", stdoutLines.get(4));
	}

	private ByteArrayInputStream simulateStdin(List<String> inputSequence) {
		AtomicReference<String> input = new AtomicReference<>("");
		inputSequence.forEach(command -> input.set(input + command + "%n"));
		return new ByteArrayInputStream((String.format(input.get()).getBytes()));
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
