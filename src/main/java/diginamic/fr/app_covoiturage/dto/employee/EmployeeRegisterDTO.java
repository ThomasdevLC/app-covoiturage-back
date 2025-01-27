package diginamic.fr.app_covoiturage.dto.employee;

import java.util.ArrayList;
import java.util.List;



/**
 * Data Transfer Object for registering a new employee.
 *
 * This class is used to encapsulate the necessary details required to register
 * an employee within the system. It includes personal information such as
 * the first name, last name, gender, phone number, email address, and password.
 * Additionally, it contains the account activation status and assigned roles.
 *
 * The default role "ROLE_USER" is assigned if no roles are specified during
 * initialization. The class provides methods to access, set, and validate
 * the employee registration data.
 *
 * Fields:
 * - firstName: The first name of the employee.
 * - lastName: The last name of the employee.
 * - gender: The gender of the employee.
 * - phone: The phone number of the employee.
 * - email: The email address of the employee.
 * - password: The password for the employee's account.
 * - isActive: A boolean indicating whether the employee's account is active.
 *   Defaults to true if not explicitly specified.
 * - roles: A list of roles assigned to the employee. Defaults to "ROLE_USER"
 *   if no roles are provided or the provided list is empty.
 *
 * Constructors:
 * - A parameterized constructor that initializes all the fields.
 * - A no-argument constructor that sets default values for the roles and account activation.
 *
 * This class is commonly used for transferring employee registration data
 * between layers of the application.
 */
public class EmployeeRegisterDTO {

    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String email;
    private String password;
    private boolean isActive = true;

    private List<String> roles = new ArrayList<>();

    public EmployeeRegisterDTO(String firstName, String lastName, String gender, String phone, String email,
            String password, List<String> roles, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.isActive = isActive != null ? isActive : true;

        if (roles == null || roles.isEmpty()) {
            this.roles.add("ROLE_USER");
        } else {
            this.roles = roles;
        }
    }

    public EmployeeRegisterDTO() {
        this.roles.add("ROLE_USER");
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            this.roles.add("ROLE_USER");
        } else {
            this.roles = roles;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
