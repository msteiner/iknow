package org.ms.iknow.client;

import org.junit.Before;
import org.junit.Test;

public class OpenThesaurusClientTest {
  
  OpenThesaurusClient client = null;
  
    @Before
    public void init() {
        client = new OpenThesaurusClient();
    }

    @Test
    public void testGetThesaurusList() {
        String url = "https://www.openthesaurus.de/synonyme/search?q=test&format=application/json";
        client.getThesaurusList(url);
    }
}
