package org.ms.iknow.core.manager;

import org.ms.iknow.core.manager.type.Answer;
import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.manager.type.Question;
import org.ms.iknow.core.manager.type.Statement;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.language.Word;
import org.ms.iknow.exception.BusinessException;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.LanguageParser;
import org.ms.iknow.language.LanguageParserFactory;
import org.ms.iknow.persistence.repo.GrammarRepository;
import org.ms.iknow.persistence.repo.Repository;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;

import java.util.List;

public class StatementManager {

    Repository        repository        = MemoryRepository.getInstance();
    GrammarRepository grammarRepository = GrammarRepositoryDE.getInstance();
    LanguageParser    languageParser    = LanguageParserFactory.getParser(LanguageParserFactory.LANGUAGE_DEFAULT);
    String            currentQuestion;
    String            currentAnswer;

    /**
     * Verarbeitung einer Antwort auf eine von IKnow gestellte Frage.
     * 
     * @param expression Das Statement oder die Frage als expression.
     */
    public void execute(String expression) throws BusinessException {

        executeStatement(expression, null);
    }

    /**
     * Verarbeitung einer Antwort auf eine von IKnow gestellte Frage.
     * 
     * @param expression Die Antwort als expression.
     * @param questionId die Id der zuvor gestellten Frage.
     */
    public void execute(String expression, String questionId) throws BusinessException {

        executeStatement(expression, questionId);
    }

    private void executeStatement(String expression, String questionId) throws BusinessException {
        // First we have to check wether it's a question or a statement or an answer.
        Message message = defineMessageType(expression, questionId);

        // Second we need to parse the message for verbs, substantivs, ........
        Synapse synapse = null;
        try {
            synapse = languageParser.parseExpression(message);
        } catch (GrammarException e) {
            throw new BusinessException(e.getMessage());
        }
        repository.persist(synapse);
    }

    protected void processStatement(Statement statement) {
		repository.persist(new Synapse());
    }

    protected Message defineMessageType(String expression, String questionId) {
        Message message;
        if (!endsWithQuestionMark(expression) && !isSet(questionId)) {
            message = new Statement();
        } else if (endsWithQuestionMark(expression) && !isSet(questionId)) {
            message = new Question();
        } else if (!endsWithQuestionMark(expression) && isSet(questionId)) {
            message = new Answer();
        } else {
            throw new RuntimeException("Expression [" + expression + "] mit questionId [" + questionId
                                       + "] konnte nicht als Statement, Question oder Answer erkannt werden.");
        }
        message.setSource(expression);
        return message;
    }

    boolean endsWithQuestionMark(String expression) {
        if (expression.endsWith("?")) {
            return true;
        } else {
            return false;
        }
    }

    boolean isSet(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        return true;
    }
}
