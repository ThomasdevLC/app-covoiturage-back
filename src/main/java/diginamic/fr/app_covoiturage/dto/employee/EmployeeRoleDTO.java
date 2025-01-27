package diginamic.fr.app_covoiturage.dto.employee;

import java.util.List;



/**
 * Data Transfer Object for managing employee role information.
 *
 * This class is used to encapsulate the information of an employee
 * along with the roles associated with them. It provides fields to store
 * employee details such as their ID, first name, last name, email, and a list
 * of roles they are assigned to. This DTO facilitates the transfer of
 * employee role-related data between different layers of an application
 * or with external systems.
 *
 * Fields:
 * - id: Represents the unique identifier of the employee.
 * - firstName: Stores the first name of the employee.
 * - lastName: Stores the last name of the employee.
 * - email: Stores the email address of the employee.
 * - roles: A list of roles associated with the employee, represented as strings.
 *
 * This class provides constructors for instantiating objects with or without initial values,
 * along with getters and setters for accessing and modifying the field values.
 */
public class EmployeeRoleDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;

    public EmployeeRoleDTO(int id, String firstName, String lastName, String email, List<String> roles) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
