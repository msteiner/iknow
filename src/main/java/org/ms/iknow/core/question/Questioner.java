package org.ms.iknow.core.question;

import org.ms.iknow.core.type.question.Answer;
import org.ms.iknow.core.type.question.Question;
import org.ms.iknow.persistence.repo.Repository;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;

import java.util.List;

public class Questioner {
  
  Repository repo = MemoryRepository.getInstance();
  
  public void createQuestion(Question question) {
    repo.createQuestion(question);
  }
  
  public List<Question> getQuestions() {    
    return repo.getQuestions();
  }
  
  public void setAnswers(Answer answer) {
    // TODO implement
  }
  
  public void setAnswers(String questionId, String userId, String statements) {
    // TODO implement
  }
}
