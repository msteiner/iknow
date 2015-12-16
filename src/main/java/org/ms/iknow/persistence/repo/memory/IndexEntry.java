package org.ms.iknow.persistence.repo.memory;

public class IndexEntry extends Entry {
  
  private String foreignKey;

  public void setForeignKey(String foreignKey) {
    this.foreignKey = foreignKey;
  }
  
  public String getForeignKey() {
    return this.foreignKey;
  }
}
