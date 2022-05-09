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
    CocktailDTO getCocktailById(int id);
    CocktailDTO makeCocktail(Cocktail newCocktail);
    List<CocktailDTO> sortCocktailRecipes(List<CocktailDTO> cocktailList);
    int calcAlcoholUnits(List<CocktailDTO> cocktailList, String gender, int height, int weight);
}
