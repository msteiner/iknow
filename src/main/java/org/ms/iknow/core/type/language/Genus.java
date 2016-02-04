package org.ms.iknow.core.type.language;

import org.ms.iknow.language.de.type.WordType;

public class Genus extends Word {

    private GenusTypeDE type;

    @Override
    WordType getWordType() {
        return WordType.GENUS;
    }
  
    /**
     * @return the type
     */
    public GenusTypeDE getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(GenusTypeDE type) {
        this.type = type;
    }
}