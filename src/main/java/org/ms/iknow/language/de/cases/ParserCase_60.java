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
 * "Bern hat 250000 Einwohner."
 */
public class ParserCase_60 extends ParserCase {
    
    public ParserCase_60(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Synapse parse() throws GrammarException {
        String unknown_1 = registerUnknown(tokens.get(0));
        Relation relation = registerVerb(tokens.get(1), tokens.get(2));
        String substantive_1 = registerSubstantive(tokens.get(3));
        return new Synapse(new Text(unknown_1), relation, new Text(substantive_1));
    }
}
