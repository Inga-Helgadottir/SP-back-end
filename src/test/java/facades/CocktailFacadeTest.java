package facades;

import entities.Cocktail;
import entities.MeasurementsIngredients;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CocktailFacadeTest {

    private static EntityManagerFactory emf;
    private static CocktailFacade facade;

    public CocktailFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CocktailFacade.getCocktailFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("MeasurementsIngredients.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE MeasurementsIngredients AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Cocktail.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Cocktail AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();

            Cocktail c = new Cocktail("A1", "Alcoholic", "Cocktail glass", "Pour all ingredients into a cocktail shaker, mix and serve over ice into a chilled glass.", "https://www.thecocktaildb.com/images/media/drink/2x8thr1504816928.jpg", "A1");
            em.getTransaction().commit();
            em.getTransaction().begin();
            Cocktail c2 = new Cocktail("ABC", "Alcoholic", "Shot glass", "Layered in a shot glass.", "https://www.thecocktaildb.com/images/media/drink/tqpvqp1472668328.jpg", "ABC");

            MeasurementsIngredients m = new MeasurementsIngredients("1 3/4 shot Gin");
            MeasurementsIngredients m2 = new MeasurementsIngredients("1 shot Grand Marnier");
            MeasurementsIngredients m3 = new MeasurementsIngredients("1/4 shot Lemmon juice");
            MeasurementsIngredients m4 = new MeasurementsIngredients("1/8 shot Grenadine");
            c.addMeasurementsIngredients(m);
            c.addMeasurementsIngredients(m2);
            c.addMeasurementsIngredients(m3);
            c.addMeasurementsIngredients(m4);

            MeasurementsIngredients m5 = new MeasurementsIngredients("1/3 Amaretto");
            MeasurementsIngredients m6 = new MeasurementsIngredients("1/3 Baileys irish cream");
            MeasurementsIngredients m7 = new MeasurementsIngredients("1/3 Cognac");
            c2.addMeasurementsIngredients(m5);
            c2.addMeasurementsIngredients(m6);
            c2.addMeasurementsIngredients(m7);

            em.persist(c);
            em.persist(c2);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    /*
    Authors: Inga, Jonas, Maria
    Date: 04/05/2022

    this function tests the function that gets all the cocktails in the database
    we are testing the function seeAllCocktails in src\main\java\facades\CocktailFacade
    */
    @Test
    void seeAllCocktails() {
        System.out.println("get all cocktails");
        List<Cocktail> cocktails = facade.seeAllCocktails();
        int actual = cocktails.size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    /*
    Authors: Inga, Maria
    Date: 04/05/2022

    this function tests the function that gets all the cocktails in the database
    we are testing the function seeAllCocktails in src\main\java\facades\CocktailFacade
    */
    @Test
    void seeAllMeasurementsIngredientsFromCocktailId() {
        System.out.println("get all measurements ingredients from cocktail id");
        List<MeasurementsIngredients> measurementsIngredients = facade.seeAllMeasurementsIngredientsFromCocktailId(1);
        int actual = measurementsIngredients.size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    /*
    Authors: Inga, Maria
    Date: 04/05/2022

    this function tests the function that gets a cocktail by the id in the database
    we are testing the function getCocktailById in src\main\java\facades\CocktailFacade
    */
    @Test
    void getCocktailById(){
        System.out.println("get cocktail by id");
        Cocktail c = facade.getCocktailById(1);
        int actualId = c.getId();
        String actualName = c.getName();
        int expectedId = 1;
        String expectedName = "ABC";
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
    }

    /*
    Authors: Inga, Maria
    Date: 04/05/2022

    this function tests the function that makes a new cocktail
    we are testing the function makeCocktail in src\main\java\facades\CocktailFacade
    */
    @Test
    void makeCocktail() {
        System.out.println("making a cocktail");
        Cocktail c = new Cocktail("Margarita", "Alcoholic", "Cocktail glass", "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten only the outer rim and sprinkle the salt on it. The salt should present to the lips of the imbiber and never mix into the cocktail. Shake the other ingredients with ice, then carefully pour into the glass.", "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg", "Margarita");

        MeasurementsIngredients m = new MeasurementsIngredients("1 1/2 oz Tequila");
        MeasurementsIngredients m2 = new MeasurementsIngredients("1/2 oz Triple sec");
        MeasurementsIngredients m3 = new MeasurementsIngredients("1 Lime juice");
        MeasurementsIngredients m4 = new MeasurementsIngredients("Salt");

        List<MeasurementsIngredients> measurementsIngredientsList = new ArrayList<>();
        measurementsIngredientsList.add(m);
        measurementsIngredientsList.add(m2);
        measurementsIngredientsList.add(m3);
        measurementsIngredientsList.add(m4);

        Cocktail cocktail = facade.makeCocktail(c, measurementsIngredientsList);
        int actual = cocktail.getId();
        int expected = 3;
        assertEquals(expected, actual);
    }
}