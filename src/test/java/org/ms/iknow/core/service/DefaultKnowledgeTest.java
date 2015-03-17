package org.ms.iknow.core.service;

import org.junit.Test;

public class DefaultKnowledgeTest {
  
  private Knowledge knowledge = null;
  
  @Test
  public void testCreateAssumption() {
    knowledge = new DefaultKnowledge();
    knowledge.createAssumption();
  }
}
