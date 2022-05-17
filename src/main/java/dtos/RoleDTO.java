package dtos;

import entities.MeasurementsIngredients;
import entities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleDTO {
    private String roleName;

    public static List<RoleDTO> getDtos(List<Role> r){
        List<RoleDTO> rmdtos = new ArrayList();
        r.forEach(rd->rmdtos.add(new RoleDTO(rd)));
        return rmdtos;
    }

    public RoleDTO(Role r) {
        if(r != null){
            this.roleName = r.getRoleName();
        }
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return roleName.equals(roleDTO.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
