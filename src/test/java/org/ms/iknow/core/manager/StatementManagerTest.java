package org.ms.iknow.core.manager;

import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.manager.type.Answer;
import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.manager.type.Question;
import org.ms.iknow.core.manager.type.Statement;

import static org.junit.Assert.*;

public class StatementManagerTest {

    StatementManager manager;

    @Before
    public void init() {
        manager = new StatementManager();
    }

    @Test
    public void testExecute() {

    }

    @Test
    public void testMessageTypePositive() {

        String statement_1 = "Das Haus ist gross.";
        String statement_2 = "Das Haus sieht gross aus.";
        String question_1 = "Ist das Haus gross?";
        String question_2 = "Was ist ein Haus?";
        String answer_1 = "Ja, das Haus ist gross.";
        String answer_2 = "Ein Haus ist ein Bauwerk.";

        testStatement(statement_1, null);
        testStatement(statement_2, null);
        testQuestion(question_1, null);
        testQuestion(question_2, null);
        testAnswer(answer_1, "001");
        testAnswer(answer_2, "002");
    }

    @Test
    public void testMessageTypeNegative() {

        String question_1 = "Ist das Haus gross?";
        String answer_1 = "Ja, das Haus ist gross.";
        try {
            manager.defineMessageType(question_1, "999");
            fail("Expression [" + question_1 + "] with questionId [999] should fail but didn't.");
        } catch (Exception e) {
            // Everything's fine...
        }
      
        // Recognized as a statement since questionId is null.
        testStatement(answer_1, null);
    }

    void testStatement(String expression, String questionId) {
        Message message = manager.defineMessageType(expression, questionId);
        String messageExpected = "Expected type Statement but was " + message + ".";
        assertTrue(messageExpected, message instanceof Statement);
    }

    void testQuestion(String expression, String questionId) {
        Message message = manager.defineMessageType(expression, questionId);
        String messageExpected = "Expected type Question but was " + message + ".";
        assertTrue(messageExpected, message instanceof Question);
    }

    void testAnswer(String expression, String questionId) {
        Message message = manager.defineMessageType(expression, questionId);
        String messageExpected = "Expected type Answer but was " + message + ".";
        assertTrue(messageExpected, message instanceof Answer);
    }
}
