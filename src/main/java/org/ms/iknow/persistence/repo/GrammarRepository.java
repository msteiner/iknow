package org.ms.iknow.persistence.repo;

import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;

public interface GrammarRepository {
  
  public WordType assignGrammar(String expression) throws GrammarException;
  
  public void addExpression(String expression, WordType wordType) throws GrammarException;
  
  public void deleteAll();
}
