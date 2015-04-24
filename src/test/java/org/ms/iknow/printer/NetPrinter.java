package org.ms.iknow.printer;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;

import java.util.ArrayList;
import java.util.List;

public class NetPrinter {

    private static final int DEFAULT_DEPTH = 5;

    private static List<String>     visited       = new ArrayList<String>();

    public static void print(Neuron neuron) {
        print("---------------------------------------------------------");
        print(neuron.getName());
      List<String> synapses = printSynapsesWithNeuron(neuron);
        for (String s : synapses) {
            print("   --- " + s);
        }
        print("---------------------------------------------------------");
    }

    public static List<String> printSynapsesWithNeuron(Neuron neuron) {
        List<String> list = new ArrayList<String>();
        for (Synapse synapse : neuron.getSynapses()) {
            print(synapse.getRelationParentChild());
            visited.add(synapse.getId());
            Neuron child = synapse.getChild();
            print(child.getName());
            for (Synapse s : child.getSynapses()) {
                if (!visited.contains(s.getId()))
                    list.add(s.getId());
                visited.add(s.getId());
            }
        }
        return list;
    }

    private static void print(Object object) {
        System.out.println(object);
    }
}
