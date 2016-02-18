package org.ms.iknow.client;

import org.junit.Before;
import org.junit.Test;

/**
 * URL mit allen Angaben:
 * https://www.openthesaurus.de/synonyme/search?q=besitzen&format=application/json&mode=all
 */
public class OpenThesaurusClientTest {
  
  OpenThesaurusClient client = null;
  String url;
  
    @Before
    public void init() {
        client = new OpenThesaurusClient();
    }

    @Test
    public void testGetThesaurusList() {
        url = "https://www.openthesaurus.de/synonyme/search?q=test&format=application/json";
        client.getThesaurusList(url);
    }
}
