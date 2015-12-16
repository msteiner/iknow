package org.ms.iknow.core.type.sense.text;

import org.ms.iknow.core.type.Neuron;

/**
 * This class is part of the package sense. Though Text is not a sense 
 * but a information which is received via radiation it is used as a 
 * helper for IKnow. It enables the use of automated input. Also it's 
 * used for several things like transform audio into an alphanumeric 
 * String value.
 */
public class Text extends Neuron {

    private String text;

    public Text() {
        super();
    }

    public Text(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getName() {
        if (this.name == null) {
          if (this.text.length() > 0) {
            return text.substring(0, text.length());
          }
            else {
              return "unknown name";
            }
        }
        return this.name;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n" + this.getClass().getName() + "\n");
        builder.append(super.toString() + "\n");
        builder.append("  name = " + getName() + "\n");
        builder.append("  text = " + text + "\n");

        return builder.toString();
    }

    @Override
    public Text clone() {
        Text text = new Text(this.text);
        text = (Text) cloneNeuron(text);
        return text;
    }
}
