package org.ms.iknow.core.type;

import org.junit.Assert;
import org.junit.Test;

public class RelationTest {
  
  public static final String ID_EXPECTED = "4";

    @Test
    public void testGetRelation() {
      Relation relation = Relation.HAS_MANY;
      String id = relation.getId();
      Assert.assertEquals(ID_EXPECTED, id);
      Assert.assertEquals(relation, Relation.getRelation(id));
    }
}
