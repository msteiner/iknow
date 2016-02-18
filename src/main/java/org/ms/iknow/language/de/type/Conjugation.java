package org.ms.iknow.language.de.type;

public class Conjugation {

    Structure singular;
    Structure plural;

    /**
     * @return the singular
     */
    public Structure getSingular() {
        return singular;
    }

    /**
     * @param singular the singular to set
     */
    public void setSingular(Structure singular) {
        this.singular = singular;
    }

    /**
     * @return the plural
     */
    public Structure getPlural() {
        return plural;
    }

    /**
     * @param plural the plural to set
     */
    public void setPlural(Structure plural) {
        this.plural = plural;
    }
}
