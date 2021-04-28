/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.shaded.json.parser.ParseException;

import facades.SearchFacade;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * REST Web Service
 *
 * @author Bruger
 */
@Path("searches")
public class SearchesResource {
    
       private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExecutorService threadPool = HttpUtils.getThreadPool();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final SearchFacade FACADE = SearchFacade.getSearchFacade(EMF);

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SearchesResource
     */
    public SearchesResource() {
    }

   @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{breed}")
    public String getDog(@PathParam("breed") String breed) throws IOException, MalformedURLException, ParseException {

        return GSON.toJson(FACADE.getSearches(breed));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total")
    public String getDog() throws IOException, MalformedURLException, ParseException {

        return GSON.toJson(FACADE.getAllSearches());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public String getAllDog() throws IOException, MalformedURLException, ParseException {

        return GSON.toJson(FACADE.getAllDogSearches());
    }
    
}
