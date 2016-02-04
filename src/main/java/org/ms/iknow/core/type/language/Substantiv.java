package org.ms.iknow.core.type.language;

import org.ms.iknow.language.de.type.WordType;

public class Substantiv extends Word {
  
    @Override
    WordType getWordType() {
        return WordType.SUBSTANTIVE;
    }

    private Genus   genus;

    private Numerus numerus;

    /**
     * @return the genus
     */
    public Genus getGenus() {
        return genus;
    }

    /**
     * @param genus the genus to set
     */
    public void setGenus(Genus genus) {
        this.genus = genus;
    }

    /**
     * @return the numerus
     */
    public Numerus getNumerus() {
        return numerus;
    }

    /**
     * @param numerus the numerus to set
     */
    public void setNumerus(Numerus numerus) {
        this.numerus = numerus;
    }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    super.toString();
    builder.append("genus: " + this.genus + "\n");
    builder.append("numerus: " + this.numerus + "\n");
    
    return builder.toString();
  }
}
