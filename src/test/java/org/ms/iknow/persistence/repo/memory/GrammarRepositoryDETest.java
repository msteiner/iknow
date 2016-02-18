package org.ms.iknow.persistence.repo.memory;

import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;

import static org.junit.Assert.*;

public class GrammarRepositoryDETest {

    GrammarRepository repo = GrammarRepositoryDE.getInstance();

    @Before
    public void init() {
    }

    @Test
    public void testAddAndAssignGrammar() {
        try {
            repo.addExpression("Baum", WordType.SUBSTANTIVE);
        } catch (GrammarException e) {
            fail(e.getMessage());
        }

        try {
            repo.addExpression("ist", WordType.VERB);
        } catch (GrammarException e) {
            fail(e.getMessage());
        }

        try {
            repo.addExpression("gross", WordType.ADJECTIVE);
        } catch (GrammarException e) {
            fail(e.getMessage());
        }

        try {
            repo.addExpression("Test", WordType.UNKNOWN);
        } catch (GrammarException e) {
            fail(e.getMessage());
        }

        WordType wordType = null;
        try {
            wordType = repo.assignGrammar("Baum");
        } catch (GrammarException e) {
            fail("Test with [Baum] failed: Could not assign expression.");
        }
        assertEquals(WordType.SUBSTANTIVE, wordType);

        wordType = null;
        try {
            wordType = repo.assignGrammar("ist");
        } catch (GrammarException e) {
            fail("Test with [ist] failed: Could not assign expression.");
        }
        assertEquals(WordType.VERB, wordType);

        wordType = null;
        try {
            wordType = repo.assignGrammar("gross");
        } catch (GrammarException e) {
            fail("Test with [gross] failed: Could not assign expression.");
        }
        assertEquals(WordType.ADJECTIVE, wordType);

        wordType = null;
        try {
            wordType = repo.assignGrammar("Unbekannt");
        } catch (GrammarException e) {
            fail("Test with [Unbekannt] failed: Could not assign expression.");
        }
        assertEquals(WordType.UNKNOWN, wordType);
    }
}
