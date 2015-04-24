package org.ms.iknow.persistence.repo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.radiation.HSB;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.printer.NetPrinter;

public class RepositoryTest {

    private static Repository repo              = MemoryRepository.getInstance();

    public static final int   HUE_GREEN         = 120;
    public static final int   HUE_BROWN         = 240;

    public static final int   SATURATION_LOW    = 0;
    public static final int   SATURATION_MEDIUM = 50;
    public static final int   SATURATION_HIGH   = 100;

    public static final int   BRIGHTNESS_LOW    = 0;
    public static final int   BRIGHTNESS_MEDIUM = 50;
    public static final int   BRIGHTNESS_HIGH   = 100;

    @Before
    public void init() {
        // do nothing yet.
    }

    @Test
    public void testPersistMultiTree() {
        Text tree = new Text("tree");
        Text green = new Text("green");
        Text color = new Text("color");
        Text brown = new Text("brown");
        Text forest = new Text("forest");
        HSB hsbGreen = new HSB(HUE_GREEN, SATURATION_LOW, BRIGHTNESS_HIGH);
        HSB hsbBrown = new HSB(HUE_BROWN, SATURATION_HIGH, BRIGHTNESS_HIGH);

        Synapse greenAndHsbGreen = new Synapse(green, Relation.IS, Relation.IS, hsbGreen);
        Synapse brownAndHsbBrown = new Synapse(brown, Relation.IS, Relation.IS, hsbBrown);
        Synapse colorAndGreen = new Synapse(color, Relation.IS, Relation.IS, brown);
        Synapse colorAndBrown = new Synapse(color, Relation.IS, Relation.IS, brown);
        Synapse treeAndGreen = new Synapse(tree, Relation.IS, Relation.IS, green);
        Synapse treeAndBrown = new Synapse(tree, Relation.IS, Relation.IS, brown);
        Synapse forestAndTree = new Synapse(forest, Relation.HAS_MANY, Relation.IS_PART_OF, tree);
      System.out.println("forest has " + forest.getSynapses().size() + " synapses.");

      NetPrinter.print(forest);
    }

    @Test
    public void testPersistAndFindNeuronAsObject() {

        // create a HSB and persist it.
        Neuron hsb = createHSBNeuron();
        System.out.println("*** *** hsb1=" + hsb);
        repo.persist(hsb);

        // read the created HSB from the repository.
        Neuron persistedHSB = repo.find(hsb);
        System.out.println("*** *** hsb2=" + persistedHSB);

        // Assertions.
        assertNeuron((HSB)hsb, (HSB)persistedHSB);

    }

    @Test
    public void testPersistAndFindSynapseAsObject() {

        // create a HSB.
        Neuron hsb = createHSBNeuron();

        // create a Text.
        Neuron text = createTextNeuron();

        // create a synapse
        Synapse synapse = new Synapse(hsb, Relation.IS, text, 10);

        // persist synapse and related neurons
        synapse = repo.persist(synapse);

        // read the created synapse from the repository.
        synapse = repo.find(synapse);

        // Assertions.
        Assert.assertNotNull("Synapse is not set.", synapse);
        HSB parent = (HSB)repo.findNeuronById(synapse.getParentId());
        Text child = (Text)repo.findNeuronById(synapse.getChildId());
        Assert.assertNotNull("Parent is not set.", hsb);
        Assert.assertNotNull("Child is not set.", text);
        assertNeuron((HSB)hsb, (HSB)parent);
        assertNeuron((Text)text, (Text)child);
    }

    @Test
    public void testPersistAndFindNeuron() {
        // do nothing yet since covered by testPersistAndFindNeuronAsObject
    }

    @Test
    public void testPersistAndFindSynapse() {
        // do nothing yet since covered by testPersistAndFindSynapseAsObject
    }

    @Test
    public void testDeleteAll() {

    }

    private Neuron createHSBNeuron() {
        Neuron neuron = new HSB(120, 80, 80);
        return neuron;
    }

    private Neuron createTextNeuron() {
        Neuron neuron = new Text("Rot");
        return neuron;
    }

    private void assertNeuron(HSB expected, HSB actual) {
        // ID
        Assert.assertNotNull("ID is not set.", actual.getId());
        // CreateDate
        Assert.assertNotNull("CreateDate is not set.", actual.getCreateDate());
        // Hue
        Assert.assertEquals(getMessage(expected.getHue(), actual.getHue()), expected.getHue(), actual.getHue());
        // Saturation
        Assert.assertEquals(getMessage(expected.getSaturation(), actual.getSaturation()), expected.getSaturation(), actual.getSaturation());
        // Brightness
        Assert.assertEquals(getMessage(expected.getBrightness(), actual.getBrightness()), expected.getBrightness(), actual.getBrightness());
    }

    private void assertNeuron(Text expected, Text actual) {
        // ID
        Assert.assertNotNull("ID is not set.", actual.getId());
        // CreateDate
        Assert.assertNotNull("CreateDate is not set.", actual.getCreateDate());
        // Text
        Assert.assertEquals(getMessage(expected.getText(), actual.getText()), expected.getText(), actual.getText());
    }

    private String getMessage(Object expected, Object actual) {
        return "Expected " + expected + " but found " + actual + ".";
    }
}
