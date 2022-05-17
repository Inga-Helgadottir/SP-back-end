package dtos;

import entities.Cocktail;
import entities.Role;
import entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {
    private String userName;
    private String userPass;
    private List<RoleDTO> roleList = new ArrayList<>();
    private List<CocktailDTO> cocktails = new ArrayList<>();

    public UserDTO(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

    public static List<UserDTO> getDtos(List<User> u){
        List<UserDTO> rmdtos = new ArrayList();
        u.forEach(uo->rmdtos.add(new UserDTO(uo)));
        return rmdtos;
    }

    public UserDTO(User u) {
        if(u != null){
            this.userName = u.getUserName();
            this.userPass = u.getUserPass();
            this.roleList = RoleDTO.getDtos(u.getRoleList());
            this.cocktails = CocktailDTO.getDtos(u.getCocktails());
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void addRoleList(RoleDTO roleList) {
        this.roleList.add(roleList);
    }

    public List<CocktailDTO> getCocktails() {
        return cocktails;
    }

    public void setCocktails(List<CocktailDTO> cocktails) {
        this.cocktails = cocktails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return userName.equals(userDTO.userName) && userPass.equals(userDTO.userPass) && roleList.equals(userDTO.roleList) && cocktails.equals(userDTO.cocktails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userPass, roleList, cocktails);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                '}';
    }
}
