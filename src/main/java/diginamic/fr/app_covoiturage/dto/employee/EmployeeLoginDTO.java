package diginamic.fr.app_covoiturage.dto.employee;

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
