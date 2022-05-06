package facades;

import dtos.CocktailDTO;
import entities.Cocktail;
import entities.MeasurementsIngredients;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public interface ICocktailFacade {
    static CocktailFacade getCocktailFacade(EntityManagerFactory _emf) {
        return null;
    }

    List<CocktailDTO> seeAllCocktails();
        List<MeasurementsIngredients> seeAllMeasurementsIngredientsFromCocktailId(int cocktailId);
        CocktailDTO getCocktailById(int id);
//        Cocktail makeCocktail(Cocktail newCocktail, List<MeasurementsIngredients> newM);
        CocktailDTO makeCocktail(Cocktail newCocktail);
}
