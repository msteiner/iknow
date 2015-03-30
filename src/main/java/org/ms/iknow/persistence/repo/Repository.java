package org.ms.iknow.persistence.repo;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;

public interface Repository {
  
  public Neuron persist(Neuron neuron);
  
  public Synapse persist(Synapse synapse);
  
  public Neuron find(Neuron neuron);
  
  public Neuron findNeuronById(String id);
  
  public Synapse find(Synapse synapse);
  
  public Synapse findSynapseById(String id);
  
  public void deleteAll();
}
