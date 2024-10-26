package diginamic.fr.app_covoiturage.dto.employee;

import java.util.List;

import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;

public class EmployeeProfileDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String email;
    private List<PrivateVehicleDTO> vehicle;

    public EmployeeProfileDTO() {
    }

    public EmployeeProfileDTO(int id, String firstName, String lastName, String gender, String phone, String email,
            List<PrivateVehicleDTO> vehicle) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.vehicle = vehicle;
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

    public List<PrivateVehicleDTO> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<PrivateVehicleDTO> vehicle) {
        this.vehicle = vehicle;
    }

}
