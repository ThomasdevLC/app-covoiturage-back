package diginamic.fr.app_covoiturage.dto.employee;

/**
 * Data Transfer Object for verifying an employee's credentials.
 *
 * This class is designed to facilitate the process of verifying an employee's
 * identity or actions within the system through an email and a generated verification code.
 * It encapsulates the required data, including the email identifier and the verification code,
 * which are essential in authentication or user verification workflows.
 *
 * Fields:
 * - email: Represents the email address of the employee. This is used to identify the employee.
 * - verificationCode: Represents the code generated for verifying the employee's identity or action.
 *
 * This class provides a constructor to initialize its fields, along with getter and setter methods
 * for accessing and modifying the email and verification code.
 */
public class EmployeeVerifyDTO {

    private String email;

    private String verificationCode;

    public EmployeeVerifyDTO(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

}
