package org.ms.iknow.persistence.repo.memory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.core.type.question.Question;

import java.util.List;
import java.util.UUID;

public class RepositoryQuestionTest {

    private static MemoryRepository repo = MemoryRepository.getInstance();

    @Before
    public void init() {
        repo.deleteAll();
    }

    @Test
    public void testCreateListDeleteQuestion() {

        // create 3 questions.
        repo.createQuestion(createQuestion("Baum", RelationType.HAS, "Stamm"));
        repo.createQuestion(createQuestion("Himmel", RelationType.IS, "Blau"));
        repo.createQuestion(createQuestion("Eisen", RelationType.IS, "organisch"));
        String key2 = null;

        // assert the 3 questions.
        List<Question> questions = repo.getQuestions();
        assertEquals("Expected 3 Questions but was " + questions.size(), 3, questions.size());

        for (Question q : questions) {
            assertNotNull("Expected createDate but was null. ", q.getCreateDate());
            assertNotNull(q.getId());
            String parentName = q.getParentName();
            if (q.getParentName().equals("Baum")) {
                assertEquals("Expected child [Stamm] but was " + q.getChildName(), "Stamm", q.getChildName());
            } else if (q.getParentName().equals("Himmel")) {
                assertEquals("Expected child [Blau] but was " + q.getChildName(), "Blau", q.getChildName());
                key2 = q.getId();
            } else if (q.getParentName().equals("Eisen")) {
                assertEquals("Expected child [organisch] but was " + q.getChildName(), "organisch", q.getChildName());
            } else {
                fail("Expected [Baum] or [Himmel] or [Eisen] but found " + parentName);
            }
        }

        // delete 1 question.
        repo.deleteQuestion(key2);

        // assert the remaining 2 questions.
        questions = repo.getQuestions();
        assertEquals("Expected 2 Questions but was " + questions.size(), 2, questions.size());

        for (Question q : questions) {
            assertNotNull("Expected createDate but was null. ", q.getCreateDate());
            assertNotNull(q.getId());
            String parentName = q.getParentName();
            if (q.getParentName().equals("Baum")) {
                assertEquals("Expected child [Stamm] but was " + q.getChildName(), "Stamm", q.getChildName());
            } else if (q.getParentName().equals("Eisen")) {
                assertEquals("Expected child [organisch] but was " + q.getChildName(), "organisch", q.getChildName());
            } else {
                fail("Expected [Baum] or [Himmel] or [Eisen] but found " + parentName);
            }
        }
    }

    Question createQuestion(String parent, RelationType relationType, String child) {
        Question question = new Question();
        question.setParentId(UUID.randomUUID().toString());
        question.setParentName(parent);
        question.setRelation(new Relation(relationType));
        question.setChildId(UUID.randomUUID().toString());
        question.setChildName(child);
        return question;
    }
}
