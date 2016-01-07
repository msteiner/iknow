package org.ms.iknow.core.type.question;

import org.ms.iknow.core.type.ElementBase;
import org.ms.iknow.core.type.Relation;

import java.util.ArrayList;
import java.util.List;

public class Answer extends ElementBase {

    List<AnswerEntry> entries = new ArrayList<AnswerEntry>();

    public List<AnswerEntry> getAnswerEntries() {
        return entries;
    }

    public void addAnswerEntry(Relation relation, String childId, String childName) {
        entries.add(new AnswerEntry(relation, childId, childName));
    }

    class AnswerEntry {
        Relation relation;
        String   childId;
        String   childName;

        public AnswerEntry(Relation relation, String childId, String childName) {
            this.relation = relation;
            this.childId = childId;
            this.childName = childName;
        }

        public Relation getRelation() {
            return relation;
        }

        public String getChildId() {
            return childId;
        }

        public String getChildName() {
            return childName;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append("        relation   = " + relation + "\n");
            builder.append("        childId    = " + childId + "\n");
            builder.append("        childName  = " + childName + "\n");

            return builder.toString();
        }
    }
}
