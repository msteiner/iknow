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
 * "Vögel sind Tiere."
 */
public class ParserCase_30 extends ParserCase {
  
    public ParserCase_30(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Synapse parse() throws GrammarException {
        String unknown_1 = registerUnknown(tokens.get(0));
        Relation relation = registerVerb(tokens.get(1));
        String unknown_2 = registerUnknown(tokens.get(2));
        return new Synapse(new Text(unknown_1), relation, new Text(unknown_2));
    }
}
