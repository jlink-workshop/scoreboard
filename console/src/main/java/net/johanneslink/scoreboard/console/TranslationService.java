package net.johanneslink.scoreboard.console;

import java.util.ResourceBundle;

public class TranslationService {

    private final ResourceBundle bundle;

    public TranslationService(final ResourceBundle bundle) {
	this.bundle = bundle;
    }

    public String translate(final String key) {
	return bundle.getString(key);
    }

}
