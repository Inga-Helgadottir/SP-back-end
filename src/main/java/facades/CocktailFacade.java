package facades;

import entities.Cocktail;
import entities.MeasurementsIngredients;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CocktailFacade implements ICocktailFacade{

    private static EntityManagerFactory emf;
    private static CocktailFacade instance;

    public CocktailFacade() {
    }

    public static CocktailFacade getCocktailFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CocktailFacade();
        }
        return instance;
    }

    /*
    Authors: Inga, Maria
    Date: 04/05/2022

    this function gets all the cocktails in the database
    */
    public List<Cocktail> seeAllCocktails(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Cocktail> query = em.createQuery("SELECT c FROM Cocktail c", Cocktail.class);
            System.out.println(query);
            List<Cocktail> cocktails = query.getResultList();
            return cocktails;
        }finally {
            em.close();
        }
    }

    /*
    Authors: Inga, Maria
    Date: 04/05/2022

    this function gets all the measurements ingredients of a cocktail in the database
    the variable is a cocktail id from the database
    */
    public List<MeasurementsIngredients> seeAllMeasurementsIngredientsFromCocktailId(int cocktailId){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<MeasurementsIngredients> query = em.createQuery("SELECT m FROM MeasurementsIngredients m WHERE m.cocktail.id = :id", MeasurementsIngredients.class);
            query.setParameter("id", cocktailId);
            List<MeasurementsIngredients> measurementsIngredients = query.getResultList();
            return measurementsIngredients;
        }finally {
            em.close();
        }
    }

    /*
    Authors: Inga, Maria
    Date: 04/05/2022

    this gets a cocktail by the id in the database
    the variable is a cocktail id from the database
    */
    @Override
    public Cocktail getCocktailById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Cocktail c = em.find(Cocktail.class, id);
            return c;
        }finally {
            em.close();
        }
    }

    /*
    Authors: Inga, Maria
    Date: 04/05/2022

    this function makes a new cocktail and adds it to the database
    the variables is a cocktail and MeasurementsIngredients
    */
    @Override
    public Cocktail makeCocktail(Cocktail newCocktail, List<MeasurementsIngredients> newM) {
        EntityManager em = emf.createEntityManager();
        try{
            Cocktail c = new Cocktail(newCocktail.getName(), newCocktail.getAlcoholic(), newCocktail.getGlass(), newCocktail.getInstructions(), newCocktail.getImage(), newCocktail.getImageAlt());
            for (int i = 0; i < newM.size(); i++) {
                MeasurementsIngredients m = new MeasurementsIngredients(newM.get(i).getMeasurementIngredient());
                m.setCocktail(c);
            }
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            return c;
        }finally {
            em.close();
        }
    }
}
