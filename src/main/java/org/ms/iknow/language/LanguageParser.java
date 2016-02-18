package org.ms.iknow.language;

import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.exception.GrammarException;

import java.util.List;

public interface LanguageParser {
  
  @Deprecated
  public List<Synapse> parseStatement(String message);
  
  public Synapse parseExpression(Message message) throws GrammarException;
}
