package org.ms.iknow.core.type;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron extends ElementBase {
  
    protected String      name;
    private List<Synapse> synapses   = new ArrayList<Synapse>();
    private List<String>  synapseIds = new ArrayList<String>();
  
    public abstract String getName();

    public void setName(String name) {
        this.name = name;
        setChangeDate();
    }

    public List<Synapse> getSynapses() {
        return synapses;
    }

    public List<String> getSynapseIds() {
        return synapseIds;
    }

    public void addSynapse(Synapse synapse) {
        //if (!synapseIds.contains(synapse.getId())) {
        //    synapseIds.add(synapse.getId());
        //}
        synapseIds.add(synapse.getId());
        synapses.add(synapse);
        setChangeDate();
    }
  
    public void removeAllSynapses() {
        this.synapses.clear();
    }

    public abstract Neuron clone();

    protected Neuron cloneNeuron(Neuron neuron) {
        neuron.name = this.name;
        for (String sid : this.synapseIds) {
            neuron.getSynapseIds().add(sid);
        }
        neuron = (Neuron) cloneElementBase(neuron);

        return neuron;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(super.toString());
        for (String id : this.synapseIds) {
          builder.append("     synapseId = " + id + "\n");
        }
        
        return builder.toString();
    }
}