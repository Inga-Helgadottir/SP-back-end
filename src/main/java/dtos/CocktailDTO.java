package dtos;

import entities.Cocktail;
import entities.MeasurementsIngredients;
import entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CocktailDTO {
    private int id;
    private String name;
    private String alcoholic;
    private String glass;
    private String instructions;
    private String image;
    private String imageAlt;
    private User user;
    private List<MeasurementsIngredientsDTO> measurementsIngredients = new ArrayList<>();

    public CocktailDTO(String name, String alcoholic, String glass, String instructions, String image, String imageAlt) {
        this.name = name;
        this.alcoholic = alcoholic;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.imageAlt = imageAlt;
    }

    public static List<CocktailDTO> getDtos(List<Cocktail> c){
        List<CocktailDTO> rmdtos = new ArrayList();
        c.forEach(co->rmdtos.add(new CocktailDTO(co)));
        return rmdtos;
    }

    public CocktailDTO(Cocktail c) {
        if(c != null){
            this.id = c.getId();
            this.alcoholic = c.getAlcoholic();
            this.glass = c.getGlass();
            this.image = c.getImage();
            this.imageAlt = c.getImageAlt();
            this.instructions = c.getInstructions();
            this.name = c.getName();
            this.measurementsIngredients = MeasurementsIngredientsDTO.getDtos(c.getMeasurementsIngredients());
        }
    }

    public List<MeasurementsIngredientsDTO> getMeasurementsIngredients() {
        return measurementsIngredients;
    }

    public void addMeasurementsIngredients(MeasurementsIngredientsDTO measurementsIngredients) {
        this.measurementsIngredients.add(measurementsIngredients);

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CocktailDTO that = (CocktailDTO) o;
        return id == that.id && name.equals(that.name) && alcoholic.equals(that.alcoholic) && glass.equals(that.glass) && instructions.equals(that.instructions) && image.equals(that.image) && imageAlt.equals(that.imageAlt) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, alcoholic, glass, instructions, image, imageAlt, user);
    }

    @Override
    public String toString() {
        return "CocktailDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alcoholic='" + alcoholic + '\'' +
                ", glass='" + glass + '\'' +
                ", instructions='" + instructions + '\'' +
                ", image='" + image + '\'' +
                ", imageAlt='" + imageAlt + '\'' +
                ", user=" + user +
                '}';
    }
}
