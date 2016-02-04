package org.ms.iknow.service.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ms.iknow.core.manager.StatementManager;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;
import org.ms.iknow.service.type.StatementEntry;

import java.util.ArrayList;
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
  
    // This method is called if TEXT_PLAIN is request
    @GET
    @Path("{statement}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStatement(@PathParam("statement") String statement) {
        new StatementManager().execute(statement, null);
        Text neuron = new Text(statement);
        MemoryRepository.getInstance().persist(neuron);

        // read for response
        List<Neuron> neurons = MemoryRepository.getInstance().findByName(statement);
        return Response.ok(neurons).build();
    }

    // This method is called if TEXT_PLAIN is request
    @GET
    @Path("{entity1}/{relation}/{entity2}")
    @Produces("application/json")
    public Response createStatement(@PathParam("entity1") String entity1,
                              @PathParam("relation") String relation,
                              @PathParam("entity2") String entity2) {
        Text parent = new Text(entity1);
        Text child = new Text(entity2);
        Relation r = Relation.getRelationById(relation);
        Synapse synapse = new Synapse(parent, r, child);
        MemoryRepository.getInstance().persist(synapse);

        List<Neuron> neurons = MemoryRepository.getInstance().findByName(entity1);
        List<StatementEntry> entries = new ArrayList<StatementEntry>();
        StatementEntry entry = null;
        for (Neuron n : neurons) {
            for (Synapse s : n.getSynapses()) {
                entry = new StatementEntry();
                entry.setParentName(n.getName());
                entry.setRelationId(s.getRelation().getId());
                entry.setChildName(s.getChild().getName());
                entries.add(entry);
            }
        }
      return Response.status(200).entity(entries).build();
    }
  
    List<StatementEntry> toList(List<Neuron> neurons) {
        List<StatementEntry> table = new ArrayList<StatementEntry>();
        StatementEntry row = null;
        for (Neuron parent : neurons) {
            for (Synapse synapse : parent.getSynapses()) {
                row = new StatementEntry();
                row.setParentName(parent.getName());
                row.setRelationId(synapse.getRelation().getValue());
                row.setChildName(synapse.getChild().getName());
                table.add(row);
            }
        }
        return table;
    }
  
    JSONArray toJSONArray(List<Neuron> neurons) {
        JSONArray table = new JSONArray();
        JSONArray row = null;
        JSONObject entry = null;
        for (Neuron parent : neurons) {
            for (Synapse synapse : parent.getSynapses()) {
                row = new JSONArray();
                entry = new JSONObject()
                  .put("parent", parent.getName())
                  .put("relation", synapse.getRelation().getValue())
                  .put("child", synapse.getChild().getName());
                row.put(entry);
            }
        }
        return table;
    }
}
