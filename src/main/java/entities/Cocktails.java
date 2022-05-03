package entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cocktails")
public class Cocktails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", length = 25)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "name")
    private String name;
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
    @Column(name = "smallImg")
    private String smallImg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bigImg")
    private String bigImg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imageAlt")
    private String imageAlt;
    @JoinTable(name = "ingredients", joinColumns = {
            @JoinColumn(name = "user", referencedColumnName = "user")})
    @ManyToMany
    private List<Ingredient> ingredients;
    @JoinTable(name = "mesurements", joinColumns = {
            @JoinColumn(name = "user", referencedColumnName = "user")})
    @ManyToMany
    private List<Mesurement> mesurements;
    @Basic(optional = true)
    @Column(name = "user")
    private String user;

    @NotNull
    @Column(name = "starRating")
    private String starRating;
    private Timestamp dateOfCreation;

    public Cocktails() {
    }

    public Cocktails(String name, String alcoholic, String glass, String instructions, String smallImg, String bigImg, String imageAlt, List<String> ingredients, List<String> mesurements, String user, String starRating, Timestamp dateOfCreation) {
        this.name = name;
        this.alcoholic = alcoholic;
        this.glass = glass;
        this.instructions = instructions;
        this.smallImg = smallImg;
        this.bigImg = bigImg;
        this.imageAlt = imageAlt;
        this.ingredients = new ArrayList<>();
        this.mesurements = new ArrayList<>();
        this.user = user;
        this.starRating = starRating;
        this.dateOfCreation = dateOfCreation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient ingredients) {
        this.ingredients.add(ingredients);
    }

    public List<Mesurement> getMesurements() {
        return mesurements;
    }

    public void setMesurements(Mesurement mesurements) {
        this.mesurements.add(mesurements);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public Timestamp getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Timestamp dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
