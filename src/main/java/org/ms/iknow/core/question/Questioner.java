package org.ms.iknow.core.question;

import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.question.Answer;
import org.ms.iknow.core.type.question.Question;
import org.ms.iknow.language.LanguageParser;
import org.ms.iknow.language.LanguageParserFactory;
import org.ms.iknow.persistence.repo.Repository;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;

import java.util.List;
import java.util.Locale;

public class Questioner {
  
  Repository repo = MemoryRepository.getInstance();
  LanguageParser languageParser = LanguageParserFactory.getParser(Locale.GERMAN);
  
  public void createQuestion(Question question) {
    repo.createQuestion(question);
  }
  
  public List<Question> getQuestions() {    
    return repo.getQuestions();
  }
  
  public void setAnswers(Answer answer) {
    // TODO implement
  }
  
  public void setAnswers(String questionId, String userId, String message) {
    List<Synapse> synapses = languageParser.parseStatement(message);
    repo.persist(synapses);
  }
}
