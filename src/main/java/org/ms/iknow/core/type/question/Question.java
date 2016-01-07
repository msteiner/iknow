package org.ms.iknow.core.type.question;

import org.ms.iknow.core.type.ElementBase;
import org.ms.iknow.core.type.Relation;

public class Question extends ElementBase {

    protected String   parentId;
    protected String   parentName;
    protected Relation relation;
    protected String   childId;
    protected String   childName;
    
    public Question() {
        super();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("Could not clone Question: " + this.toString());
        }
    }
  
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(super.toString());
        builder.append("     parentId   = " + id + "\n");
        builder.append("     parentName = " + parentName + "\n");
        builder.append("     relation   = " + relation + "\n");
        builder.append("     childId    = " + childId + "\n");
        builder.append("     childName  = " + childName + "\n");

        return builder.toString();
    }
}
