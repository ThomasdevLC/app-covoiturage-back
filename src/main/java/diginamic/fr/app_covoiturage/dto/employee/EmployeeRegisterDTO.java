package diginamic.fr.app_covoiturage.dto.employee;

import java.util.Set;
import java.util.HashSet;

public class EmployeeRegisterDTO {

    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String email;
    private String password;
    private boolean isActive = true;

    private Set<String> roles = new HashSet<>();

    public EmployeeRegisterDTO(String firstName, String lastName, String gender, String phone, String email,
            String password, Set<String> roles, Boolean isActive) {
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
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
