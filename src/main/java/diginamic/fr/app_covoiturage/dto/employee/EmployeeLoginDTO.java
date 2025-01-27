package diginamic.fr.app_covoiturage.dto.employee;

/**
 * Data Transfer Object for handling employee login credentials.
 *
 * This class is used to encapsulate the login details required for an employee
 * to authenticate within the system. It contains the email and password fields.
 * Instances of this DTO are commonly used when transferring login data between
 * the frontend and backend systems.
 *
 * Fields:
 * - email: Represents the email address of the employee.
 * - password: Represents the password associated with the employee's account.
 *
 * This class provides getters and setters for accessing and modifying the fields,
 * along with constructors for creating instances with or without initial values.
 */
public class EmployeeLoginDTO {

    private String email;

    private String password;

    public EmployeeLoginDTO(
            String email, String password) {
        this.email = email;
        this.password = password;
    }

    public EmployeeLoginDTO() {
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

}
