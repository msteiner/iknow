package org.ms.iknow.language.de;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.manager.type.Statement;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
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
        addToGrammarRepository("wirkt", RelationType.IS);
        addToGrammarRepository("gross", WordType.ADJECTIVE);
      
        Synapse synapse = parseStatement("Der Baum wirkt gross.");
        assertSynapse("Baum", RelationType.IS, "gross", synapse);
    }

    @Test
    public void testParsePartiallyKnownExpression_1() {
        addToGrammarRepository("Der", WordType.GENUS);
        addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("wirkt", RelationType.IS);
        //addToGrammarRepository("gross", WordType.ADJECTIVE);
        Synapse synapse = parseStatement("Der Baum wirkt gross.");
        assertSynapse("Baum", RelationType.IS, "gross", synapse);
        assertTrue(grammarRepo.containsVerb("wirkt"));
        assertEquals(RelationType.IS, grammarRepo.getRelation("wirkt").getType());
        assertTrue(grammarRepo.containsUndefined("gross"));
    }
  
    @Test
    public void testParsePartiallyKnownExpression_2() {
        addToGrammarRepository("Der", WordType.GENUS);
        //addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("wirkt", RelationType.IS);
        //addToGrammarRepository("gross", WordType.ADJECTIVE);
        Synapse synapse = parseStatement("Der Baum wirkt gross.");
        assertSynapse("Baum", RelationType.IS, "gross", synapse);
        assertTrue(grammarRepo.containsVerb("wirkt"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("wirkt").getType() + "].", RelationType.IS, grammarRepo.getRelation("wirkt").getType());
        assertTrue(grammarRepo.containsSubstantive("Baum"));
        assertTrue(grammarRepo.containsUndefined("gross"));
    }
    
    @Test
    public void testParsePartiallyKnownExpression_3() {
        addToGrammarRepository("Vögel", WordType.SUBSTANTIVE);
        addToGrammarRepository("sind", RelationType.IS);
        addToGrammarRepository("Tiere", WordType.SUBSTANTIVE);
        Synapse synapse = parseStatement("Vögel sind Tiere.");
        assertSynapse("Vögel", RelationType.IS, "Tiere", synapse);
        assertTrue(grammarRepo.containsVerb("sind"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("sind").getType() + "].", RelationType.IS, grammarRepo.getRelation("sind").getType());
        assertTrue(grammarRepo.containsSubstantive("Tiere"));
    }
  
    @Test
    public void testParsePartiallyKnownExpression_4() {
        //addToGrammarRepository("Vögel", WordType.SUBSTANTIVE);
        addToGrammarRepository("sind", RelationType.IS);
        //addToGrammarRepository("Tiere", WordType.SUBSTANTIVE);
        Synapse synapse = parseStatement("Vögel sind Tiere.");
        assertSynapse("Vögel", RelationType.IS, "Tiere", synapse);
        assertTrue(grammarRepo.containsUndefined("Vögel"));
        assertTrue(grammarRepo.containsVerb("sind"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("sind").getType() + "].", RelationType.IS, grammarRepo.getRelation("sind").getType());
        assertTrue(grammarRepo.containsUndefined("Tiere"));
    }
  
    @Test
    public void testParsePartiallyKnownExpression_5() {
        //addToGrammarRepository("Vögel", WordType.SUBSTANTIVE);
        //addToGrammarRepository("sind", Relation.IS);
        //addToGrammarRepository("Tiere", WordType.SUBSTANTIVE);
        Synapse synapse = parseStatement("Lorem Ipsum dolor.");
        assertSynapse("Lorem", RelationType.UNKNOWN, "dolor", synapse);
        assertTrue(grammarRepo.containsUndefined("Lorem"));
        assertTrue(grammarRepo.containsVerb("Ipsum"));
        assertEquals("Expected [Relation.UNKNOWN] but was [" + grammarRepo.getRelation("Ipsum").getType() + "].", RelationType.UNKNOWN, grammarRepo.getRelation("Ipsum").getType());
        assertTrue(grammarRepo.containsUndefined("dolor"));
    }
  
    @Test
    public void testParsePartiallyKnownExpression_6() {
        addToGrammarRepository("Bern", WordType.SUBSTANTIVE);
        addToGrammarRepository("hat", RelationType.HAS);
        addToGrammarRepository("250000", WordType.NUMERUS);
        addToGrammarRepository("Einwohner", WordType.SUBSTANTIVE);
        Synapse synapse = parseStatement("Bern hat 250000 Einwohner.");
        assertSynapse("Bern", RelationType.HAS, "Einwohner", synapse);
        assertTrue(grammarRepo.containsVerb("wirkt"));
        assertEquals("Expected [Relation.IS] but was [" + grammarRepo.getRelation("wirkt").getType() + "].", RelationType.IS, grammarRepo.getRelation("wirkt").getType());
        assertTrue(grammarRepo.containsSubstantive("Baum"));
        assertTrue(grammarRepo.containsUndefined("gross"));
    }

    private void assertSynapse(String parent, RelationType relationType, String child, Synapse synapse) {
        Neuron p = new Text(parent);
        Neuron c = new Text(child);
        Synapse expected = new Synapse(p, new Relation(relationType), c);

        assertEquals(expected.getParent().getName(), synapse.getParent().getName());
        assertEquals(expected.getChild().getName(), synapse.getChild().getName());
        assertEquals(expected.getRelation().getType(), synapse.getRelation().getType());
    }

    private Synapse parseStatement(String expression) {
        Synapse synapse = null;
        Message message = new Statement();
        message.setSource(expression);
        try {
            synapse = parser.parseExpression(message);
        } catch (GrammarException e) {
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

    private void addToGrammarRepository(String verb, RelationType relationType) {
        try {
            grammarRepo.addVerb(verb, relationType);
        } catch (GrammarException e) {
            e.printStackTrace();
        }
    }

    void assertSynapse(Synapse expected, Synapse actual) {
        assertEquals(expected.getParent().getName(), actual.getParent().getName());
        assertEquals(expected.getRelation().getType(), actual.getRelation().getType());
        assertEquals(expected.getChild().getName(), actual.getChild().getName());
    }

    List<Synapse> getExpectedList() {
        List<Synapse> expectedList = new ArrayList<Synapse>();

        expectedList.add(getSynapse("Baum", RelationType.HAS, "Ast"));
        expectedList.add(getSynapse("Baum", RelationType.HAS, "Stamm"));
        expectedList.add(getSynapse("Baum", RelationType.HAS, "Krone"));

        return expectedList;
    }

    Synapse getSynapse(String parent, RelationType relationType, String child) {
        Neuron p = new Text(parent);
        Neuron c = new Text(child);
        return new Synapse(p, new Relation(relationType), c);
    }
}