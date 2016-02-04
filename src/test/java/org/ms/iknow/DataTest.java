package org.ms.iknow;

import org.junit.Before;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.persistence.repo.Repository;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;
import org.ms.iknow.service.file.FileImporterService;
import org.ms.iknow.service.type.StatementEntry;

import java.util.List;

import static org.junit.Assert.*;

public abstract class DataTest {
  
  FileImporterService fileImporterService = new FileImporterService();
  Repository repository = MemoryRepository.getInstance();
  
  public void importData(String fileName) {
    List<StatementEntry> entries = fileImporterService.readNeurons(fileName);
    for (StatementEntry entry : entries) {
      Neuron parent = new Text(entry.getParentName());
      Relation relation = Relation.getRelationById(entry.getRelationId());
      Neuron child = new Text(entry.getChildName());
      Synapse synapse = new Synapse(parent, relation, child);
      repository.persist(synapse);
    }
  }
}
