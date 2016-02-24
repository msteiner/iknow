package org.ms.iknow.language.de;

import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;

import java.util.List;

public abstract class ParserCase {
  
    protected GrammarRepository grammarRepository = GrammarRepositoryDE.getInstance();

    public abstract Synapse parse(List<String> tokens) throws GrammarException;
  
    protected String registerSubstantive(String expression) throws GrammarException {
        if (!grammarRepository.containsSubstantive(expression)) {
            grammarRepository.addExpression(expression, WordType.SUBSTANTIVE);
        }
      
        return expression;
    }
  
    protected Relation registerVerb(String expression) throws GrammarException {
        if (!grammarRepository.containsVerb(expression)) {
            grammarRepository.addExpression(expression, WordType.VERB);
            grammarRepository.addVerb(expression, RelationType.UNKNOWN);
            Relation relation = new Relation(RelationType.UNKNOWN);
            return relation;
        }
        else {
            return grammarRepository.getRelation(expression);
        }
    }
  
    protected String registerAdjective(String expression) throws GrammarException {
        if (!grammarRepository.containsAdjective(expression)) {
            grammarRepository.addExpression(expression, WordType.ADJECTIVE);
        }
      
        return expression;
    }
}