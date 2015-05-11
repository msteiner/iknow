package org.ms.iknow.core.usecase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.assertion.AssertionKit;
import org.ms.iknow.core.service.Communicator;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.radiation.HSB;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.persistence.repo.MemoryRepository;
import org.ms.iknow.persistence.repo.Repository;

/**
 * UseCase: Create and assert a simple assumption.
 */
@Deprecated
public class CreateAssumptionTest extends AssertionKit {

    private Communicator       communicator      = null;
    private String             message           = "";
    private Repository         repository        = MemoryRepository.getInstance();

    public static final String TEXT_1            = "Rot";
    public static final int    HUE_RED           = 0;
    public static final int    HUE_GREEN         = 120;
    public static final int    HUE_BLUE          = 240;

    public static final int    SATURATION_LOW    = 0;
    public static final int    SATURATION_MEDIUM = 50;
    public static final int    SATURATION_HIGH   = 100;

    public static final int    BRIGHTNESS_LOW    = 0;
    public static final int    BRIGHTNESS_MEDIUM = 50;
    public static final int    BRIGHTNESS_HIGH   = 100;

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
    public void testCreateSimpleAssignedAssumption() {

        // Create and assert a HSB Neuron
        HSB hSB = new HSB(HUE_GREEN, SATURATION_MEDIUM, BRIGHTNESS_HIGH);
        hSB = (HSB)communicator.create(hSB);

        Assert.assertNotNull(hSB);
        Assert.assertNotNull(hSB.getId());
        Assert.assertTrue(HUE_GREEN == hSB.getHue());
        Assert.assertTrue(SATURATION_MEDIUM == hSB.getSaturation());
        Assert.assertTrue(BRIGHTNESS_HIGH == hSB.getBrightness());

        // Create and assert a Text neuron.
        Text text = new Text(TEXT_1);
        text = (Text)communicator.create(text);

        Assert.assertNotNull(text);
        Assert.assertNotNull(text.getId());
        Assert.assertEquals(TEXT_1, text.getText());

        // Create a synapse with a relation to both neurons
        Synapse synapse = new Synapse(hSB, Relation.IS, text, 10);
        synapse = communicator.create(synapse);

        //System.out.println("Neuron 1 = " + hSB);
        //System.out.println("Neuron 2 = " + text);
        //System.out.println("Synapse 1 = " + synapse);

        //System.out.println(MemoryRepository.getInstance().getNumberOfNeurons() + " neuron(s) found.");
        //System.out.println(MemoryRepository.getInstance().getNumberOfSynapses() + " synapse(s) found.");

        assertNumberOfElements(2, MemoryRepository.getInstance().getNumberOfNeurons());
        assertNumberOfElements(1, MemoryRepository.getInstance().getNumberOfSynapses());
    }

    /**
     * A simple assigned assumption means 2 neurons and
     * a synapsis. This test covers the creation only.
     */
    @Test
    public void testCreateAndReadSimpleAssumption() {

        // Create and assert a Text neuron.
        Text text = new Text(TEXT_1);
        text = (Text)communicator.create(text);

        Assert.assertNotNull(text);
        Assert.assertNotNull(text.getId());
        Assert.assertEquals(TEXT_1, text.getText());

        //System.out.println("Neuron = " + text);
        //System.out.println("Neuron hashcode = " + text.hashCode());

        //System.out.println(MemoryRepository.getInstance().getNumberOfNeurons() + " neuron(s) found.");
        //System.out.println(MemoryRepository.getInstance().getNumberOfSynapses() + " synapse(s) found.");

        assertNumberOfElements(1, MemoryRepository.getInstance().getNumberOfNeurons());
        assertNumberOfElements(0, MemoryRepository.getInstance().getNumberOfSynapses());

        Neuron result = repository.find(text);
        //System.out.println("Found Neuron = " + result);
        assertNeuron(text, (Text)result);

    }

    public void assertNumberOfElements(int expected, int actual) {
        message = "Expected " + expected + " entr(y/ies) but found " + actual + ".";
        Assert.assertTrue(message, expected == actual);
    }
}
