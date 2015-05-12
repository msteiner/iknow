package org.ms.iknow.persistence.repo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.radiation.HSB;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.persistence.repo.MemoryRepository;

public class RepositoryTest {

    private static MemoryRepository repo              = MemoryRepository.getInstance();

    public static final int         HUE_GREEN         = 120;
    public static final int         HUE_BROWN         = 240;

    public static final int         SATURATION_LOW    = 0;
    public static final int         SATURATION_MEDIUM = 50;
    public static final int         SATURATION_HIGH   = 100;

    public static final int         BRIGHTNESS_LOW    = 0;
    public static final int         BRIGHTNESS_MEDIUM = 50;
    public static final int         BRIGHTNESS_HIGH   = 100;

    @Before
    public void init() {
        repo.deleteAll();
    }

    @Test
    public void testPersistAndFindNeuron() {
        Text expected = new Text("tree");
        repo.persist(expected);
        Text actual = (Text)repo.find(expected);
        assertNeuron(expected, actual);
    }

    @Test
    public void testPersistAndFindStillExistingNeuron() {
        Text expected = new Text("tree");
        repo.persist(expected);
        repo.persist(expected); // Write the same neuron again.
        int nrOfNeurons = repo.getNumberOfNeurons();
        Assert.assertTrue("Expected 1 Neuron but found " + nrOfNeurons, 1 == nrOfNeurons);
        Text actual = (Text)repo.find(expected);
        assertNeuron(expected, actual);
    }

    @Test
    public void testPersistAndFindTwoNeuronsWithDifferentInstances() {
        Text expected1 = new Text("tree");
        Text expected2 = new Text("tree");
        repo.persist(expected1);
        repo.persist(expected2);
        int nrOfNeurons = repo.getNumberOfNeurons();
        Assert.assertTrue("Expected 2 Neurons but found " + nrOfNeurons, 2 == nrOfNeurons);
        Text actual1 = (Text)repo.find(expected1);
        Text actual2 = (Text)repo.find(expected2);
        assertNeuron(expected1, actual1);
        assertNeuron(expected2, actual2);
    }

    @Test
    public void testPersistAndFindSynapse() {
        Text expected1 = new Text("tree");
        Text expected2 = new Text("forest");
        Synapse expectedSynapse = new Synapse(expected2, Relation.HAS_MANY, Relation.IS_PART_OF, expected1);
        repo.persist(expectedSynapse);

        Synapse actualSynapse = repo.findSynapseById(expectedSynapse.getId());

        String expectedParentChild = getRelationMessageParentToChild(expectedSynapse);
        String expectedChildParent = getRelationMessageChildToParent(expectedSynapse);
        String actualParentChild = getRelationMessageParentToChild(actualSynapse);
        String actualChildParent = getRelationMessageChildToParent(actualSynapse);

        Assert.assertEquals("Expected " + expectedParentChild + " but found " + actualParentChild + ".", expectedParentChild,
                            actualParentChild);
        Assert.assertEquals("Expected " + expectedChildParent + " but found " + actualChildParent + ".", expectedChildParent,
                            actualChildParent);
    }

    @Test
    public void testPersistAndFindExistingSynapseWithExistingNeurons() {
        Text expected1 = new Text("tree");
        Text expected2 = new Text("forest");
        Synapse expectedSynapse = new Synapse(expected2, Relation.HAS_MANY, Relation.IS_PART_OF, expected1);
        repo.persist(expectedSynapse);
        repo.persist(expectedSynapse); // Write the same things again.

        Synapse actualSynapse = repo.findSynapseById(expectedSynapse.getId());

        String expectedParentChild = getRelationMessageParentToChild(expectedSynapse);
        String expectedChildParent = getRelationMessageChildToParent(expectedSynapse);
        String actualParentChild = getRelationMessageParentToChild(actualSynapse);
        String actualChildParent = getRelationMessageChildToParent(actualSynapse);

        Assert.assertEquals("Expected " + expectedParentChild + " but found " + actualParentChild + ".", expectedParentChild,
                            actualParentChild);
        Assert.assertEquals("Expected " + expectedChildParent + " but found " + actualChildParent + ".", expectedChildParent,
                            actualChildParent);
    }

    @Test
    public void testPersistAndFindNeuronAsObject() {

        // create a HSB and persist it.
        Neuron hsb = createHSBNeuron();
        repo.persist(hsb);

        // read the created HSB from the repository.
        Neuron persistedHSB = repo.find(hsb);

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

    private String getRelationMessageParentToChild(Synapse synapse) {
        StringBuilder builder = new StringBuilder();
        builder.append(synapse.getParent().getName());
        builder.append(" ");
        builder.append(synapse.getRelationParentChild().getDescription());
        builder.append(" ");
        builder.append(synapse.getChild().getName());

        return builder.toString();
    }

    private String getRelationMessageChildToParent(Synapse synapse) {
        StringBuilder builder = new StringBuilder();
        builder.append(synapse.getChild().getName());
        builder.append(" ");
        builder.append(synapse.getRelationChildParent().getDescription());
        builder.append(" ");
        builder.append(synapse.getParent().getName());

        return builder.toString();
    }
}
