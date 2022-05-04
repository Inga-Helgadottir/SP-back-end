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
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");
    }

    public static void populateCocktails(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

//        UserFacade uf = new UserFacade();
//        uf.getUserFacade(emf);
//        User user = uf.findUserByName("user");
        Cocktail c = new Cocktail("A1", "Alcoholic", "Cocktail glass", "Pour all ingredients into a cocktail shaker, mix and serve over ice into a chilled glass.", "https://www.thecocktaildb.com/images/media/drink/2x8thr1504816928.jpg", "A1");
        Cocktail c2 = new Cocktail("ABC", "Alcoholic", "Shot glass", "Layered in a shot glass.", "https://www.thecocktaildb.com/images/media/drink/tqpvqp1472668328.jpg", "ABC");

        em.getTransaction().begin();
        MeasurementsIngredients m = new MeasurementsIngredients("1 3/4 shot Gin");
        MeasurementsIngredients m2 = new MeasurementsIngredients("1 shot Grand Marnier");
        MeasurementsIngredients m3 = new MeasurementsIngredients("1/4 shot Lemmon juice");
        MeasurementsIngredients m4 = new MeasurementsIngredients("1/8 shot Grenadine");
        c.addMeasurementsIngredients(m);
        c.addMeasurementsIngredients(m2);
        c.addMeasurementsIngredients(m3);
        c.addMeasurementsIngredients(m4);

//        MeasurementsIngredients m5 = new MeasurementsIngredients("1/3 Amaretto");
//        MeasurementsIngredients m6 = new MeasurementsIngredients("1/3 Baileys irish cream");
//        MeasurementsIngredients m7 = new MeasurementsIngredients("1/3 Cognac");
//        c2.addMeasurementsIngredients(m5);
//        c2.addMeasurementsIngredients(m6);
//        c2.addMeasurementsIngredients(m7);

        em.persist(c);
//        em.persist(c2);
        em.getTransaction().commit();
    }
    
    public static void main(String[] args) {
//        populate();
        populateCocktails();
    }
}
