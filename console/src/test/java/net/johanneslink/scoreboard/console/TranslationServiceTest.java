package net.johanneslink.scoreboard.console;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

public class TranslationServiceTest {

    @Test
    void canTranslateSimpleString() {
	final ResourceBundle bundle = ResourceBundle.getBundle("TestBundle", new Locale("my"));
	final String key = "helloworld";
	final String value = "m_allo Welt";
	final TranslationService translationService = new TranslationService(bundle);
	assertEquals(value, translationService.translate(key));
    }
}
