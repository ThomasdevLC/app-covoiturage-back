package diginamic.fr.app_covoiturage.dto.employee;

public class EmployeeRegisterDTO {

    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private boolean admin = false;
    private String email;
    private String password;

    public EmployeeRegisterDTO(String firstName, String lastName, String gender, String phone, String email,
            String password, Boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        if (admin != null) {
            this.admin = admin;
        }
        this.email = email;
        this.password = password;
    }

    public EmployeeRegisterDTO() {
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
