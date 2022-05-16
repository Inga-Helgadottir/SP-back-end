package facades;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade implements IUserFacade{

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    protected UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User findUserByName(String username) {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public List<User> seeAllUsers() {
        return null;
    }

    @Override
    public User changeUserRole(User user, String Role) {
        return null;
    }

    /*
    Authors: Inga, Maria
    Date: 16/05/2022

    This function adds a user to our database
    */
    @Override
    public User signUp(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            User user = new User(userName, password);
            Role role = new Role("user");
            user.addRole(role);
            em.persist(user);
//            em.persist(role);
            em.getTransaction().commit();
            return user;
        }finally {
            em.close();
        }
    }
}
