package org.ms.iknow.service.type;

public class StatementEntry {

    String parentName;
    String relationId;
    String childName;

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getChildName() {
        return this.childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
    
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\n");
      sb.append("parent  =" + this.parentName + "\n");
      sb.append("relation=" + this.relationId + "\n");
      sb.append("child   =" + this.childName);
      
      return sb.toString();
    }
}
