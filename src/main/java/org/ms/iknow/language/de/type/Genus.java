package org.ms.iknow.language.de.type;

import org.ms.iknow.language.de.type.WordType;

public class Genus extends Word {

    GenusType type;

    @Override
    WordType getWordType() {
        return WordType.GENUS;
    }

    /**
     * @return the genusType
     */
    public GenusType getType() {
        return type;
    }

    /**
     * @param genusType the genusType to set
     */
    public void setType(GenusType type) {
        this.type = type;
    }
}
