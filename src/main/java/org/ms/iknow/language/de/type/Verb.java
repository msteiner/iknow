package org.ms.iknow.language.de.type;

import org.ms.iknow.language.de.type.WordType;

/**
 * http://konjugator.reverso.net/konjugation-deutsch-verb-sein.html
 */
public class Verb extends Word {

    String      present;
    Conjugation indikativPräsens;
    Conjugation indikativPraeteritum;
    Conjugation indikativFutur1;

    public Verb() {
        super();
    }

    @Override
    WordType getWordType() {
        return WordType.VERB;
    }

    /**
     * @return the present
     */
    public String getPresent() {
        return present;
    }

    /**
     * @param present the present to set
     */
    public void setPresent(String present) {
        this.present = present;
    }

    /**
     * @return the indikativPräsens
     */
    public Conjugation getIndikativPräsens() {
        return indikativPräsens;
    }

    /**
     * @param indikativPräsens the indikativPräsens to set
     */
    public void setIndikativPräsens(Conjugation indikativPräsens) {
        this.indikativPräsens = indikativPräsens;
    }

    /**
     * @return the indikativPraeteritum
     */
    public Conjugation getIndikativPraeteritum() {
        return indikativPraeteritum;
    }

    /**
     * @param indikativPraeteritum the indikativPraeteritum to set
     */
    public void setIndikativPraeteritum(Conjugation indikativPraeteritum) {
        this.indikativPraeteritum = indikativPraeteritum;
    }

    /**
     * @return the indikativFutur1
     */
    public Conjugation getIndikativFutur1() {
        return indikativFutur1;
    }

    /**
     * @param indikativFutur1 the indikativFutur1 to set
     */
    public void setIndikativFutur1(Conjugation indikativFutur1) {
        this.indikativFutur1 = indikativFutur1;
    }
}
