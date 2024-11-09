package diginamic.fr.app_covoiturage.dto.employee;

import java.util.Set;
import java.util.stream.Collectors;

import diginamic.fr.app_covoiturage.models.Role;

public class EmployeeConnectedDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private Set<String> roles;

    public EmployeeConnectedDTO() {
    }

    public EmployeeConnectedDTO(int id, String firstName, String lastName, String gender, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        // Transformer le Set<Role> en Set<String> pour ne contenir que le nom du rôle
        this.roles = roles.stream()
                .map(Role::getRoleName)
                .map(Enum::name)
                .collect(Collectors.toSet());
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles.stream()
                .map(Role::getRoleName)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
