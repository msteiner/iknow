package org.ms.iknow.language;

import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.language.Word;

import java.util.List;

public interface LanguageParser {
  
  @Deprecated
  public List<Synapse> parseStatement(String message);
  
  public List<Word> parseExpression(String expression);
}
