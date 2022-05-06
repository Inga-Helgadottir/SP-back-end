package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQuery(name = "MeasurementsIngredients.deleteAllRows", query = "DELETE from MeasurementsIngredients mi")
@Table(name = "MeasurementsIngredients")
public class MeasurementsIngredients implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "measurementIngredient")
    private String measurementIngredient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Cocktail_id", referencedColumnName = "id", nullable = false)
    private Cocktail cocktail;

    public MeasurementsIngredients() {
    }

    public MeasurementsIngredients(String measurementIngredient) {
        this.measurementIngredient = measurementIngredient;
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
    public String toString() {
        return "MeasurementsIngredients{" +
                "id=" + id +
                ", measurementIngredient='" + measurementIngredient + '\'' +
                '}';
    }
}
