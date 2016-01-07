package org.ms.iknow.service.rest;

import org.ms.iknow.core.question.Questioner;
import org.ms.iknow.core.type.question.Question;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @Path("approve/{questionId}/{userId}")
    @Produces(MediaType.TEXT_HTML)
    public Response setApproval(@PathParam("questionId") String questionId,
                              @PathParam("userId") String userId) {
        //questioner.setAnswers(questionId, userId, statements);
        System.out.println("Approval for questionId=[" + questionId + "], userId=[" + userId + "].");
        return Response.status(200).entity("Everything's fine.").build();
    }
  
    @GET
    @Path("disapprove/{questionId}/{userId}")
    @Produces(MediaType.TEXT_HTML)
    public Response setDisapproval(@PathParam("questionId") String questionId,
                              @PathParam("userId") String userId) {
        //questioner.setAnswers(questionId, userId, statements);
        System.out.println("Disapproval for questionId=[" + questionId + "], userId=[" + userId + "].");
        return Response.status(200).entity("Everything's fine.").build();
    }
  
    @GET
    @Path("improve/{questionId}/{userId}/{statements}")
    @Produces(MediaType.TEXT_HTML)
    public Response setAnswer(@PathParam("questionId") String questionId,
                              @PathParam("userId") String userId,
                              @PathParam("statements") String statements) {
        questioner.setAnswers(questionId, userId, statements);
        System.out.println("questionId=[" + questionId + "], userId=[" + userId + "], statements=[" + statements + "].");
        return Response.status(200).entity("Everything's fine.").build();
    }
}
