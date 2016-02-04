package org.ms.iknow.core.type.language;

import org.ms.iknow.language.de.type.WordType;

public class Verb extends Word {
  
    @Override
    WordType getWordType() {
        return WordType.VERB;
    }
}
