package entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "cocktail")
public class Cocktail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.PERSIST)
    private List<MeasurementsIngredients> measurementsIngredients;

    public Cocktail() {
    }

    public Cocktail(String name) {
        this.name = name;
        this.measurementsIngredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MeasurementsIngredients> getMeasurementsIngredients() {
        return measurementsIngredients;
    }

    public void addMeasurementsIngredients(MeasurementsIngredients measurementsIngredients) {
        this.measurementsIngredients.add(measurementsIngredients);
        measurementsIngredients.setCocktail(this);
    }
}
/*

    @Basic(optional = false)
    @NotNull
    @Column(name = "alcoholic")
    private String alcoholic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "glass")
    private String glass;
    @Basic(optional = false)
    @NotNull
    @Column(name = "instructions")
    private String instructions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "image")
    private String image;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imageAlt")
    private String imageAlt;
*/