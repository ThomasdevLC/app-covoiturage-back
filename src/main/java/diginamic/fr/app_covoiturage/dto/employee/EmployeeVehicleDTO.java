package diginamic.fr.app_covoiturage.dto.employee;

import java.util.List;

import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeVehicleDTO {

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

    private boolean admin;

    List<CompanyVehicleDTO> companyVehicle;

    public EmployeeVehicleDTO() {
    }

    public EmployeeVehicleDTO(int id, String firstName, String lastName, String gender, String phone, String email,
            boolean admin, List<CompanyVehicleDTO> companyVehicle) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.admin = admin;
        this.companyVehicle = companyVehicle;

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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<CompanyVehicleDTO> getCompanyVehicle() {
        return companyVehicle;
    }

    public void setCompanyVehicle(List<CompanyVehicleDTO> companyVehicle) {
        this.companyVehicle = companyVehicle;
    }
}
