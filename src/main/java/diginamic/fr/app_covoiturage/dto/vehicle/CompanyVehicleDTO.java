package diginamic.fr.app_covoiturage.dto.vehicle;

import org.hibernate.validator.constraints.URL;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.models.enums.Motor;
import diginamic.fr.app_covoiturage.models.enums.VehicleCategory;
import diginamic.fr.app_covoiturage.models.enums.VehicleStatus;
import diginamic.fr.app_covoiturage.models.enums.VehicleType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CompanyVehicleDTO {

    private int id;

    @NotNull(message = "Veuillez remplir le champ : immatriculation.")
    private String number;

    @Size(min = 1, max = 50, message = "La marque doit avoir entre 1 et 50 caractères.")
    private String brand;

    @Size(min = 1, max = 50, message = "Le modèle doit avoir entre 1 et 50 caractères.")
    private String model;

    private VehicleCategory category;

    @URL(message = "L'URL de l'image doit être valide.")
    private String picUrl;

    private Motor motor;

    @Min(value = 1, message = "Le nombre de sièges doit être au moins 1.")
    @Max(value = 50, message = "Le nombre de sièges ne peut pas dépasser 50.")
    private int seats;

    @Min(value = 0, message = "La valeur du CO2/km doit être positive.")
    private double co2PerKm;

    private VehicleStatus status;

    private VehicleType type;

    private EmployeeDTO employee;

    public CompanyVehicleDTO(int id, String number, String brand, String model, VehicleCategory category, String picUrl,
            Motor motor, int seats, double co2PerKm, VehicleStatus status, VehicleType type, EmployeeDTO employee) {
        this.id = id;
        this.number = number;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.picUrl = picUrl;
        this.motor = motor;
        this.seats = seats;
        this.co2PerKm = co2PerKm;
        this.status = status;
        this.type = type;
        this.employee = employee;

    }

    public CompanyVehicleDTO() {
    }

    // Getters et setters

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

    public VehicleCategory getCategory() {
        return category;
    }

    public void setCategory(VehicleCategory category) {
        this.category = category;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getCo2PerKm() {
        return co2PerKm;
    }

    public void setCo2PerKm(double co2PerKm) {
        this.co2PerKm = co2PerKm;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

}
