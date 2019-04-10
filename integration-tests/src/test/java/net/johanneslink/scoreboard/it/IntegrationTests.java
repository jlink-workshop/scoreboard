package net.johanneslink.scoreboard.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.johanneslink.scoreboard.console.AsciiArtFormatter;
import net.johanneslink.scoreboard.console.Main;

class IntegrationTests {

    private ByteArrayOutputStream stdout;
    private PrintWriter sysInWriter;
    private PrintStream originalStdout;
    private InputStream originalStdin;

    @BeforeEach
    void init() throws IOException {
        stdout = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(stdout, false, StandardCharsets.UTF_8.toString());
        originalStdout = System.out;
        originalStdin = System.in;
        System.setOut(ps);

        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream sysInStream = new PipedOutputStream(in);
        sysInWriter = new PrintWriter(sysInStream);
        System.setIn(in);
    }

    @AfterEach
    void reset() {
        System.setOut(originalStdout);
        System.setIn(originalStdin);
    }

    @Test
    void startingConsoleAppDisplaysInitialScore() throws Exception {
        startConsoleApp();
        sysInWriter.append("q\n");
        final List<String> stdoutLines = stdoutLines();
        assertEquals(stdoutLines.size(), 1);
        assertEquals(AsciiArtFormatter.format("000:000"), stdoutLines.get(0));
    }

    // @Test
    void consoleAppInterpretsInputLineByLineAndCanBeQuit() {
        // Todo: Redirect stdin to accept input from tests
        fail("Not implemented yet");
    }

    private void startConsoleApp(final String... args) throws InterruptedException {
        final Thread mainThread = new Thread(() -> Main.main(args));
        mainThread.start();
        mainThread.join(2000);
    }

    private List<String> stdoutLines() throws IOException {
        stdout.flush();
        final String[] lines = stdout.toString(StandardCharsets.UTF_8.toString())
                .split(System.getProperty("line.separator"));
        return Arrays.asList(lines);
    }
}
