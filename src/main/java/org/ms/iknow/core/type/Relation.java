package org.ms.iknow.core.type;

public enum Relation {
  
  IS("is"), IS_NOT("is not"), HAS("has"), HAS_MANY("has more than one"), IS_PART_OF("is part of");
  
  private String description;
  
  Relation(String description) {
    this.description = description;
  }
  
  public String getDescription() {
    return description;
  }
}
