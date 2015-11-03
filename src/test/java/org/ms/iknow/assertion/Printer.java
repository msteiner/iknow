package org.ms.iknow.assertion;

import org.ms.iknow.core.type.Synapse;

public class Printer {
  
  public static String getRelationMessages(Synapse synapse) {
    return getRelationMessageParentToChild(synapse) + "\n" + getRelationMessageChildToParent(synapse);
  }

    public static String getRelationMessageParentToChild(Synapse synapse) {
        StringBuilder builder = new StringBuilder();
        builder.append(synapse.getParent().getName());
        builder.append(" ");
        builder.append(synapse.getRelationParentChild().getDescription());
        builder.append(" ");
        builder.append(synapse.getChild().getName());

        return builder.toString();
    }

    public static String getRelationMessageChildToParent(Synapse synapse) {
        StringBuilder builder = new StringBuilder();
        builder.append(synapse.getChild().getName());
        builder.append(" ");
        builder.append(synapse.getRelationChildParent().getDescription());
        builder.append(" ");
        builder.append(synapse.getParent().getName());

        return builder.toString();
    }
}
