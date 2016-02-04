package org.ms.iknow.core.manager.type;

public class Answer extends Message {

    String questionId;

    /**
     * @return the questionId
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
