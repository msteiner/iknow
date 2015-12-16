package org.ms.iknow.persistence.repo.memory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
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
        Assert.assertNotNull("Expected a list but was null.", values);
        Assert.assertEquals("Expected 1 entry but found " + values.size() + " entries.", 1, values.size());
        value = values.get(0);
        Assert.assertEquals("Expected [" + value1 + "] but found [" + value + ".", value1, value);

        repo.addToIndex(key1, value2);
        values = repo.findInIndexes(key1);
        Assert.assertNotNull("Expected a list but was null.", values);
        Assert.assertEquals("Expected 2 entries but found " + values.size() + " entries.", 2, values.size());
        value = values.get(0);
        Assert.assertEquals("Expected [" + value1 + "] but found [" + value + ".", value1, value);
        value = values.get(1);
        Assert.assertEquals("Expected [" + value2 + "] but found [" + value + ".", value2, value);

        repo.addToIndex(key2, value3);
        values = repo.findInIndexes(key2);
        Assert.assertNotNull("Expected a list but was null.", values);
        Assert.assertEquals("Expected 1 entry but found " + values.size() + " entries.", 1, values.size());
        value = values.get(0);
        Assert.assertEquals("Expected [" + value3 + "] but found [" + value + ".", value3, value);

        Assert.assertEquals("Expected 2 entries but found " + repo.getNumberOfIndexes() + ".", 2, repo.getNumberOfIndexes());

        repo.removeFromIndexes(key2);

        Assert.assertEquals("Expected 1 entries but found " + repo.getNumberOfIndexes() + ".", 1, repo.getNumberOfIndexes());

        repo.removeFromIndexes(key1, value2);
        Assert.assertEquals("Expected 1 entries but found " + repo.getNumberOfIndexes() + ".", 1, repo.getNumberOfIndexes());
        values = repo.findInIndexes(key1);
        Assert.assertNotNull("Expected a list but was null.", values);
        Assert.assertEquals("Expected 1 entry but found " + values.size() + " entries.", 1, values.size());
        value = values.get(0);
        Assert.assertEquals("Expected [" + value1 + "] but found [" + value + ".", value1, value);

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
        Assert.assertEquals("Expected 1 neurons but found", 2, neurons.size());

    }

    @Test
    public void testPersistParentNeuronWithTwoChildNeurons() {
        Neuron baum = BasicSet.getBaum();
        Neuron stamm = BasicSet.getStamm();
        Neuron blatt = BasicSet.getBlatt();
        Neuron braun = BasicSet.getBraun();
        Neuron gruen = BasicSet.getGruen();

        Assert.assertEquals("Expected empty neurons repository but wasen't.", 0, MemoryRepository.getInstance().getNumberOfNeurons());
        Assert.assertEquals("Expected empty synapses repository but wasen't.", 0, MemoryRepository.getInstance().getNumberOfSynapses());

        Synapse s1 = new Synapse(baum, Relation.HAS, stamm);
        Synapse s2 = new Synapse(baum, Relation.HAS_MANY, blatt);
        Synapse s3 = new Synapse(baum, Relation.IS, braun);
        Synapse s4 = new Synapse(baum, Relation.IS, gruen);

        repo.persist(s1);
        repo.persist(s2);
        repo.persist(s3);
        repo.persist(s4);

        Assert.assertEquals("Expected 5 indexes in repository but found " + MemoryRepository.getInstance().getNumberOfIndexes() + ".", 5,
                            MemoryRepository.getInstance().getNumberOfNeurons());
	     Assert.assertEquals("Expected 5 neurons in repository but found " + MemoryRepository.getInstance().getNumberOfNeurons() + ".", 5,
                            MemoryRepository.getInstance().getNumberOfNeurons());
        Assert.assertEquals("Expected 4 synapses in repository but found " + MemoryRepository.getInstance().getNumberOfSynapses() + ".", 4,
                            MemoryRepository.getInstance().getNumberOfSynapses());

        List<Neuron> neurons = repo.findByName(baum.getName());

        Assert.assertEquals("Expected 1 neuron but found " + neurons.size() + " neurons.", 1, neurons.size());
        Neuron expectedNeuron = neurons.get(0);
        Assert.assertEquals("Expected name='baum' but found " + expectedNeuron.getName() + ".", baum.getName(), expectedNeuron.getName());
        Assert.assertNotNull("Expected id but was null", expectedNeuron.getId());

        List<Synapse> synapses = expectedNeuron.getSynapses();
        Assert.assertEquals("Expected 4 synapses but found " + synapses.size(), 4, synapses.size());

        for (Synapse s : synapses) {
            Assert.assertNotNull("Expected Synapse but was null", s);
            Neuron n = s.getChild();
            Assert.assertNotNull("Expected child Neuron but was null.", n);
            Assert.assertTrue("Expected a value of a list but found " + n.getName(),
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
