package org.ms.iknow.core.type;

import static org.junit.Assert.*;
import org.junit.Test;
import org.ms.iknow.core.type.sense.text.Text;

public class SynapseTest {
  
  @Test
  public void testCreateSynapse() {
    Text baum = new Text("Baum");
    Text ast = new Text("Ast");
    Text blatt = new Text("Blatt");
    Relation hasMany = Relation.HAS_MANY;
    Synapse synapseAst = new Synapse(baum, hasMany, ast);
    Synapse synapseBlatt_1 = new Synapse(baum, hasMany, blatt);
    
    assertNotNull("Expected parent but was null.", synapseAst.getParent());
    assertEquals("Expected parent [Baum] but found [" + synapseAst.getParent().getName() + "].", "Baum", synapseAst.getParent().getName());
    assertNotNull("Expected parent child but was null.", synapseAst.getChild());
    assertEquals("Expected child [Ast] but found [" + synapseAst.getChild().getName() + "].", "Ast", synapseAst.getChild().getName());
    
    assertNotNull("Expected parent but was null.", synapseBlatt_1.getParent());
    assertEquals("Expected parent [Baum] but found [" + synapseBlatt_1.getParent().getName() + "].", "Baum", synapseBlatt_1.getParent().getName());
    assertNotNull("Expected parent child but was null.", synapseBlatt_1.getChild());
    assertEquals("Expected child [Blatt] but found [" + synapseBlatt_1.getChild().getName() + "].", "Blatt", synapseBlatt_1.getChild().getName());
    
    assertEquals("Expected 2 synapseIds but found " + baum.getSynapseIds().size() + ".", 2, baum.getSynapseIds().size());
    assertEquals("Expected 2 synapses but found " + baum.getSynapses().size() + ".", 2, baum.getSynapses().size());
    
    Synapse synapseBlatt_2 = new Synapse(baum, hasMany, blatt);
    
    assertNotNull("Expected parent but was null.", synapseBlatt_2.getParent());
    assertEquals("Expected parent [Baum] but found [" + synapseBlatt_2.getParent().getName() + "].", "Baum", synapseBlatt_2.getParent().getName());
    assertNotNull("Expected parent child but was null.", synapseBlatt_2.getChild());
    assertEquals("Expected child [Blatt] but found [" + synapseBlatt_2.getChild().getName() + "].", "Blatt", synapseBlatt_2.getChild().getName());
    
    assertEquals("Expected 3 synapseIds but found " + baum.getSynapseIds().size() + ".", 3, baum.getSynapseIds().size());
    assertEquals("Expected 3 synapses but found " + baum.getSynapses().size() + ".", 3, baum.getSynapses().size());    
  }
}
