package org.ms.iknow.persistence.repo.memory;

import org.ms.iknow.core.type.question.Question;

public class QuestionEntry extends Entry {

    Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
