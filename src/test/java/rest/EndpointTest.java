package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CocktailDTO;
import dtos.MeasurementsIngredientsDTO;
import entities.Cocktail;
import entities.MeasurementsIngredients;
import entities.User;
import entities.Role;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

//Disabled
public class EndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);

            Cocktail c = new Cocktail("A1", "Alcoholic", "Cocktail glass", "Pour all ingredients into a cocktail shaker, mix and serve over ice into a chilled glass.", "https://www.thecocktaildb.com/images/media/drink/2x8thr1504816928.jpg", "A1");

            MeasurementsIngredients m = new MeasurementsIngredients("1 3/4 shot Gin");
            MeasurementsIngredients m2 = new MeasurementsIngredients("1 shot Grand Marnier");
            MeasurementsIngredients m3 = new MeasurementsIngredients("1/4 shot Lemmon juice");
            MeasurementsIngredients m4 = new MeasurementsIngredients("1/8 shot Grenadine");
            c.addMeasurementsIngredients(m);
            c.addMeasurementsIngredients(m2);
            c.addMeasurementsIngredients(m3);
            c.addMeasurementsIngredients(m4);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.persist(c);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    //integration test
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    public void testRestNoAuthenticationRequired() {
        given()
            .contentType("application/json")
            .when()
            .get("/info/").then()
            .statusCode(200)
            .body("msg", equalTo("Hello anonymous"));
    }

    @Test
    public void testRestForAdmin() {
        login("admin", "test");
        given()
            .contentType("application/json")
            .accept(ContentType.JSON)
            .header("x-access-token", securityToken)
            .when()
            .get("/info/admin").then()
            .statusCode(200)
            .body("msg", equalTo("Hello to (admin) User: admin"));
    }

    @Test
    public void testRestForUser() {
        login("user", "test");
        given()
            .contentType("application/json")
            .header("x-access-token", securityToken)
            .when()
            .get("/info/user").then()
            .statusCode(200)
            .body("msg", equalTo("Hello to User: user"));
    }

    @Test
    public void testAutorizedUserCannotAccesAdminPage() {
        login("user", "test");
        given()
            .contentType("application/json")
            .header("x-access-token", securityToken)
            .when()
            .get("/info/admin").then() //Call Admin endpoint as user
            .statusCode(401);
    }

    @Test
    public void testAutorizedAdminCannotAccesUserPage() {
        login("admin", "test");
        given()
            .contentType("application/json")
            .header("x-access-token", securityToken)
            .when()
            .get("/info/user").then() //Call User endpoint as Admin
            .statusCode(401);
    }

    @Test
    public void testRestForMultiRole1() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .accept(ContentType.JSON)
            .header("x-access-token", securityToken)
            .when()
            .get("/info/admin").then()
            .statusCode(200)
            .body("msg", equalTo("Hello to (admin) User: user_admin"));
    }

    @Test
    public void testRestForMultiRole2() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .header("x-access-token", securityToken)
            .when()
            .get("/info/user").then()
            .statusCode(200)
            .body("msg", equalTo("Hello to User: user_admin"));
    }

    @Test
    public void userNotAuthenticated() {
        logOut();
        given()
            .contentType("application/json")
            .when()
            .get("/info/user").then()
            .statusCode(403)
            .body("code", equalTo(403))
            .body("message", equalTo("Not authenticated - do login"));
    }

    @Test
    public void adminNotAuthenticated() {
        logOut();
        given()
            .contentType("application/json")
            .when()
            .get("/info/user").then()
            .statusCode(403)
            .body("code", equalTo(403))
            .body("message", equalTo("Not authenticated - do login"));
    }

    /*
    Authors: Inga, Maria
    Date: 16/05/2022

    This function tests the sign up function
    The function tested here is located src\main\java\security\SignInEndpoint
    */
    @Test
    void signUpTest() {
        String json = String.format("{username: \"testing\", password: \"testingPass\"}");
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/signup")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    void pokemon() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/pokemon").then()
            .statusCode(200);
    }

    @Test
    void swapi() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/swapi").then()
            .statusCode(200);
    }

    /*
    Author: Inga
    Date: 03/05/2022

    This function tests the endpoint that gets all cocktails with the first letter a
    The function tested here is located src\main\java\rest\DemoResource.java
    */
    @Test
    void cocktailsByLetterTest() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/cocktails/letter/a").then()
            .statusCode(200);
    }

    /*
    Author: Inga
    Date: 03/05/2022

    This function tests the endpoint that gets all cocktails with the name margarita
    The function tested here is located src\main\java\rest\DemoResource.java
    */
    @Test
    void cocktailsByNameTest() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/cocktails/name/margarita").then()
                .statusCode(200);
    }

    /*
    Author: Inga
    Date: 03/05/2022

    This function tests the endpoint that gets all cocktails with vodka as the ingridient
    The function tested here is located src\main\java\rest\DemoResource.java
    */
    @Test
    void cocktailsWithIngridientTest() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/cocktails/ingridient/vodka").then()
            .statusCode(200);
    }

    /*
    Author: Inga
    Date: 03/05/2022

    This function tests the endpoint that gets a random cocktail
    The function tested here is located src\main\java\rest\DemoResource.java
    */
    @Test
    void cocktailRandomTest() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/cocktails/random").then()
            .statusCode(200);
    }

    /*
    Author: Inga, Ole
    Date: 10/05/2022

   This function tests the endpoint for getting a cocktail by its id from the cocktail API
    The function tested here is located src\main\java\rest\DemoResource.java
    */
    @Test
    void cocktailsByIdAPITest() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/cocktails/API/11007").then()
            .statusCode(200);
    }

    /*
    Author: Inga, Ole
    Date: 05/05/2022

    This function tests the endpoint that gets all the cocktail in our database
    The function tested here is located src\main\java\rest\DemoResource
    */
    @Test
    void seeAllCocktailsTest() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/cocktails/all").then()
            .statusCode(200);
    }

    /*
    Authors: Inga, Maria, Jonas
    Date: 06/05/2022

    This function tests the endpoint for get a cocktail by id
    */
    @Test
    void getCocktailByIdTest() {
        login("user_admin", "test");
        given()
            .contentType("application/json")
            .when()
            .get("info/cocktails/1").then()
            .statusCode(200);
    }
    /*
    Authors: Inga
    Date: 06/05/2022

    This function tests the endpoint for get a cocktail by id
    */
    @Test
    void makeCocktailTest() {
        login("user_admin", "test");
        CocktailDTO c = new CocktailDTO("new", "Alcoholic", "Cocktail glass", "put stuff in glass.", "https://www.thecocktaildb.com/images/media/drink/vyxwut1468875960.jpg", "new");
        MeasurementsIngredientsDTO m = new MeasurementsIngredientsDTO(new MeasurementsIngredients("1 3/4 shot Gin"));
        MeasurementsIngredientsDTO m2 = new MeasurementsIngredientsDTO(new MeasurementsIngredients("1 shot Grand Marnier"));
        MeasurementsIngredientsDTO m3 = new MeasurementsIngredientsDTO(new MeasurementsIngredients("1/4 shot Lemmon juice"));
        MeasurementsIngredientsDTO m4 = new MeasurementsIngredientsDTO(new MeasurementsIngredients("1/8 shot Grenadine"));
        c.addMeasurementsIngredients(m);
        c.addMeasurementsIngredients(m2);
        c.addMeasurementsIngredients(m3);
        c.addMeasurementsIngredients(m4);
        String requestBody = GSON.toJson(c);
        System.out.println(requestBody);
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("info/cocktails/add")
                .then()
                .assertThat()
                .body("name", equalTo("new"))
                .body("alcoholic", equalTo("Alcoholic"));
    }
}
