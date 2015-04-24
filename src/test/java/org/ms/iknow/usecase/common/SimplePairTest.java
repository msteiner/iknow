package org.ms.iknow.usecase.common;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ms.iknow.assertion.AssertionKit;
import org.ms.iknow.core.service.Communicator;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.persistence.repo.MemoryRepository;
import org.ms.iknow.persistence.repo.Repository;

public class SimplePairTest extends AssertionKit {

    private Communicator       communicator = null;
    private Repository         repository   = MemoryRepository.getInstance();

    public static final String TEXT_GREEN   = "green";
    public static final String TEXT_OAK     = "Oak";

    @Before
    public void init() {
        communicator = new Communicator();
        MemoryRepository.getInstance().deleteAll();
    }

    /**
     * A simple assigned assumption means 2 neurons and
     * a synapsis. This test covers the creation only.
     */
    @Test
    @Ignore
    public void testCreateAndReadPairAssumption() {

        // Create Neuron "green".
        Text green = new Text(TEXT_GREEN);
        green = (Text)communicator.create(green);

        assertNumberOfElements(1, MemoryRepository.getInstance().getNumberOfNeurons());
        assertNumberOfElements(0, MemoryRepository.getInstance().getNumberOfSynapses());

        Neuron result = repository.find(green);
        assertNotNull(result);
        assertNeuron(green, (Text)result);

        // Create Neuron "tree".
        Text oak = new Text(TEXT_OAK);
        // Create synapses from "tree" to "green".
        Synapse synapse = new Synapse(oak, Relation.IS, green);

        communicator.create(synapse);

        assertNumberOfElements(2, MemoryRepository.getInstance().getNumberOfNeurons());
        assertNumberOfElements(1, MemoryRepository.getInstance().getNumberOfSynapses());

        result = repository.find(oak);
        assertNotNull(result);
        assertNeuron(oak, (Text)result);

        result = repository.find(green);
        assertNotNull(result);
        assertNeuron(green, (Text)result);

        Synapse s1 = repository.find(synapse);
        assertSynapse(synapse, s1);
      
      System.out.println("===============================================================");
      MemoryRepository.getInstance().printAllNeurons();
      MemoryRepository.getInstance().printAllSynapses();
      System.out.println("===============================================================");
      
      String information = "";
        oak = (Text) communicator.ask(oak);
      assertNotNull("Neuron [oak]", oak);
      System.out.println("=== oak: " + oak);
      System.out.println("=== oak.synapseIds: " + oak.getSynapseIds());
      //System.out.println("=== oak.getSynapses()= " + oak.getSynapses());
      assertNotNull("Synapses", oak.getSynapses());
      assertTrue(1, oak.getSynapses().size());
      
        //System.out.println("antwort=" + antwort);
    }
}