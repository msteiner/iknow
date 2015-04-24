package org.ms.iknow.persistence.repo;

import org.ms.iknow.core.assertion.ElementValidator;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.radiation.HSB;
import org.ms.iknow.core.type.sense.text.Text;

import java.util.HashMap;
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

    /**
     * This method persists a Neuron with its synapses id's.
     * 
     *   NEURON <...> SYNAPSE <---\  /---> SYNAPSE <...> NEURON
     *                             \/
     * NEURON <...> SYNAPSE <---> NEURON <---> SYNAPSE <...> NEURON
     *                             /\
     *   NEURON <...> SYNAPSE <---/  \---> SYNAPSE <...> NEURON
     */
    @Override
    public Neuron persist(Neuron neuron) {
        checkNotNull("Expected Neuron but was null.", neuron);
        this.neurons.put(neuron.getId(), neuron);
        return neuron;
    }

    /**
     * This method persists a synapses including its parent neuron and child neuron.
     * Further levels will be ignored.
     * <br>
     * NEURON <---> SYNAPSE <---> NEURON
     */
    @Override
    public Synapse persist(Synapse synapse) {
        Synapse s = synapse;
        persist(synapse.getParent());
        persist(synapse.getChild());
        s = s.cloneSimple();
        this.synapses.put(synapse.getId(), s);
        return s;
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
  
    public void loadSynapses(Neuron neuron) {
        checkNotNull("Expected Neuron but was null.", neuron);
      
        for (String synapseId : neuron.getSynapseIds()) {
            neuron.addSynapse(synapses.get(synapseId));
        }
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
    public Synapse find(Synapse synapse) {
        String id = synapse.getId();
        return this.synapses.get(id);
    }

    @Override
    public Synapse findSynapseById(String id) {
        return synapses.get(id);
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