package org.ms.iknow.core.type.language;

import org.ms.iknow.language.de.type.WordType;

public abstract class Word {
  
  private String expression;
  
  abstract WordType getWordType();
  
  public String getExpression() {
    return expression;
  }
  
  public void setExpression(String expression) {
    this.expression = expression;
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    builder.append("Expression: " + expression + "\n");
    
    return builder.toString();
  }
}
