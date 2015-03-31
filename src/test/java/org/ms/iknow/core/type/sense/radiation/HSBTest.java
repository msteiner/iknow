package org.ms.iknow.core.type.sense.radiation;

import org.junit.Assert;
import org.junit.Test;

public class HSBTest {
  
  public static final int RED = 0;
  public static final int GREEN = 120;
  public static final int BLUE = 240;
  
  public static final int SATURATION_LOW = 0;
  public static final int SATURATION_MEDIUM = 50;
  public static final int SATURATION_HIGH = 100;
  
  public static final int BRIGHTNESS_LOW = 0;
  public static final int BRIGHTNESS_MEDIUM = 50;
  public static final int BRIGHTNESS_HIGH = 100;
  
  @Test
  public void testCreateHSB() {
    HSB hsb = new HSB(GREEN, SATURATION_MEDIUM, BRIGHTNESS_HIGH);
    Assert.assertNotNull(hsb);
  }
}
