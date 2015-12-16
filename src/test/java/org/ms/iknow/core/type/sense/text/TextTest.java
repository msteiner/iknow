package org.ms.iknow.core.type.sense.text;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class TextTest {

    public static final String TEXT_GREEN  = "green";
    public static final String CREATE_USER = "Mark";
    public static final String CHANGE_USER = "Anna";

    @Test
    public void testCreateText() {
        Text text = new Text(TEXT_GREEN);
        Assert.assertNotNull(text);
        Assert.assertNotNull(text.getId());
        Assert.assertEquals(TEXT_GREEN, text.getName());
    }

    @Test
    public void testClone() {

        Text text = new Text(TEXT_GREEN);
        text.setCreateUser(CREATE_USER);
        text.setChangeUser(CHANGE_USER);
        text.setChangeDate(new Date());

        Text clone = text.clone();

        Assert.assertNotNull("Expected Text but was null.", clone);
        Assert.assertTrue("Expected " + TEXT_GREEN + " but found " + clone.getText(), TEXT_GREEN == clone.getText());
        Assert.assertNotNull("Expected ID but was null.", clone.getId());
        Assert.assertNotNull("Expected createDate but was null.", clone.getCreateDate());
        Assert.assertEquals("Expected createUser=" + CREATE_USER + " but was " + clone.getCreateUser(), CREATE_USER, clone.getCreateUser());
        Assert.assertNotNull("Expected changeDate but was null.", clone.getChangeDate());
        Assert.assertEquals("Expected changeUser=" + CHANGE_USER + " but was " + clone.getChangeUser(), CHANGE_USER, clone.getChangeUser());
        Assert.assertTrue("Expected 0 synapsesId but found " + clone.getSynapseIds().size(), 0 == clone.getSynapseIds().size());
        Assert.assertNotNull("Expected Synapse but was null.", clone.getSynapses());
        Assert.assertTrue("Expected 0 synapse but found " + clone.getSynapses().size(), 0 == clone.getSynapses().size());
    }
}
