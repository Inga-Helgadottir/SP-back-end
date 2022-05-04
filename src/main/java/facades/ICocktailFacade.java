package facades;

import entities.Cocktail;
import entities.MeasurementsIngredients;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public interface ICocktailFacade {
    static CocktailFacade getCocktailFacade(EntityManagerFactory _emf) {
        return null;
    }

    List<Cocktail> seeAllCocktails();
        List<MeasurementsIngredients> seeAllMeasurementsIngredientsFromCocktailId(int cocktailId);
        Cocktail getCocktailById(int id);
        Cocktail makeCocktail(Cocktail newCocktail, List<MeasurementsIngredients> newM);

}
