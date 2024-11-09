package diginamic.fr.app_covoiturage.dto.employee;

import diginamic.fr.app_covoiturage.models.enums.UserStatus;

public class EmployeeConnectedDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private UserStatus userStatus;

    public EmployeeConnectedDTO() {
    }

    public EmployeeConnectedDTO(int id, String firstName, String lastName, String gender, UserStatus userStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.userStatus = userStatus;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
