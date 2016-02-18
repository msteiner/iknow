package org.ms.iknow.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class OpenThesaurusClient {
  
    public void getThesaurusList(String url) {
    
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
      
        //Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        if (clientResponse.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + clientResponse.getStatus());
        }
        //String output = response.getEntity(String.class);
        SynonymResponse response = clientResponse.getEntity(SynonymResponse.class);

      
        System.out.println("Output from Server .... \n");
        System.out.println(response);
    }
} 