package org.ms.iknow.language.de.type;

import org.ms.iknow.language.de.type.WordType;

public class Substantive extends Word {

    String name;

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    WordType getWordType() {
        return WordType.SUBSTANTIVE;
    }
}
