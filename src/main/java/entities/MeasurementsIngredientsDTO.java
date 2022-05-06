package entities;

import dtos.CocktailDTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MeasurementsIngredientsDTO {
    private int id;
    private String measurementIngredient;
    private Cocktail cocktail;

    public static List<MeasurementsIngredientsDTO> getDtos(List<MeasurementsIngredients> m){
        List<MeasurementsIngredientsDTO> rmdtos = new ArrayList();
        m.forEach(mi->rmdtos.add(new MeasurementsIngredientsDTO(mi)));
        return rmdtos;
    }

    public MeasurementsIngredientsDTO(MeasurementsIngredients m) {
        if(m != null){
            this.id = m.getId();
            this.measurementIngredient = m.getMeasurementIngredient();
            this.cocktail = m.getCocktail();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasurementIngredient() {
        return measurementIngredient;
    }

    public void setMeasurementIngredient(String measurementIngredient) {
        this.measurementIngredient = measurementIngredient;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementsIngredientsDTO that = (MeasurementsIngredientsDTO) o;
        return id == that.id && measurementIngredient.equals(that.measurementIngredient) && cocktail.equals(that.cocktail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measurementIngredient, cocktail);
    }

    @Override
    public String toString() {
        return "MeasurementsIngredientsDTO{" +
                "id=" + id +
                ", measurementIngredient='" + measurementIngredient + '\'' +
                ", cocktail=" + cocktail +
                '}';
    }
}
