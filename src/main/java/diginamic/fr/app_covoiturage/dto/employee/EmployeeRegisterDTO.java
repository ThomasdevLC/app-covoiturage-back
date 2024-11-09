package diginamic.fr.app_covoiturage.dto.employee;

import diginamic.fr.app_covoiturage.models.enums.UserStatus;

public class EmployeeRegisterDTO {

    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private UserStatus userStatus = UserStatus.USER;
    private String email;
    private String password;
    private boolean isActive = true;

    public EmployeeRegisterDTO(String firstName, String lastName, String gender, String phone, String email,
            String password, UserStatus userStatus, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.userStatus = userStatus != null ? userStatus : UserStatus.USER;
        this.email = email;
        this.password = password;
        this.isActive = isActive != null ? isActive : true;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
