package rest;

import com.google.gson.*;
import entities.Cocktail;
import entities.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import facades.CocktailFacade;
import facades.Populator;
import facades.UserFacade;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final CocktailFacade cf = CocktailFacade.getCocktailFacade(EMF);
    private static final UserFacade uf = UserFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pokemon")
    public Response pokemon() {
        return Response.ok()
                .entity(GSON.toJson(getNoUrl("https://pokeapi.co/api/v2/pokemon?limit=10&offset=0")))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("swapi")
    public Response swapi() {
        return Response.ok()
                .entity(GSON.toJson(getNoUrl("https://swapi.dev/api/people/1")))
                .build();
    }

    /*
   Authors: Inga, Ole
   Date: 10/05/2022

   This function makes the endpoint for getting a cocktail by its id from the cocktail API
   it uses tha function getNoUrl that takes an endpoint as a parameter and gets from that endpoint
   */
    @GET
    @Path("cocktails/API/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cocktailsByIdAPI(@PathParam("id") int id) {
        return Response.ok()
                .entity(GSON.toJson(getNoUrl("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="+id)))
                .build();
    }

    /*
    Authors: Inga, Maria, Jonas
    Date: 03/05/2022

    This function makes the endpoint for our cocktails searched by the first letter
    it uses tha function getNoUrl that takes an endpoint as a parameter and gets from that endpoint
    */
    @GET
    @Path("cocktails/letter/{letter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cocktailsByLetter(@PathParam("letter") String letter) {
        return Response.ok()
                .entity(GSON.toJson(getNoUrl("https://www.thecocktaildb.com/api/json/v1/1/search.php?f="+letter)))
                .build();
    }

    /*
    Authors: Inga, Maria, Jonas
    Date: 03/05/2022

    This function makes the endpoint for our cocktails searched by name
    it uses tha function getNoUrl that takes an endpoint as a parameter and gets from that endpoint
    */
    @GET
    @Path("cocktails/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cocktailsByName(@PathParam("name") String name) {
        return Response.ok()
                .entity(GSON.toJson(getNoUrl("https://www.thecocktaildb.com/api/json/v1/1/search.php?s="+name)))
                .build();
    }

    /*
    Authors: Inga, Maria, Jonas
    Date: 03/05/2022

    This function makes the endpoint for the filter endpoint that gets cocktails with an ingredient
    it uses tha function getNoUrl that takes an endpoint as a parameter and gets from that endpoint
    */
    @GET
    @Path("cocktails/ingridient/{ingridient}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cocktailsWithIngridient(@PathParam("ingridient") String ingridient) {
        return Response.ok()
                .entity(GSON.toJson(getNoUrl("https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + ingridient)))
                .build();
    }

    /*
    Authors: Inga, Maria, Jonas
    Date: 03/05/2022

    This function makes the endpoint for our random cocktail
    it uses tha function getNoUrl that takes an endpoint as a parameter and gets from that endpoint
    */
    @GET
    @Path("cocktails/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cocktailRandom() {
        return Response.ok()
                .entity(GSON.toJson(getNoUrl("https://www.thecocktaildb.com/api/json/v1/1/random.php")))
                .build();
    }

    /*
    Authors: Inga, Ole
    Date: 05/05/2022

    This function makes an endpoint for getting all the cocktails in our database
    it uses the seeAllCocktails function in src\main\java\facades\CocktailFacade
    */
    @GET
    @Path("/cocktails/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response seeAllCocktails() {
        System.out.println("---------------------");
        return Response.ok()
                .entity(GSON.toJson(cf.seeAllCocktails()))
                .build();
    }

    /*
    Authors: Inga, Maria, Jonas
    Date: 06/05/2022

    This function makes the endpoint for get a cocktail by id
    it uses the getCocktailById function in src\main\java\facades\CocktailFacade
    */
    @GET
    @Path("cocktails/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCocktailById(@PathParam("id") int id) {
        return Response.ok()
                .entity(GSON.toJson(cf.getCocktailById(id)))
                .build();
    }

    /*
    Authors: Inga, Ole, Jonas, Maria
    Date: 06/05/2022

    This function makes the endpoint for add a cocktail
    it uses the makeCocktail function in src\main\java\facades\CocktailFacade
    */
    @POST
    @Path("cocktails/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeCocktail(String jsonContext) {
        System.out.println(jsonContext);
        Cocktail c = GSON.fromJson(jsonContext, Cocktail.class);
        return Response.ok()
                .entity(GSON.toJson(cf.makeCocktail(c)))
                .build();
    }

    /*
   Authors: Inga, Maria
   Date: 17/05/2022

   This tests the function that gets all the users in our database
   the function being tested is in src\test\java\facades\UserFacadeTest.java
   */
    @GET
    @Path("populateMyDBWithUsersAndACocktail")
    @Produces(MediaType.APPLICATION_JSON)
    public void populate() {
        String[] args = new String[0];
        Populator.main(args);
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response seeAllUsers() {
        return Response.ok()
                .entity(GSON.toJson(uf.seeAllUsers()))
                .build();
    }

    /*
   Authors: Inga, Maria
   Date: 18/05/2022

   This function makes an endpoint that changes a users role in the database
   */
    @POST
    @Path("users/changeUserRole")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response changeUserRole(String jsonString) {
        String userName;
        String role;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            userName = json.get("userName").getAsString();
            role = json.get("role").getAsString();

            return Response.ok()
                .entity(GSON.toJson(uf.changeUserRole(userName, role)))
                .build();

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    Authors: Inga, Maria, Jonas
    Date: 03/05/2022

    This function takes an url as a parameter and gets from the endpoint that has that url
    */
    private JsonObject getNoUrl(String sentUrl) {
        try {
            URL url = new URL(sentUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println("conn");
            System.out.println(conn);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "server");
            conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output = br.readLine();
            JsonObject convertedObject = new Gson().fromJson(output, JsonObject.class);
            conn.disconnect();
            return convertedObject;

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
            JsonObject error = new Gson().fromJson(new Gson().toJson(e), JsonObject.class);
            return error;
        }
    }
}