/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import entities.Dog;
import entities.Searches;
import entities.User;
import errorhandling.API_Exception;
import facades.DogFacade;
import fetch.BreedFetcher;
import fetch.BreedImageFetcher;

import fetch.FactsFetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static rest.UserResource.USER_FACADE;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * REST Web Service
 *
 * @author Bruger
 */
@Path("dog")
public class DogResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExecutorService threadPool = HttpUtils.getThreadPool();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final DogFacade FACADE = DogFacade.getDogFacade(EMF);

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BreedResource
     */
    public DogResource() {
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addDog(String jsonString) throws AuthenticationException, API_Exception {
        String name;
        String dateOfBirth;
        String info;
        String breed;
        String activeUser;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            name = json.get("name").getAsString();
            dateOfBirth = json.get("dob").getAsString();
            info = json.get("info").getAsString();
            breed = json.get("breed").getAsString();
            activeUser = json.get("activeUser").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        Dog dog = FACADE.addDog(name, dateOfBirth, info, breed, activeUser);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("name", name);
        responseJson.addProperty("msg", "Welcome on board!");

        return Response.ok(new Gson().toJson(responseJson)).build();

    }

    /**
     * Retrieves representation of an instance of rest.BreedResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/breeds")
    public String getAllbreeds() throws IOException, MalformedURLException, ParseException {

        return GSON.toJson(BreedFetcher.getAllBreeds());

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/img{breed}")
    public String image(@PathParam("breed") String breed) throws IOException, MalformedURLException, ParseException {

        return GSON.toJson(BreedImageFetcher.getBreedImage(breed));

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fact")
    public String fact() throws IOException, MalformedURLException, ParseException {

        return GSON.toJson(FactsFetcher.getFact());

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{breed}")
    public String getDog(@PathParam("breed") String breed) throws IOException, MalformedURLException, ParseException {

        return GSON.toJson(FACADE.getDog(breed));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mydog/{activeUser}")
    public String getMyDog(@PathParam("activeUser") String activeUser) throws IOException, MalformedURLException, ParseException{
        
        
        return GSON.toJson(FACADE.getDogsByPerson(activeUser));
    }
}
