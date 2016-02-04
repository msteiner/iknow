package org.ms.iknow.client;

import java.util.Set;

public class Synset {

    String    id;
    Set<?>    categories;
    Set<Term> terms;

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

    /**
     * @return the categories
     */
    public Set<?> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(Set<?> categories) {
        this.categories = categories;
    }

    /**
     * @return the terms
     */
    public Set<Term> getTerms() {
        return terms;
    }

    /**
     * @param terms the terms to set
     */
    public void setTerms(Set<Term> terms) {
        this.terms = terms;
    }
}
