package org.ms.iknow.core.manager.type;

import org.ms.iknow.core.type.language.Unknown;
import org.ms.iknow.core.type.language.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Message {

    String     id;
    List<Word> expression;
    String source;

    public Message() {
        this.id = UUID.randomUUID().toString();
    }
  
    public List<Word> getAllUnknownWords() {
        List<Word> unknownWords = new ArrayList<Word>();
        for (Word word : expression) {
            if (word instanceof Unknown) {
                unknownWords.add(word);
            }
        }
        return unknownWords;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the expression
     */
    public List<Word> getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(List<Word> expression) {
        this.expression = expression;
    }
  
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }
  
    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
  
    /**
     * @return the source
     */
    public String getExpressionAsString() {
        return source;
    }
}
