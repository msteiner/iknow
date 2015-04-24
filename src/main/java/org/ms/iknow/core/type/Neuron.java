package org.ms.iknow.core.type;

//import org.ms.iknow.persistence.repo.MemoryRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron extends ElementBase {
  
  protected String name;
  private List<Synapse> synapses = new ArrayList<Synapse>();
  private List<String> synapseIds = new ArrayList<String>();
  
  public String getId() {
    return id;
  }
  
  public abstract String getName();
  
  public void setName(String name) {
    this.name = name;
    setChangeDate();
  }
  
  public List<Synapse> getSynapses() {
    return synapses;
  }
  
  public List<String> getSynapseIds() {
    return synapseIds;
  }
  
  public void addSynapse(Synapse synapse) {
    synapseIds.add(synapse.getId());
    synapses.add(synapse);
    setChangeDate();
  }
  
  public abstract Neuron clone();
  
  protected void cloneNeuron(Neuron neuron) {
    neuron.name = this.name;
    neuron.synapseIds = this.synapseIds;
    neuron.synapses = this.synapses;
    cloneElementBase(neuron);
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    builder.append(super.toString());
    builder.append("  name = " + name + "\n");
    /**
    if (synapses != null) {
      for (Synapse synapse : synapses) {
        builder.append("  synapse = " + synapse + "\n");
      }
    }
    */
    return builder.toString();
  }
}