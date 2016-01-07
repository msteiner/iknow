package org.ms.iknow.service.rest;

import org.ms.iknow.core.question.Questioner;
import org.ms.iknow.core.type.question.Question;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/Question")
public class QuestionService {
  
    Questioner questioner = new Questioner();
  
    @GET
    @Produces("application/json")
    public Response getQuestions() {
      List<Question> questions = questioner.getQuestions();
      return Response.status(200).entity(questions).build();
    }
  
    @GET
    @Path("{parent}/{relation}/{child}")
    @Produces("application/json")
    public Response setAnswer(@PathParam("parent") String parent,
                              @PathParam("relation") String relation,
                              @PathParam("child") String child) {
        // TODO implement
      return Response.status(200).entity("Everything's fine.").build();
    }
}
