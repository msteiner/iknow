package org.ms.iknow.language;

import org.ms.iknow.language.de.LanguageParserDE;

import java.util.Locale;

public class LanguageParserFactory {

    public static final Locale LANGUAGE_DEFAULT = Locale.GERMAN;

    public static LanguageParser getParser(Locale language) {
        if (language == null) {
            language = LANGUAGE_DEFAULT;
        }
        if (language.equals(Locale.GERMAN)) {
            return new LanguageParserDE();
        } 
        else {
            return new LanguageParserDE();
        }
    }
}
