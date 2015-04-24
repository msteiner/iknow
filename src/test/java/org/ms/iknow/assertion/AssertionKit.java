package org.ms.iknow.assertion;

import org.junit.Assert;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.radiation.HSB;
import org.ms.iknow.core.type.sense.text.Text;

public abstract class AssertionKit extends StandardAssertionKit {

    public void assertNeuron(HSB expected, HSB actual) {
        // ID
        Assert.assertNotNull(getMessageNotNull(), actual.getId());
        // CreateDate
        Assert.assertNotNull(getMessageNotNull(), actual.getCreateDate());
        // Hue
        Assert.assertEquals(getMessageEquals(expected.getHue(), actual.getHue()), expected.getHue(), actual.getHue());
        // Saturation
        Assert.assertEquals(getMessageEquals(expected.getSaturation(), actual.getSaturation()), expected.getSaturation(),
                            actual.getSaturation());
        // Brightness
        Assert.assertEquals(getMessageEquals(expected.getBrightness(), actual.getBrightness()), expected.getBrightness(),
                            actual.getBrightness());
    }

    public void assertNeuron(Text expected, Text actual) {
        assertNotNull(expected);
        // ID
        assertNotNull(actual.getId());
        // CreateDate
        assertNotNull(actual.getCreateDate());
        // Text
        assertEquals(expected.getText(), actual.getText());
    }

    public void assertNumberOfElements(int expected, int actual) {
        Assert.assertTrue(getMessageTrue(expected, actual), expected == actual);
    }

    public void assertTextNeuron(Text text, String textvalue) {
        Assert.assertNotNull(getMessageNotNull(), text);
        Assert.assertNotNull(getMessageNotNull(), text.getId());
        Assert.assertEquals(getMessageEquals(textvalue, text.getText()), textvalue, text.getText());
    }
  
    public void assertSynapse(Synapse expected, Synapse actual) {
        assertNotNull(actual);
        assertEquals(expected.getParentId(), actual.getParentId());
        assertEquals(expected.getChildId(), actual.getChildId());
        assertEquals(expected.getRelation(), actual.getRelation());
    }
}
