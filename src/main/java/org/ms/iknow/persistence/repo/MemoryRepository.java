package org.ms.iknow.persistence.repo;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository implements Repository {

    private static MemoryRepository instance;
    private Map<String, Neuron> neurons = new HashMap<String, Neuron>();
    private Map<String, Synapse> synapses = new HashMap<String, Synapse>();

    protected MemoryRepository() {
        // Exists only to defeat instantiation.
    }

    public static MemoryRepository getInstance() {
        if (instance == null) {
            instance = new MemoryRepository();
        }
        return instance;
    }

    @Override
    public Neuron persist(Neuron neuron) {
        this.neurons.put(neuron.getId(), neuron);
        return neuron;
    }

    @Override
    public Synapse persist(Synapse synapse) {
        Synapse s = synapse;
        persist(synapse.getParent());
        persist(synapse.getChild());
        s = s.cloneSimple();
        this.synapses.put(synapse.getId(), s);
        return s;
    }

    @Override
    public Neuron find(Neuron neuron) {
        String id = neuron.getId();
        return neurons.get(id);
    }
  
  @Override
  public Neuron findNeuronById(String id) {
    return neurons.get(id);
  }

    @Override
    public Synapse find(Synapse synapse) {
        String id = synapse.getId();
        return this.synapses.get(id);
    }
  
  @Override
  public Synapse findSynapseById(String id) {
    return synapses.get(id);
  }

    public int getNumberOfNeurons() {
        return neurons.size();
    }

    public int getNumberOfSynapses() {
        return synapses.size();
    }

    @Override
    public void deleteAll() {
        neurons.clear();
        synapses.clear();
    }
  
    public void printAllNeurons() {
        for (Map.Entry<String, Neuron> entry : neurons.entrySet()) {
            String key = entry.getKey();
            Neuron value = entry.getValue();
            System.out.println("... " + key + ":" + value);
        }
    }
  
  public void printAllSynapses() {
        for (Map.Entry<String, Synapse> entry : synapses.entrySet()) {
            String key = entry.getKey();
            Synapse value = entry.getValue();
            System.out.println("... " + key + ":" + value);
        }
    }
}