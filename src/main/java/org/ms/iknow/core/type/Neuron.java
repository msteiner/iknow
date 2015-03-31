package org.ms.iknow.core.type;

//import org.ms.iknow.persistence.repo.MemoryRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron extends ElementBase {
  
  private String name;
  private List<Synapse> synapses;
  
  public String getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
    setChangeDate();
  }
  
  public List<Synapse> getSynapses() {
    return synapses;
  }
  
  public void addSynapse(Synapse synapse) {
    if (synapses == null) {
      synapses = new ArrayList<Synapse>();
    }
    synapses.add(synapse);
    setChangeDate();
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    builder.append(super.toString());
    builder.append("  name = " + name + "\n");
    if (synapses != null) {
      for (Synapse synapse : synapses) {
        builder.append("  synapse = " + synapse + "\n");
      }
    }
    
    return builder.toString();
  }
}