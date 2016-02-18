package org.ms.iknow.persistence.repo.memory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;

public class GrammarRepositoryDESynonymTest {
  
  GrammarRepository repo = GrammarRepositoryDE.getInstance();

    @Before
    public void init() {
      
    }
  
  @Test
  public void testAssignAndAddVerb() throws Exception {
    String expression = "besitzen";
    WordType wordType = null;
    wordType = repo.assignGrammar(expression);
    
    assertEquals("Expected WordType.UNKNOWN but was " + wordType + ".", WordType.UNKNOWN, wordType);
    
    // Since expression was not found: simulate an answer of a question relating on this.
    repo.addExpression("besitzen", WordType.VERB);
    
    wordType = repo.assignGrammar(expression);
    
    
  }
}
