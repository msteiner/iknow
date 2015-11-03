package org.ms.iknow.persistence.repo;

import org.ms.iknow.core.assertion.ElementValidator;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.radiation.HSB;
import org.ms.iknow.core.type.sense.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryRepository extends ElementValidator implements Repository {

    private static MemoryRepository instance;
    private Map<String, Neuron> neurons = new HashMap<String, Neuron>();
    private Map<String, Synapse> synapses = new HashMap<String, Synapse>();

    protected MemoryRepository() {
        // Exists only to defeat instantiation.
    }

    public static MemoryRepository getInstance() {
        if (instance == null) {
            instance = new MemoryRepository();
        }
        return instance;
    }

    @Override
    public Neuron persist(Neuron neuron) {
        checkNotNull("Expected Neuron but was null.", neuron);
        this.neurons.put(neuron.getId(), neuron);
        return neuron;
    }

    @Override
    public Synapse persist(Synapse synapse) {
        Synapse s = synapse;
        persist(synapse.getParent());
        persist(synapse.getChild());
        s = s.cloneSimple();
        this.synapses.put(synapse.getId(), s);
        return s;
    }
  
    @Override
    public List<Synapse> persist(List<Synapse> synapses) {
        for (Synapse synapse : synapses) {
          persist(synapse);
        }
        return synapses;
    }
  
    /**
     * This method can be used to find a neuron. <br>
     * Supported types are <br>
     * <ul>
     * <li> {@link HSB} </li>
     * <li> {@link Text} </li>
     * </ul>
     * <br><br>
     * Good to know: <br>
     * <ul>
     * <li> A find will return the exact matching value. </li>
     * <li> A find will return the first matching element. </li>
     * </ul>
     */
    @Override
    public Neuron find(Neuron neuron) {
        for (Map.Entry<String, Neuron> entry : neurons.entrySet()) {
            if (neuron instanceof Text) {
                if (entry.getValue() != null && entry.getValue() instanceof Text) {
                    Text text = (Text)entry.getValue();
                    if (isEqualsValue((Text)neuron, text)) {
                        loadSynapses(text);
                        return text;
                    }
                }
            } else if (neuron instanceof HSB) {
                if (entry.getValue() != null && entry.getValue() instanceof HSB) {
                    HSB hsb = (HSB)entry.getValue();
                    if (isEqualsValue((HSB)neuron, hsb)) {
                        loadSynapses(hsb);
                        return hsb;
                    }
                }
            } else {
                throw new RuntimeException("Neuron [" + neuron.getClass() + "] is not supported.");
            }
        }
        return null;
    }
  
  public void loadSynapses(List<Neuron> neurons) {
    for (Neuron n : neurons) {
      loadSynapses(n);
    }
  }
  
    public void loadSynapses(Neuron neuron) {
        checkNotNull("Expected Neuron but was null.", neuron);
      
        for (String synapseId : neuron.getSynapseIds()) {
            neuron.addSynapse(synapses.get(synapseId));
        }
    }
  
    public List<Synapse> getSynapses(List<String> synapseIds) {
        checkNotNull("Expected Neuron but was null.", synapseIds);
        List<Synapse> list = new ArrayList<Synapse>();
        for (String synapseId : synapseIds) {
            list.add(synapses.get(synapseId));
        }
        return list;
    }

    private boolean isEqualsValue(HSB expected, HSB actual) {
        if (expected.getHue() == actual.getHue() && expected.getSaturation() == actual.getSaturation()
            && expected.getBrightness() == actual.getBrightness()) {
            return true;
        }
        return false;
    }

    private boolean isEqualsValue(Text expected, Text actual) {
        if (expected.getText() == actual.getText()) {
            return true;
        }
        return false;
    }

    @Override
    public Neuron findNeuronById(String id) {
        return neurons.get(id);
    }
  
    @Override
    public List<Neuron> findByName(String name) {
        List<Neuron> list = new ArrayList<Neuron>();
        for (Map.Entry<String, Neuron> entry : neurons.entrySet()) {
            Neuron n = (Neuron)entry.getValue();
            if (name.equals(n.getName())) {
              List<Synapse> synapses = getSynapses(n.getSynapseIds());
              System.out.println("+++ found " + getNumberOfSynapses() + " synapses in repo.");
              System.out.println("+++ found " + synapses.size() + " synapses.");
              for (Synapse syn : synapses) {
               System.out.println(syn);
              }
             for (Synapse s : synapses) {
               if (s != null) {
                 n.addSynapse(s);
               }
               
             }
                list.add(n);
            }
        }
      System.out.println("+++ " + list.size() + " Neurons found in response.");
        // TODO find a way to add synapses
        return list;
    }

    @Override
    public Synapse find(Synapse synapse) {
        Synapse s = this.synapses.get(synapse.getId());
        s.setParent(findNeuronById(s.getParentId()));
        s.setChild(findNeuronById(s.getChildId()));
        return s;
    }

    @Override
    public Synapse findSynapseById(String id) {
        Synapse s = this.synapses.get(id);
        s.setParent(findNeuronById(s.getParentId()));
        s.setChild(findNeuronById(s.getChildId()));
        return s;
    }

    public int getNumberOfNeurons() {
        return neurons.size();
    }

    public int getNumberOfSynapses() {
        return synapses.size();
    }

    @Override
    public void deleteAll() {
        neurons.clear();
        synapses.clear();
    }

    public void printAllNeurons() {
        for (Map.Entry<String, Neuron> entry : neurons.entrySet()) {
            String key = entry.getKey();
            Neuron value = entry.getValue();
            System.out.println("... " + key + ":" + value);
        }
    }

    public void printAllSynapses() {
        for (Map.Entry<String, Synapse> entry : synapses.entrySet()) {
            String key = entry.getKey();
            Synapse value = entry.getValue();
            System.out.println("... " + key + ":" + value);
        }
    }
}