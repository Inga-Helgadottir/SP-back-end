package facades;

import entities.User;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public interface IUserFacade {
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        return null;
    }
    User getVeryfiedUser(String username, String password) throws AuthenticationException;
    User findUserByName(String username);
    List<User> seeAllUsers();
    User signUp(String username, String password);
    User changeUserRole(User user, String Role);
}
