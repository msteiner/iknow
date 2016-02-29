package org.ms.iknow.language.de;

import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.exception.BusinessException;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;

import java.util.List;

public abstract class ParserCase {
  
    protected List<String> tokens;
  
    protected GrammarRepository grammarRepository = GrammarRepositoryDE.getInstance();

    public abstract Synapse parse() throws GrammarException;
  
    protected String registerSubstantive(String expression) throws GrammarException {
        if (!grammarRepository.containsSubstantive(expression)) {
            grammarRepository.addExpression(expression, WordType.SUBSTANTIVE);
        }      
        return expression;
    }
    
    protected Relation registerVerb(String expression) throws GrammarException {
        return registerVerb(expression, null);
    }
  
    protected Relation registerVerb(String expression, String value) throws GrammarException {
        Relation relation;
        if (!grammarRepository.containsVerb(expression)) {
            grammarRepository.addExpression(expression, WordType.VERB);
            grammarRepository.addVerb(expression, RelationType.UNKNOWN);

            if (value != null) {
                try {
                    relation = new Relation(RelationType.UNKNOWN, value);
                } catch (BusinessException e) {
                    throw new GrammarException(e.getMessage());
                }
            } else {
                relation = new Relation(RelationType.UNKNOWN);
            }
            return relation;
        } else {
            relation = grammarRepository.getRelation(expression);
            relation.setValue(value);
            return relation;
        }
    }
  
    protected String registerAdjective(String expression) throws GrammarException {
        if (!grammarRepository.containsAdjective(expression)) {
            grammarRepository.addExpression(expression, WordType.ADJECTIVE);
        }      
        return expression;
    }
    
    protected String registerUnknown(String expression) throws GrammarException {
        if (!grammarRepository.containsSubstantive(expression) || !grammarRepository.containsAdjective(expression)) {
            grammarRepository.addExpression(expression, WordType.UNKNOWN);
        }
        return expression;
    }
}