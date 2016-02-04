package org.ms.iknow.core.type.language;

import org.ms.iknow.language.de.type.WordType;

public class Numerus extends Word {

    private NumerusTypeDE type;
  
    @Override
    WordType getWordType() {
        return WordType.NUMERUS;
    }

    /**
     * @return the type
     */
    public NumerusTypeDE getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(NumerusTypeDE type) {
        this.type = type;
    }
}
