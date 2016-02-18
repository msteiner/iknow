package org.ms.iknow.language.de.type;

import java.util.UUID;

public abstract class Word {

    String id;

    public Word() {
        id = UUID.randomUUID().toString();
    }

    abstract WordType getWordType();
  
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}