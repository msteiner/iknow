package org.ms.iknow.core.type;

import java.util.ArrayList;
import java.util.List;

public enum Relation {
  
  IS("1", "is"), IS_NOT("2", "is not"), HAS("3", "has"), HAS_MANY("4", "has more than one"), IS_PART_OF("5", "is part of");
  
  private String id;
  private String value;
  private static List<Relation> list;
  static {
    list = new ArrayList<Relation>();
    list.add(IS);
    list.add(IS_NOT);
    list.add(HAS);
    list.add(HAS_MANY);
    list.add(IS_PART_OF);
  }
  
  Relation(String id, String value) {
    this.id = id;
    this.value = value;
  }
  
  public static Relation getRelation(String id) {
    for (Relation relation : list) {
      if (relation.getId().equals(id)) {
        return relation;
      }
    }
    return null;
  }
  
  public static List<Relation> getAllValues() {
    return list;
  }
  
  public String getId() {
    return id;
  }
  
  public String getValue() {
    return value;
  }
}
