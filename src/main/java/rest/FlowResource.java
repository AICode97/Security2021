
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Flow;
import errorhandling.API_Exception;
import facades.FlowFacade;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 *
 * @author danie
 */
@Path("flow")
public class FlowResource {
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FlowFacade facade = FlowFacade.getFlowFacade(EMF);
    
    @Context
    private UriInfo context;

    public FlowResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response addFlow(String jsonString) throws API_Exception {

        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            String description = json.get("description").getAsString();

            Flow flow = facade.addFlow(name, description);

            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("name", name);
            responseJson.addProperty("msg", "You have made a new flow");
            return Response.ok(new Gson().toJson(responseJson)).build();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    @RolesAllowed("admin")
    public String getAllFlows() {
        return GSON.toJson(facade.getAllFlows());
    }

}
