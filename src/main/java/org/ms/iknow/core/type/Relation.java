package org.ms.iknow.core.type;

import org.ms.iknow.exception.BusinessException;

public class Relation {

    RelationType type;
    String       value;

    private Relation() {
        // hide this constructor
    }

    public Relation(RelationType type) {
        this.type = type;
    }

    public Relation(RelationType type, String value) throws BusinessException {
        if (type.equals(RelationType.HAS)) {
            this.type = type;
            this.value = value;
        } else {
            throw new BusinessException("Ein Value kann nur für den RelationType [" + type + "] gesetzt werden.");
        }
    }

    /**
     * @return the type
     */
    public RelationType getType() {
        return type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("type :" + type + "\n");
      builder.append("value:" + value);
      return builder.toString();
    }
}