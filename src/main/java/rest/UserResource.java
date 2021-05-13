package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import dto.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.InvalidInputException;
import errorhandling.NotFoundException;
import facades.UserFacade;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

@Path("user")
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
    public String addUser(String user) throws InvalidInputException {
        List<String> roles = new ArrayList();
        roles.add("user");
        UserDTO userDTO = GSON.fromJson(user, UserDTO.class);
        userDTO = new UserDTO(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), roles);
        userDTO = USER_FACADE.addUser(userDTO);

        return GSON.toJson(userDTO);
    }
    
    /*@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(String jsonString) throws AuthenticationException, API_Exception {
    
    try {
    JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
    Long userId = json.get("id").getAsLong();
    User user = USER_FACADE.findUser(userId);
    
    if (json.has("email")) {
    user.setEmail(json.get("email").getAsString());
    }
    
    if (json.has("username")) {
    user.setUserName(json.get("username").getAsString());
    }
    
    USER_FACADE.updateUser(user);
    
    JsonObject responseJson = new JsonObject();
    responseJson.addProperty("message", String.format("Successfully updated user %d", userId));
    return Response.ok(new Gson().toJson(responseJson)).build();
    }
    catch (Exception e) {
    return Response.status(400, "Malformed JSON supplied").build();
    }
    
    }*/

    // GET /users => find all users
    // GET /users/:id => find user by supplied id
    // POST /users => create new user
    // DELETE /users/:id => delete user by supplied id
    // PUT /users/:id => update user by supplied id, send json object with updates

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
    public List<User> getAllUsers() {
        List<User> allUsers = USER_FACADE.getAllUsers();
        return allUsers;
    }

    

   @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public String getUser(@PathParam("email") String email) throws IOException, MalformedURLException, ParseException {
        
        return GSON.toJson(USER_FACADE.getUserByEmail(email));
    } 
}
    
    
