package org.ms.iknow.core.assertion;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Synapse;

public abstract class ElementValidator {
  
    public void checkNotNull(Neuron neuron) {
        checkNotNull("Expected Neuron but was null.", neuron);
    }

    public void checkNotNull(Synapse synapse) {
        checkNotNull("Expected Synapse but was null.", synapse);
    }

    public void checkNotNull(String message, Object object) {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }
}
