package org.ms.iknow.assertion;

import org.junit.Assert;
import org.ms.iknow.core.type.Relation;

public abstract class StandardAssertionKit {
  
    public void assertEquals(Relation expected, Relation actual) {
        assertEquals(expected.toString(), actual.toString());
    }
  
   public void assertEquals(String expected, String actual) {
        Assert.assertEquals(getMessageEquals(expected, actual), expected, actual);
    }

    public void assertNotNull(Object object) {
        Assert.assertNotNull(getMessageNotNull(), object);
    }
  
  public void assertNotNull(String text, Object object) {
        Assert.assertNotNull(getMessageNotNull(text), object);
    }
  
  public void assertTrue(int expected, int actual) {
    Assert.assertTrue(getMessageTrue(expected, actual), expected == actual);
  }

    public String getMessage(Object expected, Object actual) {
        return "Expected " + expected + " but found " + actual + ".";
    }

    public String getMessageTrue(int expected, int actual) {
        return getMessageEquals(String.valueOf(expected), String.valueOf(actual));
    }

    public String getMessageTrue(String expected, String actual) {
        return getMessageEquals(expected, actual);
    }

    public String getMessageEquals(int expected, int actual) {
        return getMessageEquals(String.valueOf(expected), String.valueOf(actual));
    }

    public String getMessageEquals(String expected, String actual) {
        return "Expected " + expected + " but found " + actual + ".";
    }
  
  public String getMessageNotNull() {
        return getMessageNotNull("an object");
    }
  
  public String getMessageNotNull(String text) {
        return "Expected " + text + " but was null.";
    }
}
