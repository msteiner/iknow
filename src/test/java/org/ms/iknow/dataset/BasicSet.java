package org.ms.iknow.dataset;

import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.radiation.HSB;
import org.ms.iknow.core.type.sense.text.Text;

public class BasicSet {

    public static final int    HUE_GREEN         = 120;
    public static final int    HUE_BROWN         = 240;

    public static final int    SATURATION_LOW    = 0;
    public static final int    SATURATION_MEDIUM = 50;
    public static final int    SATURATION_HIGH   = 100;

    public static final int    BRIGHTNESS_LOW    = 0;
    public static final int    BRIGHTNESS_MEDIUM = 50;
    public static final int    BRIGHTNESS_HIGH   = 100;

    public static final String NEURON_BAUM       = "Baum";
    public static final String NEURON_STAMM      = "Stamm";
    public static final String NEURON_BLATT      = "Blatt";


    public static Text getBaum() {
        Text t = new Text(NEURON_BAUM);
        return t;
    }

    public static Text getStamm() {
        Text t = new Text(NEURON_STAMM);
        return t;
    }

    public static Text getBlatt() {
        Text t = new Text(NEURON_BLATT);
        return t;
    }

    public static HSB getGruen() {
        return new HSB(HUE_GREEN, SATURATION_MEDIUM, BRIGHTNESS_HIGH);
    }

    public static HSB getBraun() {
        return new HSB(HUE_BROWN, SATURATION_MEDIUM, BRIGHTNESS_MEDIUM);
    }

    public static Synapse getBaumHatStamm() {
        return new Synapse(getBaum(), new Relation(RelationType.HAS), getStamm());
    }

    public static Synapse getBaumHatBlatt() {
        return new Synapse(getBaum(), new Relation(RelationType.HAS_MANY), getBlatt());
    }

    public static Synapse getBaumIstGruen() {
        return new Synapse(getBaum(), new Relation(RelationType.IS), getGruen());
    }

    public static Synapse getBaumIstBraun() {
        return new Synapse(getBaum(), new Relation(RelationType.IS), getBraun());
    }
}