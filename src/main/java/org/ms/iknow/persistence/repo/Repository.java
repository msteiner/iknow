package org.ms.iknow.persistence.repo;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;

import java.util.List;

public interface Repository {

    /**
     * This method persists a {@link Neuron}. 
     */
    public Neuron persist(Neuron neuron);

    /**
     * This method persists the given Synapse including its Neuron's(parent and child).
     */
    public Synapse persist(Synapse synapse);

    /**
     * This method persists all synapses in a loop by calling {@link #persist(Synapse)}.
     */
    public List<Synapse> persist(List<Synapse> synapses);

    public List<Neuron> findByName(String name);

    public void deleteAll();
}
