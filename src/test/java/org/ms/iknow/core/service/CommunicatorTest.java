package org.ms.iknow.core.service;

import org.junit.Assert;
import org.junit.Test;
import org.ms.iknow.core.type.sense.radiation.HSB;

public class CommunicatorTest {
  
  @Test
  public void testCreateSynapses() {
    
    Communicator communicator = new Communicator();
    
    HSB hSB = new HSB(220, 80, 90);
    
    hSB = (HSB) communicator.create(hSB);
    
    Assert.assertNotNull(hSB);
    Assert.assertTrue(220 == hSB.getHue());
    Assert.assertTrue(80 == hSB.getSaturation());
    Assert.assertTrue(90 == hSB.getBrightness());
  }
}
