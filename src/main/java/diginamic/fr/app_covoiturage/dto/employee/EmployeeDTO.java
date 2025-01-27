package diginamic.fr.app_covoiturage.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Data Transfer Object for representing an employee's information.
 *
 * This class contains essential details about an employee, including personal
 * details such as first name, last name, gender, contact information, and a list
 * of user roles associated with the employee. The class ensures validation
 * constraints are applied on fields like firstName, lastName, phone, email, etc.
 *
 * Fields include:
 * - id: Represents the unique identifier of the employee.
 * - firstName: First name of the employee, mandatory, must be between 1 and 100 characters.
 * - lastName: Last name of the employee, mandatory, must be between 1 and 100 characters.
 * - gender: Gender of the employee, mandatory, must be between 1 and 30 characters.
 * - phone: Phone number of the employee, mandatory, must be at least 10 digits.
 * - email: Email address of the employee, mandatory, must follow a valid email format.
 * - roles: List of roles assigned to the employee, mandatory.
 *
 * This DTO is typically used for transferring data related to employees
 * between layers of the application (e.g., from the API layer to the service layer).
 */
public class EmployeeDTO {

    private int id;

    @NotBlank(message = "Veuillez renseigner votre prénom.")
    @Size(min = 1, max = 100, message = "Longueur maximale de 100 caractères et minimale de 1.")
    private String firstName;

    @NotBlank(message = "Veuillez renseigner votre nom.")
    @Size(min = 1, max = 100, message = "Longueur maximale de 100 caractères et minimale de 1.")
    private String lastName;

    @NotBlank(message = "Veuillez renseigner votre genre.")
    @Size(min = 1, max = 30, message = "Longueur maximale de 30 caractères et minimale de 1.")
    private String gender;

    @NotBlank(message = "Veuillez renseigner votre numéro de téléphone.")
    @Size(min = 10, message = "Le numéro de téléphone doit contenir au moins 10 chiffres")
    private String phone;

    @NotBlank(message = "Veuillez renseigner votre email.")
    @Email(message = "Le champ doit être un email valide")
    private String email;

    @NotNull(message = "Veuillez renseigner les rôles d'utilisateur.")
    private List<String> roles;

    // Constructeurs

    public EmployeeDTO() {
    }

    public EmployeeDTO(int id, String firstName, String lastName, String gender, String phone, String email,
            List<String> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
    }

    // Getters et setters

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
