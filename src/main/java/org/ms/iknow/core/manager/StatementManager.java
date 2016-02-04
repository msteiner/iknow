package org.ms.iknow.core.manager;

import org.ms.iknow.core.manager.type.Answer;
import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.manager.type.Question;
import org.ms.iknow.core.manager.type.Statement;
import org.ms.iknow.core.type.language.Word;
import org.ms.iknow.language.LanguageParser;
import org.ms.iknow.language.LanguageParserFactory;
import org.ms.iknow.persistence.repo.GrammarRepository;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;

import java.util.List;

public class StatementManager {
  
    GrammarRepository grammarRepository = GrammarRepositoryDE.getInstance();
    LanguageParser languageParser = LanguageParserFactory.getParser(LanguageParserFactory.LANGUAGE_DEFAULT);
    String            currentQuestion;
    String            currentAnswer;

    public void execute(String expression, String questionId) {

        // First we have to check wether it's a question or a statement or an answer.
        Message message = defineMessageType(expression, questionId);

        // Second we need to parse the message for verbs, substantivs, ........
        List<Word> wordList = languageParser.parseExpression(expression);

        message.setExpression(wordList);
      
        // Der Satz sollte 1 Verb enthalten ("Baum IST schön"). Wir versuchen nun, 
        // dieses auf einen Operator zu mappen: 
        // "Baum ERSCHEINT schön" zu "Baum IST schön"
        // "Baum BESITZT Stamm" zu "Baum HAT Stamm".

        if (message instanceof Statement) {

        } else if (message instanceof Answer) {

        } else {
          // Should not be possible. Do nothing yet. 
        }
    }
  
    protected Message defineMessageType(String expression, String questionId) {
        // Statement
        if (!endsWithQuestionMark(expression) && !isSet(questionId)) {
            Statement statement = new Statement();
            return statement;
        } else if (endsWithQuestionMark(expression) && !isSet(questionId)) {
            Question question = new Question();
            return question;
        } else if (!endsWithQuestionMark(expression) && isSet(questionId)) {
            Answer answer = new Answer();
            return answer;
        } else {
            throw new RuntimeException("Expression [" + expression + "] mit questionId [" + questionId + "] konnte nicht als Statement, Question oder Answer erkannt werden.");
        }
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
