package org.ms.iknow.persistence.repo;

import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;

public interface GrammarRepository {
  
  public WordType assignGrammar(String expression) throws GrammarException;
  
  public void addExpression(String expression, WordType wordType) throws GrammarException;
  
  public void addVerb(String expression, RelationType relationType) throws GrammarException;
  
  public void deleteAll();
  
  public boolean containsGenus(String genus);
  
  public boolean containsSubstantive(String substantive);
  
  public boolean containsAdjective(String adjective);
  
  public boolean containsVerb(String verb);
  
  public boolean containsNumerus(String numerus);
  
  public boolean containsUndefined(String term);
  
  public Relation getRelation(String verb);
}
