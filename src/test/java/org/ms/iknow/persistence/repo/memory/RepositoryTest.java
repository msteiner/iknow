package org.ms.iknow.persistence.repo.memory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.dataset.BasicSet;

import java.util.List;

public class RepositoryTest {

    private static MemoryRepository repo = MemoryRepository.getInstance();

    @Before
    public void init() {
        repo.deleteAll();
    }

    @Test
    public void testIndexesCRUD() {

        String key1 = "Baum";
        String key2 = "Strauch";
        String value1 = "1001";
        String value2 = "1002";
        String value3 = "1003";

        List<String> values = null;
        String value = null;

        repo.addToIndex(key1, value1);
        values = repo.findInIndexes(key1);
        assertNotNull("Expected a list but was null.", values);
        assertEquals("Expected 1 entry but found " + values.size() + " entries.", 1, values.size());
        value = values.get(0);
        assertEquals("Expected [" + value1 + "] but found [" + value + ".", value1, value);

        repo.addToIndex(key1, value2);
        values = repo.findInIndexes(key1);
        assertNotNull("Expected a list but was null.", values);
        assertEquals("Expected 2 entries but found " + values.size() + " entries.", 2, values.size());
        value = values.get(0);
        assertEquals("Expected [" + value1 + "] but found [" + value + ".", value1, value);
        value = values.get(1);
        assertEquals("Expected [" + value2 + "] but found [" + value + ".", value2, value);

        repo.addToIndex(key2, value3);
        values = repo.findInIndexes(key2);
        assertNotNull("Expected a list but was null.", values);
        assertEquals("Expected 1 entry but found " + values.size() + " entries.", 1, values.size());
        value = values.get(0);
        assertEquals("Expected [" + value3 + "] but found [" + value + ".", value3, value);

        assertEquals("Expected 2 entries but found " + repo.getNumberOfIndexes() + ".", 2, repo.getNumberOfIndexes());

        repo.removeFromIndexes(key2);

        assertEquals("Expected 1 entries but found " + repo.getNumberOfIndexes() + ".", 1, repo.getNumberOfIndexes());

        repo.removeFromIndexes(key1, value2);
        assertEquals("Expected 1 entries but found " + repo.getNumberOfIndexes() + ".", 1, repo.getNumberOfIndexes());
        values = repo.findInIndexes(key1);
        assertNotNull("Expected a list but was null.", values);
        assertEquals("Expected 1 entry but found " + values.size() + " entries.", 1, values.size());
        value = values.get(0);
        assertEquals("Expected [" + value1 + "] but found [" + value + ".", value1, value);

    }

    @Test
    public void testNeuronsCRUD() {

    }

    @Test
    public void testPersistSameNeuronTwoTimes() {
        Neuron baum1 = BasicSet.getBaum();
        Neuron baum2 = BasicSet.getBaum();

        repo.persist(baum1);
        repo.persist(baum2);

        List<Neuron> neurons = repo.findByName(baum1.getName());
        assertEquals("Expected 1 neurons but found", 2, neurons.size());

    }

    @Test
    public void testPersistNeuronWithChildTwoTimes() {
        Neuron baum_1 = new Text("Baum");
        Neuron baum_2 = new Text("Baum");
        Neuron baum_3 = new Text("Baum");
        Neuron stamm_1 = new Text("Stamm");
        Neuron stamm_2 = new Text("Stamm");
        Neuron stamm_3 = new Text("Stamm");

        Synapse synapse_1 = new Synapse(baum_1, new Relation(RelationType.HAS), stamm_1);
        Synapse synapse_2 = new Synapse(baum_2, new Relation(RelationType.HAS), stamm_2);
        Synapse synapse_3 = new Synapse(baum_3, new Relation(RelationType.HAS), stamm_3);

        assertEquals("Expected empty neurons repository but wasen't.", 0, MemoryRepository.getInstance().getNumberOfNeurons());
        assertEquals("Expected empty synapses repository but wasen't.", 0, MemoryRepository.getInstance().getNumberOfSynapses());

        repo.persist(synapse_1);
        repo.persist(synapse_2);
        repo.persist(synapse_3);

        assertEquals("Expected 6 indexes in repository but found " + MemoryRepository.getInstance().getNumberOfIndexes() + ".", 6,
                     MemoryRepository.getInstance().getNumberOfNeurons());
        assertEquals("Expected 6 neurons in repository but found " + MemoryRepository.getInstance().getNumberOfNeurons() + ".", 6,
                     MemoryRepository.getInstance().getNumberOfNeurons());
        assertEquals("Expected 3 synapses in repository but found " + MemoryRepository.getInstance().getNumberOfSynapses() + ".", 3,
                     MemoryRepository.getInstance().getNumberOfSynapses());

        // 1. Test on tables
        for (String key : MemoryRepository.getInstance().neurons.keySet()) {
            Neuron n = MemoryRepository.getInstance().neurons.get(key).getNeuron();
            if (n.getName().equals("Baum")) {
                assertEquals("Expected 1 synapseId but found " + n.getSynapseIds().size(), 1, n.getSynapseIds().size());
                for (String synapseId : n.getSynapseIds()) {

                    SynapseEntry synapseEntry = MemoryRepository.getInstance().synapses.get(synapseId);
                    Synapse synapse = synapseEntry.getSynapse();
                    Neuron child = MemoryRepository.getInstance().neurons.get(synapse.getChildId()).getNeuron();
                    assertEquals("Expected child name [Stamm] but found " + child.getName(), "Stamm", child.getName());
                }
            } else if (n.getName().equals("Stamm")) {
                assertEquals("Expected 0 synapseId but found " + n.getSynapseIds().size(), 0, n.getSynapseIds().size());
            } else {
                fail("Expected [Baum] or [Stamm] but found " + n.getName());
            }
        }

        // 2. Test on search
        List<Neuron> neurons = repo.findByName("Baum");
        assertEquals("Expected 3 neurons but found " + neurons.size() + " neurons.", 3, neurons.size());
        for (Neuron neuron : neurons) {
            assertEquals("Expected 'Baum' but found " + neuron.getName() + ".", "Baum", neuron.getName());
            assertEquals("Expected 1 Synapse but found " + neuron.getSynapses().size(), 1, neuron.getSynapses().size());
            Synapse s = neuron.getSynapses().get(0);
            String childName = s.getChild().getName();
            assertEquals("Expected 'Stamm' but found '" + childName + "'.", "Stamm", childName);
        }
    }

    @Test
    public void testPersistParentNeuronWithTwoChildNeurons() {
        Neuron baum = BasicSet.getBaum();
        Neuron stamm = BasicSet.getStamm();
        Neuron blatt = BasicSet.getBlatt();
        Neuron braun = BasicSet.getBraun();
        Neuron gruen = BasicSet.getGruen();

        assertEquals("Expected empty neurons repository but wasen't.", 0, MemoryRepository.getInstance().getNumberOfNeurons());
        assertEquals("Expected empty synapses repository but wasen't.", 0, MemoryRepository.getInstance().getNumberOfSynapses());

        Synapse s1 = new Synapse(baum, new Relation(RelationType.HAS), stamm);
        Synapse s2 = new Synapse(baum, new Relation(RelationType.HAS_MANY), blatt);
        Synapse s3 = new Synapse(baum, new Relation(RelationType.IS), braun);
        Synapse s4 = new Synapse(baum, new Relation(RelationType.IS), gruen);

        repo.persist(s1);
        repo.persist(s2);
        repo.persist(s3);
        repo.persist(s4);

        assertEquals("Expected 5 indexes in repository but found " + MemoryRepository.getInstance().getNumberOfIndexes() + ".", 5,
                     MemoryRepository.getInstance().getNumberOfNeurons());
        assertEquals("Expected 5 neurons in repository but found " + MemoryRepository.getInstance().getNumberOfNeurons() + ".", 5,
                     MemoryRepository.getInstance().getNumberOfNeurons());
        assertEquals("Expected 4 synapses in repository but found " + MemoryRepository.getInstance().getNumberOfSynapses() + ".", 4,
                     MemoryRepository.getInstance().getNumberOfSynapses());

        List<Neuron> neurons = repo.findByName(baum.getName());

        assertEquals("Expected 1 neuron but found " + neurons.size() + " neurons.", 1, neurons.size());
        Neuron expectedNeuron = neurons.get(0);
        assertEquals("Expected name='baum' but found " + expectedNeuron.getName() + ".", baum.getName(), expectedNeuron.getName());
        assertNotNull("Expected id but was null", expectedNeuron.getId());

        List<Synapse> synapses = expectedNeuron.getSynapses();
        assertEquals("Expected 4 synapses but found " + synapses.size(), 4, synapses.size());

        for (Synapse s : synapses) {
            assertNotNull("Expected Synapse but was null", s);
            Neuron n = s.getChild();
            assertNotNull("Expected child Neuron but was null.", n);
            assertTrue("Expected a value of a list but found " + n.getName(),
                       isOneOfThese(n, stamm.getName(), blatt.getName(), gruen.getName(), braun.getName()));
        }
    }

    private boolean isOneOfThese(Neuron n, String... names) {

        for (String name : names) {
            if (name.equals(n.getName())) {
                return true;
            }
        }
        return false;
    }
}
