package org.ms.iknow.core.service;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.persistence.repo.MemoryRepository;
import org.ms.iknow.persistence.repo.Repository;

import java.util.List;

public class CoreStatementService {

    private Repository repository = MemoryRepository.getInstance();

    public void persist(Neuron neuron) {
        repository.persist(neuron);
    }

    public List<Neuron> findByName(String name) {
        return repository.findByName(name);
    }
}
