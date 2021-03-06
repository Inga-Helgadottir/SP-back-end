package entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NamedQuery(name = "Cocktail.deleteAllRows", query = "DELETE from Cocktail c")
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

    @NotNull
    @Column(name = "alcoholic")
    private String alcoholic;

    @NotNull
    @Column(name = "glass")
    private String glass;

    @NotNull
    @Column(name = "instructions", length = 500)
    private String instructions;

    @NotNull
    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "imageAlt")
    private String imageAlt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "user_name", nullable = true)
    private User user;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private List<MeasurementsIngredients> measurementsIngredients = new ArrayList<>();

    public Cocktail() {
    }

    public Cocktail(String name, String alcoholic, String glass, String instructions, String image, String imageAlt) {
        this.name = name;
        this.alcoholic = alcoholic;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.imageAlt = imageAlt;
    }

    public Cocktail(String name, String alcoholic, String glass, String instructions, String image, String imageAlt, User user) {
        this.name = name;
        this.alcoholic = alcoholic;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.imageAlt = imageAlt;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setMeasurementsIngredients(List<MeasurementsIngredients> measurementsIngredients) {
        for (MeasurementsIngredients m : measurementsIngredients){
            addMeasurementsIngredients(m);
        }
    }

    public void addMeasurementsIngredients(MeasurementsIngredients measurementsIngredients) {
        this.measurementsIngredients.add(measurementsIngredients);
        measurementsIngredients.setCocktail(this);
    }

    public String getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alcoholic='" + alcoholic + '\'' +
                ", glass='" + glass + '\'' +
                ", instructions='" + instructions + '\'' +
                ", image='" + image + '\'' +
                ", imageAlt='" + imageAlt + '\'' +
                ", user=" + user +
                ", measurementsIngredients=" + measurementsIngredients.toString() +
                '}';
    }
}