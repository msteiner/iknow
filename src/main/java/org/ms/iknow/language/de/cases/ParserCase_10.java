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
 * "Der Baum ist gross."
 */
public class ParserCase_10 extends ParserCase {

    @Override
    public Synapse parse(List<String> tokens) throws GrammarException {
        String substantive_1 = registerSubstantive(tokens.get(1));
        Relation relation = registerVerb(tokens.get(2));
        String adjective_1 = registerAdjective(tokens.get(3));
        return new Synapse(new Text(substantive_1), relation, new Text(adjective_1));
    }
}
