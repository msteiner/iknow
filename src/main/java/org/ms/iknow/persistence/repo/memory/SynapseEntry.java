package org.ms.iknow.persistence.repo.memory;

import org.ms.iknow.core.type.Synapse;

public class SynapseEntry extends Entry {
  
  private Synapse synapse;
  
  public Synapse getSynapse() {
    return this.synapse;
  }
  
  public void setSynapse(Synapse synapse) {
    this.synapse = synapse;
  }
}