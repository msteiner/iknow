package org.ms.iknow.language.de.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "isSubstantive", description = "Checks wether a term is a substantive")
public class CheckSubstantiveRule {

    private boolean substantive;
    private String  term;

    @Condition
    public boolean checkIsKnown() {
        return true;
    }

    @Action
    public void setResult() {
        substantive = true;
    }

    public boolean isSubstantive() {
        return substantive;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }
}
