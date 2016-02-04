package org.ms.iknow.core.type.language;

import org.ms.iknow.language.de.type.WordType;

public class Unknown extends Word {
  
    @Override
    WordType getWordType() {
        return WordType.UNKNOWN;
    }
}
