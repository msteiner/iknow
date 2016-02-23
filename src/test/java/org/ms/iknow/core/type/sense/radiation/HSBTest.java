package org.ms.iknow.core.type.sense.radiation;

import org.junit.Assert;
import org.junit.Test;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;

import java.util.Date;

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
  
  public static final String CREATE_USER = "Mark";
  public static final String CHANGE_USER = "Anna";
  
  public static final String TEXT_VALUE = "Leaf";
  
  
  @Test
  public void testCreateHSB() {
    HSB hsb = new HSB(GREEN, SATURATION_MEDIUM, BRIGHTNESS_HIGH);
    Assert.assertNotNull(hsb);
  }
  
  @Test
  public void testClone() {
    
    HSB hsb = new HSB(GREEN, SATURATION_MEDIUM, BRIGHTNESS_HIGH);
    hsb.setCreateUser(CREATE_USER);
    hsb.setChangeUser(CHANGE_USER);
    hsb.setChangeDate(new Date());
    
    Text text = new Text(TEXT_VALUE);
    
    new Synapse(hsb, new Relation(RelationType.IS), text);
      
    HSB clone = hsb.clone();
    
    Assert.assertNotNull("Expected HSB but was null.", clone);
    Assert.assertTrue("Expected " + GREEN + " but found " + clone.getHue(), 
                      GREEN == clone.getHue());
    Assert.assertTrue("Expected " + SATURATION_MEDIUM + " but found " + clone.getSaturation(), 
                      SATURATION_MEDIUM == clone.getSaturation());
    Assert.assertTrue("Expected " + BRIGHTNESS_HIGH + " but found " + clone.getBrightness(), 
                      BRIGHTNESS_HIGH == clone.getBrightness());
    Assert.assertNotNull("Expected ID but was null.", clone.getId());
    Assert.assertNotNull("Expected createDate but was null.", clone.getCreateDate());
    Assert.assertEquals("Expected createUser=" + CREATE_USER + " but was " + clone.getCreateUser(),
                       CREATE_USER, clone.getCreateUser());
    Assert.assertNotNull("Expected changeDate but was null.", clone.getChangeDate());
    Assert.assertEquals("Expected changeUser=" + CHANGE_USER + " but was " + clone.getChangeUser(),
                       CHANGE_USER, clone.getChangeUser());
    Assert.assertTrue("Expected 1 synapsesId but found " + clone.getSynapseIds().size(),
                     1 == clone.getSynapseIds().size());
    Assert.assertNotNull("Expected Synapse but was null.", clone.getSynapses());
    Assert.assertTrue("Expected 0 synapse but found " + clone.getSynapses().size(),
                     0 == clone.getSynapses().size());
  }
}
