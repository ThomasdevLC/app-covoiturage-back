package diginamic.fr.app_covoiturage.dto.vehicle;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.models.enums.VehicleType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PrivateVehicleDTO {

    private int id;
    @NotNull(message = "Veuillez remplir le champ : immatriculation.")
    private String number;

    @NotNull(message = "Veuillez remplir le champ : marque.")
    @Size(min = 1, max = 50, message = "La marque doit avoir entre 1 et 50 caractères.")
    private String brand;

    @NotNull(message = "Veuillez remplir le champ : modèle.")
    @Size(min = 1, max = 50, message = "Le modèle doit avoir entre 1 et 50 caractères.")
    private String model;

    @NotNull(message = "Veuillez renseigner le nombre de sieges.")
    @Min(value = 1, message = "Le nombre de sièges doit être au moins 1.")
    @Max(value = 50, message = "Le nombre de sièges ne peut pas dépasser 50.")
    private int seats;

    @NotNull(message = "Veuillez renseigner le type de véhicule.")
    private VehicleType type;

    private EmployeeDTO employee;

    public PrivateVehicleDTO() {
    }

    public PrivateVehicleDTO(int id, String number, String brand, String model,
            int seats, EmployeeDTO employee, VehicleType type) {
        this.id = id;
        this.number = number;
        this.brand = brand;
        this.model = model;
        this.seats = seats;
        this.employee = employee;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

}
