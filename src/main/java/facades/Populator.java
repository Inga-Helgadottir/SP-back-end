/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.Cocktail;
import entities.MeasurementsIngredients;
import entities.Role;
import entities.User;
import utils.EMF_Creator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");

        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
    }

    public static void populateCocktails(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Cocktail c = new Cocktail("Hot Wired", "Alcoholic", "Cocktail glass", "Fill shaker with 2 scoops of crushed ice. Add all the ingredients & blend. Then blend it until it becomes a slushie. Pour it in a black salt rimmed glass", "/static/media/cocktailGlass.7c4beed71b440dbede22.jpg", "Hot Wired");

        em.getTransaction().begin();
        MeasurementsIngredients m = new MeasurementsIngredients("60 ml vodka");
        MeasurementsIngredients m2 = new MeasurementsIngredients("15 ml lime juice");
        MeasurementsIngredients m3 = new MeasurementsIngredients("5 Ice cubes");
        MeasurementsIngredients m4 = new MeasurementsIngredients("100 gm black currants");
        MeasurementsIngredients m5 = new MeasurementsIngredients("1 sprig Mint Leaves");

        c.addMeasurementsIngredients(m);
        c.addMeasurementsIngredients(m2);
        c.addMeasurementsIngredients(m3);
        c.addMeasurementsIngredients(m4);
        c.addMeasurementsIngredients(m5);

        em.persist(c);
        em.getTransaction().commit();
    }
    
    public static void main(String[] args) {
        populate();
        populateCocktails();
    }
}
