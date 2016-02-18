package org.ms.iknow.language.de.type;

public class Adjective extends Word {

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
        return WordType.ADJECTIVE;
    }
}
