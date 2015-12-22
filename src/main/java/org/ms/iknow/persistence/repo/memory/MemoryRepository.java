package org.ms.iknow.persistence.repo.memory;

import org.ms.iknow.core.assertion.ElementValidator;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.persistence.repo.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryRepository extends ElementValidator implements Repository {

    private static MemoryRepository instance;
    // <Name, id>
    Map<String, List<IndexEntry>> indexes = new HashMap<String, List<IndexEntry>>();
    Map<String, NeuronEntry> neurons = new HashMap<String, NeuronEntry>();
    Map<String, SynapseEntry> synapses = new HashMap<String, SynapseEntry>();

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
        checkNotNull("Expected Neuron but was null.", neuron);
        addToIndex(neuron.getName(), neuron.getId());
        addToNeurons(neuron);
        return neuron;
    }

    @Override
    public Synapse persist(Synapse synapse) {
      Neuron parent = synapse.getParent();
      Neuron child = synapse.getChild();
      System.out.print("+++ ___ MemoryRepository.persist(Synapse): ");
      System.out.println("Parent has " + parent.getSynapseIds().size() + " SynapseIds.");
      
      System.out.print("+++ ___ MemoryRepository.persist(Synapse): ");
      System.out.println("Child has " + child.getSynapseIds().size() + " SynapseIds.");
      
      System.out.print("+++ ___ MemoryRepository.persist(Synapse): ");      
      System.out.print(parent.getName() + "[" + parent.getId() + "/" + synapse.getParentId() + "] ");
      System.out.print("[" + synapse.getId() + "] ");
      System.out.println(child.getName() + "[" + child.getId() + "/" + synapse.getChildId() + "]");
      
        checkNotNull("Expected Synapse but was null.", synapse);
        addToIndex(synapse.getParent());
        addToIndex(synapse.getChild());
        addToNeurons(synapse.getParent());
        addToNeurons(synapse.getChild());
        addToSynapses(synapse);
      
      List<IndexEntry> indexEntries = indexes.get(parent.getName());
      for (IndexEntry ie : indexEntries) {
        System.out.println("*** [" + parent.getName() + "]:[" + ie.getForeignKey() + "]");
      }
      for (String key : neurons.keySet()) {
        Neuron n = neurons.get(key).getNeuron();        
        System.out.println("*** [" + key + "]:[" + n.getName() + "]");
        for (String synapseId : n.getSynapseIds()) {
          System.out.println("   *** [" + synapseId + "]");
        }
        
      }
      
        return synapse;
    }

    @Override
    public List<Synapse> persist(List<Synapse> synapses) {
        for (Synapse synapse : synapses) {
          persist(synapse);
        }
        return synapses;
    }
  
    @Override
    public List<Neuron> findByName(String name) {
        List<Neuron> results = new ArrayList<Neuron>();
        List<String> synapseIds = null;
        List<Synapse> synapses = null;
        List<String> neuronIds = findInIndexes(name);
System.out.println("+++ MemoryRepository.findByName(): found " + neuronIds.size() + " neuronIds.");
        Neuron child = null;
        for (String neuronId : neuronIds) {
            Neuron parent = findInNeurons(neuronId);
System.out.println("   +++ run " + parent.getName() + " with " + parent.getSynapseIds().size() + " synapses...");

            synapseIds = parent.getSynapseIds();
            synapses = findInSynapses(synapseIds);
            for (Synapse synapse : synapses) {
                child = findInNeurons(synapse.getChildId());
                synapse.setParent(parent);
                synapse.setChild(child);
                parent.addSynapse(synapse);
            }
            results.add(parent);
        }
        return results;
    }
        
    protected void addToIndex(Neuron neuron) {
        addToIndex(neuron.getName(), neuron.getId());
    }
  
    protected void addToIndex(String key, String value) {
        List<IndexEntry> entries = null;
        IndexEntry entry = new IndexEntry();
        entry.lock();
        entry.setForeignKey(value);

        // Key found: entry already existing.
        if (indexes.containsKey(key)) {
            entries = indexes.get(key);
            if (!contains(value, entries)) {
                entries.add(entry);
            }
        }
        // Key not found: make a new entry.
        else {
            entries = new CopyOnWriteArrayList<IndexEntry>();
            entries.add(entry);
            indexes.put(key, entries);
        }
        entry.unlock();
    }
  
    protected void addToNeurons(Neuron neuron) {
        addToNeurons(neuron.getName(), neuron);
    }
  
    protected void addToNeurons(String key, Neuron neuron) {
        Neuron n = neuron.clone();
        NeuronEntry entry = new NeuronEntry();
        entry.setNeuron(n);
        entry.lock();
        this.neurons.put(n.getId(), entry);
        entry.unlock();
    }
  
    protected void addToSynapses(Synapse synapse) {
        Synapse s = synapse.clone();
        SynapseEntry entry = new SynapseEntry();
        entry.setSynapse(s);
        entry.lock();
        this.synapses.put(s.getId(), entry);
        entry.unlock();
    }

    /**
     * Returns the value (List<String>) of the entry matching key.
     */
    protected List<String> findInIndexes(String key) {
        List<IndexEntry> indexEntries = indexes.get(key);
        List<String> foreignKeys = new ArrayList<String>();
        for (IndexEntry indexEntry : indexEntries) {
            foreignKeys.add(indexEntry.getForeignKey());
        }
        return foreignKeys;
    }
  
    protected Neuron findInNeurons(String key) {
        return neurons.get(key).getNeuron();
    }
  
    protected List<Neuron> findInNeurons(List<String> keys) {
        List<Neuron> results = new ArrayList<Neuron>();
        Neuron clonedNeuron = null;

        for (String key : keys) {
            NeuronEntry neuronEntry = neurons.get(key);
            clonedNeuron = neuronEntry.getNeuron().clone();
            results.add(clonedNeuron);
        }

        return results;
    }
  
    protected List<Synapse> findInSynapses(List<String> keys) {
        List<Synapse> results = new ArrayList<Synapse>();
        Synapse clonedSynapse = null;

        for (String key : keys) {
            SynapseEntry synapseEntry = synapses.get(key);
            clonedSynapse = synapseEntry.getSynapse().clone();
            results.add(clonedSynapse);
        }

        return results;
    }
  
    protected Synapse findInSynapses(String key) {
        SynapseEntry synapseEntry = synapses.get(key);
        return synapseEntry.getSynapse().clone();
    }

    protected void removeFromIndexes(String key) {
        if (indexes.containsKey(key)) {
            indexes.remove(key);
        }
    }

    protected void removeFromIndexes(String key, String foreignKey) {
        List<IndexEntry> foreignKeys = indexes.get(key);
        for (IndexEntry indexEntry : foreignKeys) {
            if (indexEntry.getForeignKey().equals(foreignKey)) {
                indexEntry.lock();
                foreignKeys.remove(indexEntry);
                indexEntry.unlock();
            }
        }
    }
  
    protected boolean contains(String value, List<IndexEntry> entries) {
        for (IndexEntry indexEntry : entries) {
            if (indexEntry.getForeignKey().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteAll() {
        neurons.clear();
        synapses.clear();
        indexes.clear();
    }

    public int getNumberOfNeurons() {
        return neurons.size();
    }

    public int getNumberOfSynapses() {
        return synapses.size();
    }
  
    public int getNumberOfIndexes() {
        return indexes.size();
    }
}