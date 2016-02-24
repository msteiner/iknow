package org.ms.iknow.language.de;

import org.ms.iknow.exception.BusinessException;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;

import java.util.List;

public class ParserCaseFactory {

    public static ParserCase getParserCase(List<String> tokens) throws BusinessException {

        if (tokens == null) {
            throw new BusinessException("Die Message ist null.");
        }

        ParserCase parserCase = null;
        int size = tokens.size();

        if (size < 3 || size > 5) {
            throw new BusinessException("Eine Message muss 3 bis 5 Wörter aufweisen, hier: " + size + " Wörter.");
        }
        if (size == 4 && isKnownAsGenus(tokens.get(0))) {
            //CASE 10
        } else if (size == 5 && isKnownAsGenus(tokens.get(0)) && !isKnownAsNumerus(tokens.get(3))) {
            //CASE 20 
        } else if (size == 3) {
            //CASE 30
        } else if (size == 4 && isKnownAsNumerus(tokens.get(2))) {
            //CASE 60
        } else if (size == 5 && isKnownAsGenus(tokens.get(0)) && isKnownAsNumerus(tokens.get(3))) {
            //CASE 70
        } else {
            throw new BusinessException("Unverständliche Expression");
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
            System.out.println("+++ Is a number");
            return true;
        } else {
            System.out.println("+++ Is not a number");
            return false;
        }
    }
}
