package org.ms.iknow.core.type.sense.text;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.ms.iknow.assertion.Printer;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.crawler.ThreadController;
import org.ms.iknow.persistence.repo.MemoryRepository;
import org.ms.iknow.persistence.repo.Repository;

import java.util.Date;
import java.util.List;

public class TextTest {

    public static final String TEXT_GREEN  = "green";
    public static final String CREATE_USER = "Mark";
    public static final String CHANGE_USER = "Anna";

    private Repository         repo        = MemoryRepository.getInstance();

    @Before
    public void init() {
        MemoryRepository.getInstance().deleteAll();
    }

    /**
     * Use case before merging:
     *         /-- IST --\            /-- HAT --\
     * Stahl -             - Mutter -             - Gewinde
     *         \-- IST --/            \-- IPO --/
     * 
     *         /-- IST --\            /-- HAT --\
     * Wesen -             - Mutter -             - Kind
     *         \-- IST --/            \-- HAT --/
     * 
     */
    @Test
    public void testMergerSameNameNotSameNeurons() {
        // 1. Create 2 synapses with the same name of neuron.
        Text mutter1 = new Text("Mutter");
        Text stahl = new Text("Stahl");
        Text gewinde = new Text("Gewinde");
        Synapse s1 = new Synapse(mutter1, Relation.IS, Relation.IS, stahl);
        Synapse s2 = new Synapse(mutter1, Relation.HAS, Relation.IS_PART_OF, gewinde);
        repo.persist(s1);
        repo.persist(s2);
        System.out.println(Printer.getRelationMessages(s1));
        System.out.println(Printer.getRelationMessages(s2));

        Text mutter2 = new Text("Mutter");
        Text lebewesen = new Text("Lebewesen");
        Text kind = new Text("Kind");
        Synapse s3 = new Synapse(mutter2, Relation.IS, Relation.IS, lebewesen);
        Synapse s4 = new Synapse(mutter2, Relation.HAS, Relation.HAS, kind);
        repo.persist(s3);
        repo.persist(s4);
        System.out.println(Printer.getRelationMessages(s3));
        System.out.println(Printer.getRelationMessages(s4));

        int numberOfNeurons = MemoryRepository.getInstance().getNumberOfNeurons();
        int numberOfSynapses = MemoryRepository.getInstance().getNumberOfSynapses();
        Assert.assertEquals(6, numberOfNeurons);
        Assert.assertEquals(4, numberOfSynapses);

        // 2. Start a merger.
        ThreadController merger = new ThreadController();
        merger.startMergerThread();

        // Assert results
        List<Neuron> neurons = repo.findByName("Mutter");
        Assert.assertEquals(2, neurons.size());
      
        for (Neuron n : neurons) {
          System.out.println("__________________________________________");
          System.out.println("Found in Repo total " + MemoryRepository.getInstance().getNumberOfSynapses() + " synapses.");
            System.out.println("---" + n);
          System.out.println("   ---- Neuron has " + n.getSynapses().size() + " Synapses.");
          for (Synapse s : n.getSynapses()) {
            System.out.println("   " + s);
          }
          System.out.println("__________________________________________");
        }
      
    }

    @Test
    public void testCreateText() {
        Text text = new Text(TEXT_GREEN);
        Assert.assertNotNull(text);
        Assert.assertNotNull(text.getId());
        Assert.assertEquals(TEXT_GREEN, text.getName());
    }

    @Test
    public void testClone() {

        Text text = new Text(TEXT_GREEN);
        text.setCreateUser(CREATE_USER);
        text.setChangeUser(CHANGE_USER);
        text.setChangeDate(new Date());

        Text clone = text.clone();

        Assert.assertNotNull("Expected Text but was null.", clone);
        Assert.assertTrue("Expected " + TEXT_GREEN + " but found " + clone.getText(), TEXT_GREEN == clone.getText());
        Assert.assertNotNull("Expected ID but was null.", clone.getId());
        Assert.assertNotNull("Expected createDate but was null.", clone.getCreateDate());
        Assert.assertEquals("Expected createUser=" + CREATE_USER + " but was " + clone.getCreateUser(), CREATE_USER, clone.getCreateUser());
        Assert.assertNotNull("Expected changeDate but was null.", clone.getChangeDate());
        Assert.assertEquals("Expected changeUser=" + CHANGE_USER + " but was " + clone.getChangeUser(), CHANGE_USER, clone.getChangeUser());
        Assert.assertTrue("Expected 0 synapsesId but found " + clone.getSynapseIds().size(), 0 == clone.getSynapseIds().size());
        Assert.assertNotNull("Expected Synapse but was null.", clone.getSynapses());
        Assert.assertTrue("Expected 0 synapse but found " + clone.getSynapses().size(), 0 == clone.getSynapses().size());
    }
}
