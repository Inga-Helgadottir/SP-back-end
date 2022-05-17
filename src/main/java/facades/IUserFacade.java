package facades;

import dtos.UserDTO;
import entities.User;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public interface IUserFacade {
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        return null;
    }
    User getVeryfiedUser(String username, String password) throws AuthenticationException;
    User findUserByName(String username);
    List<UserDTO> seeAllUsers();
    User changeUserRole(User user, String Role);
    User signUp(String userName, String password);
}
