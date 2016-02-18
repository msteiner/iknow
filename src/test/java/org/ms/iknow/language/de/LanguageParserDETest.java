package org.ms.iknow.language.de;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.manager.type.Statement;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.LanguageParser;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;
import org.ms.iknow.persistence.repo.Repository;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;

import java.util.ArrayList;
import java.util.List;

public class LanguageParserDETest {

    public static final String STATEMENT_1 = "Baum has Ast";
    public static final String STATEMENT_2 = "Baum has Stamm";
    public static final String STATEMENT_3 = "             Baum          has   Krone     ";
    public static final String QUESTION_1  = "has Baum Ast?";

    public LanguageParser      parser;
    GrammarRepository          grammarRepo = GrammarRepositoryDE.getInstance();
    Repository                 repository  = MemoryRepository.getInstance();

    @Before
    public void init() {
        parser = new LanguageParserDE();
        grammarRepo.deleteAll();
        repository.deleteAll();
    }

    @Deprecated
    @Test
    public void testParseStatement() {
        String statement = STATEMENT_1 + "," + STATEMENT_2 + ",          " + STATEMENT_3;
        List<Synapse> results = parser.parseStatement(statement);
        assertSynapse(getExpectedList().get(0), results.get(0));
        assertSynapse(getExpectedList().get(1), results.get(1));
        assertSynapse(getExpectedList().get(2), results.get(2));
    }

    @Test
    public void testParseKnownExpression() {
        addToGrammarRepository("Der", WordType.GENUS);
        addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("wirkt", Relation.IS);
        addToGrammarRepository("gross", WordType.ADJECTIVE);
      
        Synapse synapse = parseStatement("Der Baum wirkt gross.");
        assertSynapse("Baum", Relation.IS, "gross", synapse);
    }

    @Test
    public void testParsePartiallyKnownExpression_1() {
        addToGrammarRepository("Der", WordType.GENUS);
        addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("wirkt", Relation.IS);
        //addToGrammarRepository("gross", WordType.ADJECTIVE);
        Synapse synapse = parseStatement("Der Baum wirkt gross.");
        assertSynapse("Baum", Relation.IS, "gross", synapse);
        assertTrue(grammarRepo.containsVerb("wirkt"));
        assertEquals(Relation.IS, grammarRepo.getRelation("wirkt"));
        assertTrue(grammarRepo.containsUndefined("gross"));
    }
  
    @Test
    public void testParsePartiallyKnownExpression_2() {
        addToGrammarRepository("Der", WordType.GENUS);
        //addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("wirkt", Relation.IS);
        //addToGrammarRepository("gross", WordType.ADJECTIVE);
        Synapse synapse = parseStatement("Der Baum wirkt gross.");
        assertSynapse("Baum", Relation.IS, "gross", synapse);
        assertTrue(grammarRepo.containsVerb("wirkt"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("wirkt") + "].", Relation.IS, grammarRepo.getRelation("wirkt"));
        assertTrue(grammarRepo.containsSubstantive("Baum"));
        assertTrue(grammarRepo.containsUndefined("gross"));
    }
    
    @Test
    public void testParsePartiallyKnownExpression_3() {
        addToGrammarRepository("Vögel", WordType.SUBSTANTIVE);
        addToGrammarRepository("sind", Relation.IS);
        addToGrammarRepository("Tiere", WordType.SUBSTANTIVE);
        Synapse synapse = parseStatement("Vögel sind Tiere.");
        assertSynapse("Vögel", Relation.IS, "Tiere", synapse);
        assertTrue(grammarRepo.containsVerb("sind"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("sind") + "].", Relation.IS, grammarRepo.getRelation("sind"));
        assertTrue(grammarRepo.containsSubstantive("Tiere"));
    }
  
    @Test
    public void testParsePartiallyKnownExpression_4() {
        //addToGrammarRepository("Vögel", WordType.SUBSTANTIVE);
        addToGrammarRepository("sind", Relation.IS);
        //addToGrammarRepository("Tiere", WordType.SUBSTANTIVE);
        Synapse synapse = parseStatement("Vögel sind Tiere.");
        assertSynapse("Vögel", Relation.IS, "Tiere", synapse);
        assertTrue(grammarRepo.containsUndefined("Vögel"));
        assertTrue(grammarRepo.containsVerb("sind"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("sind") + "].", Relation.IS, grammarRepo.getRelation("sind"));
        assertTrue(grammarRepo.containsUndefined("Tiere"));
    }
  
    @Test
    public void testParsePartiallyKnownExpression_5() {
        //addToGrammarRepository("Vögel", WordType.SUBSTANTIVE);
        addToGrammarRepository("sind", Relation.IS);
        //addToGrammarRepository("Tiere", WordType.SUBSTANTIVE);
        Synapse synapse = parseStatement("Lorem Ipsum dolor.");
        assertSynapse("Vögel", Relation.IS, "Loren", synapse);
        assertTrue(grammarRepo.containsUndefined("Vögel"));
        assertTrue(grammarRepo.containsVerb("sind"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("sind") + "].", Relation.IS, grammarRepo.getRelation("sind"));
        assertTrue(grammarRepo.containsUndefined("Tiere"));
    }

    private void assertSynapse(String parent, Relation relation, String child, Synapse synapse) {
        Neuron p = new Text(parent);
        Neuron c = new Text(child);
        Synapse expected = new Synapse(p, relation, c);

        assertEquals(expected.getParent().getName(), synapse.getParent().getName());
        assertEquals(expected.getChild().getName(), synapse.getChild().getName());
        assertEquals(expected.getRelation(), synapse.getRelation());
    }

    private Synapse parseStatement(String expression) {
        Synapse synapse = null;
        Message message = new Statement();
        message.setSource(expression);
        try {
            synapse = parser.parseExpression(message);
        } catch (GrammarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return synapse;
    }

    private void addToGrammarRepository(String expression, WordType wordType) {
        try {
            grammarRepo.addExpression(expression, wordType);
        } catch (GrammarException e) {
            e.printStackTrace();
        }
    }

    private void addToGrammarRepository(String verb, Relation relation) {
        try {
            grammarRepo.addVerb(verb, relation);
        } catch (GrammarException e) {
            e.printStackTrace();
        }
    }

    void assertSynapse(Synapse expected, Synapse actual) {
        assertEquals(expected.getParent().getName(), actual.getParent().getName());
        assertEquals(expected.getRelation(), actual.getRelation());
        assertEquals(expected.getChild().getName(), actual.getChild().getName());
    }

    List<Synapse> getExpectedList() {
        List<Synapse> expectedList = new ArrayList<Synapse>();

        expectedList.add(getSynapse("Baum", Relation.HAS, "Ast"));
        expectedList.add(getSynapse("Baum", Relation.HAS, "Stamm"));
        expectedList.add(getSynapse("Baum", Relation.HAS, "Krone"));

        return expectedList;
    }

    Synapse getSynapse(String parent, Relation relation, String child) {
        Neuron p = new Text(parent);
        Neuron c = new Text(child);
        return new Synapse(p, relation, c);
    }
}
