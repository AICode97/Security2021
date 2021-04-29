
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.FlowFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
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
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllFlows() {
        return GSON.toJson(facade.getAllFlows());
    }

}
