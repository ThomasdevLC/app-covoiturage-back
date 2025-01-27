package diginamic.fr.app_covoiturage.dto.employee;

import java.util.List;
import java.util.stream.Collectors;

import diginamic.fr.app_covoiturage.models.Role;

/**
 * Data Transfer Object (DTO) representing an employee with associated roles.
 *
 * This class is used to transport employee data, including their unique identifier,
 * first name, last name, gender, and a list of roles they are associated with.
 * The roles are represented as a list of strings containing the role names.
 *
 * Provides constructors for creating objects, both with and without initial data.
 * Facilitates conversion of Role objects into a list of role name strings.
 *
 * Key responsibilities:
 * - Encapsulation of employee data.
 * - Conversion of Role entities to a simpler format of role names as strings.
 * - Providing getter and setter methods to access and modify the data.
 */
public class EmployeeConnectedDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private List<String> roles;

    public EmployeeConnectedDTO() {
    }

    public EmployeeConnectedDTO(int id, String firstName, String lastName, String gender, List<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        // Transformer le List<Role> en List<String> pour ne contenir que le nom du rôle
        this.roles = roles.stream()
                .map(Role::getRoleName)
                .map(Enum::name)
                .collect(Collectors.toList());
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles.stream()
                .map(Role::getRoleName)
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
