package org.ms.iknow.core.type;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron extends Iterator {
  
    protected String      name;
    private List<Synapse> synapses   = new ArrayList<Synapse>();
    private List<String>  synapseIds = new ArrayList<String>();
  
    public abstract String getName();

    @Override
    public Synapse nextSynapse() {
        increaseCurrentIndex();
        return synapses.get(currentIndex);
    }

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

    @Override
    public boolean hasMoreElements() {
        if (this.getSynapses().size() > currentIndex) {
          return true;
        }
        return false;
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