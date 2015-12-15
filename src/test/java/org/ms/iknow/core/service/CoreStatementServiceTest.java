package org.ms.iknow.core.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;
import java.util.List;

public class CoreStatementServiceTest {
  
  public static final String PARENT = "Baum";
  public static final String CHILD_1 = "Blatt";
  public static final String CHILD_2 = "Krone";
  public static final String CHILD_3 = "Gruen";
  public static final String CHILD_4 = "Braun";
  
  private CoreStatementService service = null;
  
  @Before
  public void init() {
    service = new CoreStatementService();
    MemoryRepository.getInstance().deleteAll();
  }
  
  @Test
  @Ignore
    public void testCreateSynapses() {

        Neuron parent = new Text(PARENT);
        Neuron child_1 = new Text(CHILD_1);
        Neuron child_2 = new Text(CHILD_2);
        Neuron child_3 = new Text(CHILD_3);
        Neuron child_4 = new Text(CHILD_4);
        Synapse synapse_1 = new Synapse(parent, Relation.HAS_MANY, child_1);
        Synapse synapse_2 = new Synapse(parent, Relation.HAS, child_2);
        Synapse synapse_3 = new Synapse(parent, Relation.IS, child_3);
        Synapse synapse_4 = new Synapse(parent, Relation.IS, child_4);
System.out.println("******** [" + parent.getName() + "] has " + parent.getSynapseIds().size() + " synapseIds.");

        service.persist(synapse_1);
        service.persist(synapse_2);
        service.persist(synapse_3);
        service.persist(synapse_4);
      
      
        int nrOfNeurons = MemoryRepository.getInstance().getNumberOfNeurons();
        int nrOfSynapses = MemoryRepository.getInstance().getNumberOfSynapses();
        Assert.assertTrue("Expected 5 neurons in repo but found " + nrOfNeurons, 5 == nrOfNeurons);
        Assert.assertTrue("Expected 4 synapses in repo but found " + nrOfSynapses, 4 == nrOfSynapses);

        List<Neuron> results = service.findByName(PARENT);
        Assert.assertNotNull(results);
        Assert.assertTrue("Expected 1 Neuron but found " + results.size(), 1 == results.size());

        Neuron n = results.get(0);
        Assert.assertEquals("Expected 4 Synapses but found " + n.getSynapses().size(), 4, n.getSynapses().size());
        for (Synapse s : n.getSynapses()) {
            Neuron c = s.getChild();
            Assert.assertNotNull("Expected Neuron(child) but was null", c);
            boolean isOneOfThese =
                                   c.getName().equals(CHILD_1) || c.getName().equals(CHILD_2) || c.getName().equals(CHILD_3)
                                       || c.getName().equals(CHILD_4);
            Assert.assertTrue(true == isOneOfThese);
        }
    }
}