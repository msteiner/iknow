package org.ms.iknow.core.type;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
  private long id;
  private String name;
  private List<ValueEntry> valueEntries = new ArrayList<ValueEntry>();
  
  public Neuron(Long id, String name, ValueEntry... valueEntries) {
    this.id = id;
    this.name = name;
    for (ValueEntry valueEntry : valueEntries) {
      this.valueEntries.add(valueEntry);
    }
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public List<ValueEntry> getValueEntries() {
    return valueEntries;
  }
  
  public ValueEntry getValueEntry(String key) {
    for (ValueEntry valueEntry : this.valueEntries) {
      if (valueEntry.getKey().equals(key)) {
        return valueEntry;
      }
    }
    return null;
  }
  
  public void addValueEntry(ValueEntry valueEntry) {
    this.valueEntries.add(valueEntry);
  }
}
