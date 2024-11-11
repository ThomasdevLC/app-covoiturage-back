package diginamic.fr.app_covoiturage.dto.employee;

import java.util.Set;

import diginamic.fr.app_covoiturage.dto.role.RoleDTO;

public class EmployeeRoleDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleDTO> roles;

    public EmployeeRoleDTO(int id, String firstName, String lastName, String email, Set<RoleDTO> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public EmployeeRoleDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

}