package org.ms.iknow.service.rest;

import org.ms.iknow.core.type.RelationType;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/relations")
public class MasterDataService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRelations() {
        List<EnumObject> relations = new ArrayList<EnumObject>();
        for (RelationType relationType : RelationType.getAllValues()) {
            relations.add(new EnumObject(relationType));
        }
        return Response.ok(relations).build();
    }

    class EnumObject {
        private String id;
        private String value;

        public EnumObject(RelationType relationType) {
            this.id = relationType.getId();
            this.value = relationType.getValue();
        }

        public EnumObject(String id, String value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String toString() {
            return "EnumObject: id: " + id + ", value: " + value;
        }
    }
}
