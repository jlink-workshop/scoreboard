package net.johanneslink.scoreboard.console;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

public class MessageTest {
    @Test
    void testReadEnglishMessages() {
	final Locale locale = new Locale("en", "US");
	final ResourceBundle bundle = ResourceBundle.getBundle("ScoreBoardMessages", locale);
	assertEquals("Hello World", bundle.getString("test"));
    }

    @Test
    void testReadGermanMessages() {
	final ResourceBundle bundle = ResourceBundle.getBundle("ScoreBoardMessages", new Locale("de", "DE"));
	assertEquals("Hallo Welt", bundle.getString("test"));
    }

    @Test
    void testDefault() {
	Locale.setDefault(Locale.FRENCH);
	final ResourceBundle bundle = ResourceBundle.getBundle("ScoreBoardMessages", new Locale("es", "ES"));
	assertEquals("Hola Mundo", bundle.getString("test"));
    }

}
