package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.UserDTO;
import entities.User;
import errorhandling.API_Exception;
import facades.UserFacade;

import java.rmi.UnexpectedException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.regex.*;

import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

@Path("users")
public class UserResource {

    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;


    public UserResource() {
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(String jsonString) throws API_Exception {
        try {


            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();
            String email = json.get("email").getAsString();

            JsonObject checker = new JsonObject();

         

           boolean testUsername = Pattern.matches("^[a-åA-Å0-9)]{6,}$", username.toLowerCase());
            if( testUsername != true){
                checker.addProperty("msg", "Invalid Username: Must not contain any foreign characters");
                return Response.ok(new Gson().toJson(checker)).build();
            }

            boolean testEmail = Pattern.matches("^[a-åA-Å-0-9@.]*${0,30}", email.toLowerCase());
            if( testEmail != true){
                checker.addProperty("msg", "Invalid Email: Must not contain any special characters");
                return Response.ok(new Gson().toJson(checker)).build();
            }

            User user = USER_FACADE.addUser(username, password,email);                   

            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("username", username);
            responseJson.addProperty("msg", "Welcome to the site");
            return Response.ok(new Gson().toJson(responseJson)).build();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
    }


    @Path("user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public UserDTO getUser(@Context SecurityContext context) throws UnexpectedException {
        return new UserDTO(USER_FACADE.getUserByUsername(context.getUserPrincipal().getName()));
    }

    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(String jsonString) throws AuthenticationException, API_Exception {

        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            Long userId = json.get("id").getAsLong();
            User user = USER_FACADE.deleteUser(userId);
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("message", String.format("Successfully deleted user %d", userId));
            return Response.ok(new Gson().toJson(responseJson)).build();
        } catch (Exception e) {
            return Response.status(400, "Malformed JSON supplied").build();
        }
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public List<User> getAllUsers() {
        List<User> allUsers = USER_FACADE.getAllUsers();
        return allUsers;
    }
}
    
    
