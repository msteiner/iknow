package org.ms.iknow.core.type.sense.radiation;

import org.ms.iknow.core.type.Neuron;

/**
 * This class represents the HSV color model. 
 * http://de.wikipedia.org/wiki/HSV-Farbraum
 */
public class HSB extends Neuron {
  
  private Integer hue;
  private Integer saturation;
  private Integer brightness;
  
  public HSB () {
    super();
  }
  
  public HSB (Integer hue, Integer saturation, Integer brightness) {
    super();
    this.hue = hue;
    this.saturation = saturation;
    this.brightness = brightness;
  }
  
  public Integer getHue () {
    return hue;
  }
  
  public void setHue (Integer hue) {
    this.hue = hue;
  }
  
  public Integer getSaturation () {
    return saturation;
  }
  
  public void setSaturation (Integer saturation) {
    this.saturation = saturation;
  }
  
  public Integer getBrightness () {
    return brightness;
  }
  
  public void setBrightness (Integer brightness) {
    this.brightness = brightness;
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    builder.append("\n" + this.getClass().getName() + "\n");
    builder.append(super.toString() + "\n");
    builder.append("  hue = " + hue + "\n");
    builder.append("  saturation = " + saturation + "\n");
    builder.append("  brightness = " + brightness + "\n");
    
    return builder.toString();
  }
}