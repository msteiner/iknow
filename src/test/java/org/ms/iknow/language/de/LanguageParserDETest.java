package org.ms.iknow.language.de;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.language.Adjectiv;
import org.ms.iknow.core.type.language.Genus;
import org.ms.iknow.core.type.language.Numerus;
import org.ms.iknow.core.type.language.Substantiv;
import org.ms.iknow.core.type.language.Verb;
import org.ms.iknow.core.type.language.Word;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.LanguageParser;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;

import java.util.ArrayList;
import java.util.List;

public class LanguageParserDETest {

    public static final String STATEMENT_1 = "Baum has Ast";
    public static final String STATEMENT_2 = "Baum has Stamm";
    public static final String STATEMENT_3 = "             Baum          has   Krone     ";
    public static final String QUESTION_1  = "has Baum Ast?";

    public LanguageParser      parser;
    GrammarRepository          repo        = GrammarRepositoryDE.getInstance();

    @Before
    public void init() {
        parser = new LanguageParserDE();
        repo.deleteAll();
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
    public void testParseExpressionPositive() {

        addToGrammarRepository("Der", WordType.GENUS);
        addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("ist", WordType.VERB);
        addToGrammarRepository("braun", WordType.ADJECTIVE);

        String expression;
        List<Word> words;
        expression = "Der Baum ist braun";
        words = parser.parseExpression(expression);
        assertNotNull("Expected 4 Words but was null.", words);
        assertEquals("Expected 4 Words but found " + words.size() + ".", 4, words.size());

        assertWord(words.get(0), "Der", WordType.GENUS);
        assertWord(words.get(1), "Baum", WordType.SUBSTANTIVE);
        assertWord(words.get(2), "ist", WordType.VERB);
        assertWord(words.get(3), "braun", WordType.ADJECTIVE);
    }

    @Test
    public void testParseExpressionWithMarks() {

        addToGrammarRepository("Der", WordType.GENUS);
        addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("ist", WordType.VERB);
        addToGrammarRepository("braun", WordType.ADJECTIVE);

        String expression;
        List<Word> words;
        expression = "Der Baum ist braun.";
        words = parser.parseExpression(expression);
        assertNotNull("Expected 4 Words but was null.", words);
        assertEquals("Expected 4 Words but found " + words.size() + ".", 4, words.size());

        assertWord(words.get(0), "Der", WordType.GENUS);
        assertWord(words.get(1), "Baum", WordType.SUBSTANTIVE);
        assertWord(words.get(2), "ist", WordType.VERB);
        assertWord(words.get(3), "braun", WordType.ADJECTIVE);
    }


    @Test
    public void testParseExpressionWithUnknownWords() {

        addToGrammarRepository("Der", WordType.GENUS);
        addToGrammarRepository("Baum", WordType.SUBSTANTIVE);
        addToGrammarRepository("ist", WordType.VERB);
        addToGrammarRepository("braun", WordType.ADJECTIVE);

        String expression;
        List<Word> words;
        expression = "Der Baum erscheint gross";
        words = parser.parseExpression(expression);
        assertNotNull("Expected 4 Words but was null.", words);
        assertEquals("Expected 4 Words but found " + words.size() + ".", 4, words.size());

        assertWord(words.get(0), "Der", WordType.GENUS);
        assertWord(words.get(1), "Baum", WordType.SUBSTANTIVE);
        assertWord(words.get(2), "erscheint", WordType.UNKNOWN);
        assertWord(words.get(3), "gross", WordType.UNKNOWN);

        System.out.println("+++ [" + words.get(0).getExpression() + "] ist ein " + getWordType(words.get(0)) + ".");
        System.out.println("+++ [" + words.get(1).getExpression() + "] ist ein " + getWordType(words.get(1)) + ".");
        System.out.println("+++ [" + words.get(2).getExpression() + "] ist ein " + getWordType(words.get(2)) + ".");
        System.out.println("+++ [" + words.get(3).getExpression() + "] ist ein " + getWordType(words.get(3)) + ".");
    }

    private void assertWord(Word word, String expected, WordType wordTypeExpected) {
        assertNotNull("Expected Word but was null.", word);
        assertNotNull("Expected expression but was null.", word.getExpression());
        assertEquals("Expected " + word.getExpression() + " for expression " + wordTypeExpected + " but found " + word.getExpression()
                     + ".", expected, word.getExpression());
        assertEquals("Expected " + wordTypeExpected + " for expression " + word.getExpression() + " but found " + getWordType(word) + ".",
                     wordTypeExpected, getWordType(word));
    }

    private void addToGrammarRepository(String expression, WordType wordType) {
        try {
            repo.addExpression(expression, wordType);
        } catch (GrammarException e) {
            e.printStackTrace();
        }
    }

    private WordType getWordType(Word word) {
        WordType wordType = null;
        if (word instanceof Adjectiv) {
            wordType = WordType.ADJECTIVE;
        } else if (word instanceof Genus) {
            wordType = WordType.GENUS;
        } else if (word instanceof Numerus) {
            wordType = WordType.ADJECTIVE;
        } else if (word instanceof Substantiv) {
            wordType = WordType.SUBSTANTIVE;
        } else if (word instanceof Verb) {
            wordType = WordType.VERB;
        } else {
            wordType = WordType.UNKNOWN;
        }
        return wordType;
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
