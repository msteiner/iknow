package org.ms.iknow.language.de;

import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.cases.ParserCase_10;
import org.ms.iknow.language.de.cases.ParserCase_20;
import org.ms.iknow.language.de.cases.ParserCase_30;
import org.ms.iknow.language.de.cases.ParserCase_60;
import org.ms.iknow.language.de.cases.ParserCase_70;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;

import java.util.List;

public class ParserCaseFactory {

    public static ParserCase getParserCase(List<String> tokens) throws GrammarException {

        if (tokens == null) {
            throw new GrammarException("Die Message ist null.");
        }

        ParserCase parserCase = null;
        int size = tokens.size();

        if (size < 3 || size > 5) {
            throw new GrammarException("Eine Message muss 3 bis 5 Wörter aufweisen, hier: " + size + " Wörter.");
        }
        if (size == 4 && isKnownAsGenus(tokens.get(0))) {            
            parserCase = new ParserCase_10(tokens); //CASE 10
        } else if (size == 5 && isKnownAsGenus(tokens.get(0)) && !isKnownAsNumerus(tokens.get(3))) {
            parserCase = new ParserCase_20(tokens); //CASE 20
        } else if (size == 3) {
            parserCase = new ParserCase_30(tokens); //CASE 30
        } else if (size == 4 && isKnownAsNumerus(tokens.get(2))) {
            parserCase = new ParserCase_60(tokens); //CASE 60
        } else if (size == 5 && isKnownAsGenus(tokens.get(0)) && isKnownAsNumerus(tokens.get(3))) {
            parserCase = new ParserCase_70(tokens); //CASE 70
        } else {
            throw new GrammarException("Unverständliche Expression");
        }
        return parserCase;
    }

    private static boolean isKnownAsGenus(String token) {
        return GrammarRepositoryDE.getInstance().containsGenus(token);
    }

    private static boolean isKnownAsNumerus(String token) {
        if (isNumeric(token)) {
          return true;
        }
        else if (GrammarRepositoryDE.getInstance().containsNumerus(token)) {
          return true;
        }
        else {
          return false;
        }        
    }

    private static boolean isNumeric(String token) {
        if (token.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            return true;
        } else {
            return false;
        }
    }
}
