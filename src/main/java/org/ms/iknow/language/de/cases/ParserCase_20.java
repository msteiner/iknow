package org.ms.iknow.language.de.cases;

import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.ParserCase;

import java.util.List;

/**
 * Siehe dazu DESIGN LANGUAGE PARSER DE
 * Beispiel:
 * "Der ICN ist ein Zug."
 */
public class ParserCase_20 extends ParserCase {
    
    public ParserCase_20(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Synapse parse() throws GrammarException {
        String substantive_1 = registerSubstantive(tokens.get(1));
        Relation relation = registerVerb(tokens.get(2));
        String substantive_2 = registerSubstantive(tokens.get(4));
        return new Synapse(new Text(substantive_1), relation, new Text(substantive_2));
    }
}
