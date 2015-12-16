package org.ms.iknow.persistence.repo.memory;

import org.ms.iknow.core.type.Neuron;

public class NeuronEntry extends Entry {
  
  private Neuron neuron;
  
  public Neuron getNeuron() {
    return this.neuron;
  }
  
  public void setNeuron(Neuron neuron) {
    this.neuron = neuron;
  }
}
