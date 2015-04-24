package org.ms.iknow.core.service;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.persistence.repo.MemoryRepository;

public class Communicator {
  
    public Neuron create(Neuron neuron) {
        fire(neuron);
        return neuron;
    }

    public Synapse create(Synapse synapse) {
        fire(synapse);
        return synapse;
    }
  
    public Neuron ask(Neuron neuron) {
        return MemoryRepository.getInstance().find(neuron);
    }

    private static void fire(Neuron neuron) {
        MemoryRepository.getInstance().persist(neuron);
    }

    private static void fire(Synapse synapse) {
        MemoryRepository.getInstance().persist(synapse);
    }
}
