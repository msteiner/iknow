package org.ms.iknow.core.type;

public class ValueEntry {
  private Long id;
  private String key;
  private Object value;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public Object getValue() {
    return value;
  }
  
  public void setValue(Object value) {
    this.value = value;
  }
}
