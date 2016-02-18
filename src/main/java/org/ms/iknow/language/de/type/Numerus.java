package org.ms.iknow.language.de.type;

import org.ms.iknow.language.de.type.WordType;

public class Numerus extends Word {

    NumerusType type;

    @Override
    WordType getWordType() {
        return WordType.NUMERUS;
    }

    /**
     * @return the type
     */
    public NumerusType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(NumerusType type) {
        this.type = type;
    }
}
