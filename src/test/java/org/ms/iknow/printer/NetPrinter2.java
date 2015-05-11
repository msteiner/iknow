package org.ms.iknow.printer;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;

import java.util.List;

public class NetPrinter2 {

    private Neuron           parent;
    private static final int MAX_COUNT   = 100;
    private static final int INDENT_STEP = 4;
    private int              counter;
    private int              indent;

    public void print(Neuron neuron) {
        print("---------------------------------------------------------");
        parent = neuron;
        neuron.resetIndex();
        counter = 0;
        indent = 0;
        print(neuron.getName());

        printSynapses(neuron.getSynapses());

        print("---------------------------------------------------------");
    }

    public void printSynapses(List<Synapse> synapses) {
        for (Synapse s : synapses) {
            if (!s.isVisited()) {
                printSynapseWithNeuron(s);
            }
        }
        parent.setVisited(true);
    }

    public void printSynapseWithNeuron(Synapse synapse) {
        counter++;
        if (counter > MAX_COUNT) {
            return;
        }
        Neuron child = synapse.getChild();
        Neuron parent = synapse.getParent();
        synapse.setVisited(true);
        child.setVisited(false);
        print(getIndent(indent) + parent.getName() + " " + synapse.getRelationParentChild().getDescription() + " " + child.getName());
        printSynapses(child.getSynapses());
    }

    private void print(Object object) {
        System.out.println(object);
    }

    private String getIndent(int indent) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            s.append(" ");
        }
        return s.toString();
    }
}
