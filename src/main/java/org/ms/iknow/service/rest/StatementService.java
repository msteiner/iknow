package org.ms.iknow.service.rest;

import org.ms.iknow.core.service.CoreStatementService;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

/**
 * http://crunchify.com/how-to-build-restful-service-with-java-using-jax-rs-and-jersey/
 */
@Path("/Statement")
public class StatementService {

    private CoreStatementService service;

    // This method is called if TEXT_PLAIN is request
    @GET
    @Path("{entity1}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response statement(@PathParam("entity1") String entity1) {
        Text neuron = new Text(entity1);
        service = new CoreStatementService();
        service.persist(neuron);

        // read for response
        List<Neuron> neurons = service.findByName(entity1);
        return Response.ok(neurons).build();
    }

    // This method is called if TEXT_PLAIN is request
    @GET
    @Path("{entity1}/{relation}/{entity2}")
    @Produces("application/json")
    public Response statement(@PathParam("entity1") String entity1,
                              @PathParam("relation") String relation,
                              @PathParam("entity2") String entity2) {
        service = new CoreStatementService();
        Text neuron1 = new Text(entity1);
        Text neuron2 = new Text(entity2);
        Relation r = Relation.getRelation(relation);
        Synapse synapse = new Synapse(neuron1, r, neuron2);
        service.persist(synapse);

        // read for response
        List<Neuron> neurons = service.findByName(entity1);
      System.out.println("   +++ " + neurons.size() + " neurons found: " + neurons.get(0));
      
      return Response.ok(neurons).build();
    }
}
