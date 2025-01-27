package diginamic.fr.app_covoiturage.dto.vehicle;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import diginamic.fr.app_covoiturage.dto.booking.VehicleBookingDTO;
import diginamic.fr.app_covoiturage.models.enums.Motor;
import diginamic.fr.app_covoiturage.models.enums.VehicleCategory;
import diginamic.fr.app_covoiturage.models.enums.VehicleStatus;
import diginamic.fr.app_covoiturage.models.enums.VehicleType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The CompanyVehicleBookingsDTO class represents a Data Transfer Object (DTO)
 * for company vehicles and their related booking information.
 * This class is used for managing and transferring data related to
 * company vehicles, including attributes such as the vehicle's identification,
 * registration number, brand, model, category, image URL, motor type, seating
 * capacity, CO2 emissions, vehicle status, and type. It also includes a list of
 * associated vehicle bookings.
 *
 * The class validates various fields such as the vehicle's registration number,
 * brand, model, category, motor type, image URL, seating capacity, CO2 emissions
 * per kilometer, and other parameters using annotations provided by the Java
 * Bean Validation framework. This ensures that the data adheres to specific
 * constraints during initialization or modification.
 *
 * Key components of this DTO:
 * - Information about the vehicle, such as identification, registration number,
 *   brand, model, category, and type.
 * - Specification details, including motor type, seating capacity, and CO2 emissions.
 * - Operational status of the vehicle (e.g., available or out of service).
 * - URL for an image of the vehicle.
 * - A list of associated bookings represented as VehicleBookingDTO objects.
 *
 * It contains constructors for initializing the DTO either with all attributes
 * or with default values. Getter and setter methods are provided for accessing
 * and modifying the attributes.
 */
public class CompanyVehicleBookingsDTO {

    private int id;

    @NotNull(message = "Veuillez remplir le champ : immatriculation.")
    @Size(min = 1, max = 20, message = "Le numéro doit avoir entre 1 et 20 caractères.")
    private String number;

    @NotNull(message = "Veuillez remplir le champ : marque.")
    @Size(min = 1, max = 50, message = "La marque doit avoir entre 1 et 50 caractères.")
    private String brand;

    @NotNull(message = "Veuillez remplir le champ : modèle.")
    @Size(min = 1, max = 50, message = "Le modèle doit avoir entre 1 et 50 caractères.")
    private String model;

    @NotNull(message = "Veuillez remplir le champ : catégorie.")
    private VehicleCategory category;

    @NotNull(message = "Veuillez renseigner une image.")
    @URL(message = "L'URL de l'image doit être valide.")
    private String picUrl;

    @NotNull(message = "Veuillez remplir le champ : motorisation.")
    private Motor motor;

    @NotNull(message = "Veuillez renseigner le nombre de sièges.")
    @Min(value = 1, message = "Le nombre de sièges doit être au moins 1.")
    @Max(value = 50, message = "Le nombre de sièges ne peut pas dépasser 50.")
    private int seats;

    @NotNull(message = "Veuillez renseigner la valeur du CO2/km.")
    @Min(value = 0, message = "La valeur du CO2/km doit être positive.")
    private double co2PerKm;

    @NotNull(message = "Veuillez renseigner le statut de véhicule.")
    private VehicleStatus status;

    @NotNull(message = "Veuillez renseigner le type de véhicule.")
    private VehicleType type;

    private List<VehicleBookingDTO> vehicleBookings;

    public CompanyVehicleBookingsDTO(int id, String number, String brand, String model, VehicleCategory category,
            String picUrl, Motor motor, int seats, double co2PerKm, VehicleType type, VehicleStatus status,
            List<VehicleBookingDTO> vehicleBookings) {
        this.id = id;
        this.number = number;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.picUrl = picUrl;
        this.motor = motor;
        this.seats = seats;
        this.co2PerKm = co2PerKm;
        this.type = type;
        this.status = status;
        this.vehicleBookings = vehicleBookings;
    }

    public CompanyVehicleBookingsDTO() {
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

    public List<VehicleBookingDTO> getVehicleBookings() {
        return vehicleBookings;
    }

    public void setVehicleBookings(List<VehicleBookingDTO> vehicleBookings) {
        this.vehicleBookings = vehicleBookings;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

}
