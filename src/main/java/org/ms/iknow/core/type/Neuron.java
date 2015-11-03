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
        synapseIds.add(synapse.getId());        
        synapses.add(synapse);
        setChangeDate();
    }

    public abstract Neuron clone();

    protected void cloneNeuron(Neuron neuron) {
        neuron.name = this.name;
        neuron.synapseIds = this.synapseIds;
        neuron.synapses = this.synapses;
        cloneElementBase(neuron);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(super.toString());
        return builder.toString();
    }
}