package org.ms.iknow.core.type;

import java.util.ArrayList;
import java.util.List;

public enum Relation {
  
  IS("1", "is"), 
  IS_NOT("2", "is not"), 
  HAS("3", "has"), 
  HAS_MANY("4", "has more than one"),
  HAS_NOT("5", "has not"),
  IS_PART_OF("6", "is part of"),
  CONSISTS_OF("7", "consists of"),
  CONTAINS("8", "contains"),
  IS_EQUALS("9", "is equals to"),
  IS_NOT_EQUALS("10", "is not equals to");
  
  private String id;
  private String value;
  private static List<Relation> list;
  static {
    list = new ArrayList<Relation>();
    list.add(IS);
    list.add(IS_NOT);
    list.add(HAS);
    list.add(HAS_MANY);
    list.add(HAS_NOT);
    list.add(IS_PART_OF);
    list.add(CONSISTS_OF);
    list.add(CONTAINS);
    list.add(IS_EQUALS);
    list.add(IS_NOT_EQUALS);
  }
  
  Relation(String id, String value) {
    this.id = id;
    this.value = value;
  }
  
  public static Relation getRelationById(String id) {
    for (Relation relation : list) {
      if (relation.getId().equals(id)) {
        return relation;
      }
    }
    return null;
  }
  
  public static Relation getRelationByValue(String value) {
    for (Relation relation : list) {
      if (relation.getValue().equals(value)) {
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
