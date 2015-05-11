package org.ms.iknow.printer;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;

public class NetPrinter {

    public void printStatement(Neuron neuron) {

    }

    public void print(Neuron neuron) {
        println("---------------------------------------------------------\n");

        println(createTitle(neuron.getName()));
        for (Synapse synapse : neuron.getSynapses()) {
            println(createStatement(synapse));
        }

        println("---------------------------------------------------------");
    }

    private String createTitle(String title) {
        StringBuilder builder = new StringBuilder();

        builder.append(title);
        builder.append(CHAR_LINE_BREAK);
        for (int i = 0; i < title.length(); i++) {
            builder.append(CHAR_LINE);
        }

        return builder.toString();
    }

    private String createStatement(Synapse synapse) {
        StringBuilder builder = new StringBuilder();

        String parentName = synapse.getParent().getName();
        builder.append(parentName);
        builder.append(CHAR_SPACE);
        builder.append(synapse.getRelationChildParent());
        builder.append(CHAR_SPACE);
        builder.append(synapse.getChild().getName());

        return builder.toString();
    }

    private void println(Object object) {
        System.out.println(object);
    }

    public static final String CHAR_SPACE      = " ";
    public static final String CHAR_LINE_BREAK = "\n";
    public static final String CHAR_LINE       = "-";
}
