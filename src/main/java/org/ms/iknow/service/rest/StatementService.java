package org.ms.iknow.service.rest;

import org.json.JSONObject;
import org.ms.iknow.core.service.CoreStatementService;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.sense.text.Text;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * http://crunchify.com/how-to-build-restful-service-with-java-using-jax-rs-and-jersey/
 */
@Path("/Statement")
public class StatementService {

    private CoreStatementService service;

    // This method is called if TEXT_PLAIN is request
    @GET
    @Path("{entity1}")
    @Produces("application/json")
    public Response statement(@PathParam("entity1") String entity1) {

        // write neuron
        Text neuron = new Text(entity1);
        service = new CoreStatementService();
        service.persist(neuron);

        // read for response
        List<Neuron> neurons = service.findByName(entity1);
        JSONObject entries = new JSONObject();
        for (Neuron n : neurons) {
            JSONObject entry = createJsonTextNeuron((Text)n);
            entries.put(n.getId(), entry);
        }

      // create response
        String result = "@Produces(\"application/json\") Output: \n\ntextNeuron Output: \n\n" + entries;
        return Response.status(200).entity(result).build();
    }

    // This method is called if TEXT_PLAIN is request
    @GET
    @Path("{entity1}/{relation}/{entity2}")
    @Produces("application/json")
    public Response statement(@PathParam("entity1") String entity1,
                              @PathParam("relation") String relation,
                              @PathParam("entity2") String entity2) {
        String statement = "+++ " + entity1 + " " + relation + " " + entity2;
        JSONObject neurons = new JSONObject();
        Text arve = createDummyTextNeuron("Arve");
        JSONObject jsonArve = new JSONObject();
        jsonArve = createJsonDummyTextNeuron(jsonArve, arve);
        Text erle = createDummyTextNeuron("Erle");
        JSONObject jsonErle = new JSONObject();
        jsonErle = createJsonDummyTextNeuron(jsonErle, erle);

        neurons.put("statement", statement);
        neurons.put("arve", jsonArve);
        neurons.put("erle", jsonErle);

        String result = "@Produces(\"application/json\") Output: \n\ntextNeuron Output: \n\n" + neurons;
        return Response.status(200).entity(result).build();
    }

    /**
     * @return
     */
    private JSONObject createJsonTextNeuron(Text neuron) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("changeDate", neuron.getChangeDate());
        jsonObject.put("changeUser", neuron.getChangeUser());
        jsonObject.put("createDate", neuron.getCreateDate());
        jsonObject.put("createUser", neuron.getCreateUser());
        jsonObject.put("index", neuron.getIndex());
        jsonObject.put("name", neuron.getName());
        jsonObject.put("visited", neuron.isVisited());
        jsonObject.put("text", neuron.getText());
        return jsonObject;
    }

    /**
     * @return
     */
    private JSONObject createJsonDummyTextNeuron(JSONObject jsonObject, Text neuron) {
        jsonObject.put("changeDate", neuron.getChangeDate());
        jsonObject.put("changeUser", neuron.getChangeUser());
        jsonObject.put("createDate", neuron.getCreateDate());
        jsonObject.put("createUser", neuron.getCreateUser());
        jsonObject.put("index", neuron.getIndex());
        jsonObject.put("name", neuron.getName());
        jsonObject.put("visited", neuron.isVisited());
        jsonObject.put("text", neuron.getText());
        return jsonObject;
    }

    /**
     * @return
     */
    private Text createDummyTextNeuron(String text) {
        Text neuron = new Text();
        neuron.setChangeDate(new Date());
        neuron.setChangeUser("Paul");
        neuron.setCreateDate(new Date());
        neuron.setCreateUser("Anita");
        neuron.setIndex(1);
        neuron.setName(text);
        neuron.setVisited(false);
        neuron.setText(text);
        return neuron;
    }


}
