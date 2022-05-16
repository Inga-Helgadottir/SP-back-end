package facades;

import entities.Cocktail;
import entities.MeasurementsIngredients;
import entities.Role;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("roles.deleteAllRows").executeUpdate();
            em.createNamedQuery("users.deleteAllRows").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /*
    Authors: Inga, Maria
    Date: 16/05/2022

    This tests the function adds a user to our database
    */
    @Test
    void signUpTest() {
        User actual = facade.signUp("testing", "testingThis");
        User expected = new User("testing", "testingThis");
        assertEquals(actual.getUserName(), expected.getUserName());
    }

    /*
    Authors: Inga, Maria
    Date: 16/05/2022

    This tests the function adds a user to our database when it is given wrong info
    */
    @Test
    void signUpTestFail() {
        User actual = facade.signUp("testinghihi", "testingThis");
        User expected = new User("testing", "testingThis");
        assertNotEquals(actual.getUserName(), expected.getUserName());
    }
}