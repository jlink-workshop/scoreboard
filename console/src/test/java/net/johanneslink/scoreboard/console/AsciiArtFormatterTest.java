package net.johanneslink.scoreboard.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AsciiArtFormatterTest {

    @Test
    void testFormatText() {
	final String asciiArt = AsciiArtFormatter.format("123:456");
	assertTrue(asciiArt.contains("████"));
	assertEquals(getExpectedAsciiArt(), asciiArt);
    }

    private String getExpectedAsciiArt() {
	return "                                                                           \n"
		+ "                                                                           \n"
		+ "      ██     ██████    █████            ███    ███████    ██████           \n"
		+ "     ███    ████████  ███████           ███    ███████    ███████          \n"
		+ "   █████    ████████  ███ ███          ████    ███       ████████          \n"
		+ "   █████    ███  ███       ██    ██   █████    ███       ███               \n"
		+ "   ██ ██         ███      ███    ██   █████    ███████   ███               \n"
		+ "      ██         ███     ████        ███ ██    ████████  ███████           \n"
		+ "      ██        ████     █████      ████ ██    ███ ████  ████████          \n"
		+ "      ██       ████        ███      ███  ██         ███  ████████          \n"
		+ "      ██      ████         ███      █████████        ██  ███  ███          \n"
		+ "      ██     ████     ███  ███      █████████  ███  ███  ███  ███          \n"
		+ "      ██    ████      ████████           ██    ████████  ████████          \n"
		+ "      ██    ████████  ████████   ██      ██    ████████  ████████          \n"
		+ "      ██    ████████   ██████    ██      ██     ██████    ██████           \n";
    }
}
