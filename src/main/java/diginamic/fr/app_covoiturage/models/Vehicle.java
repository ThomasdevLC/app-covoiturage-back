package diginamic.fr.app_covoiturage.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import org.hibernate.validator.constraints.URL;
import diginamic.fr.app_covoiturage.models.enums.Motor;
import diginamic.fr.app_covoiturage.models.enums.VehicleCategory;
import diginamic.fr.app_covoiturage.models.enums.VehicleStatus;
import diginamic.fr.app_covoiturage.models.enums.VehicleType;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "^[A-Z]{2}-\\d{3}-[A-Z]{2}$|^\\d{1,4} [A-Z]{1,2} \\d{1,2}$", message = "Le numéro de plaque d'immatriculation doit respecter le format français.")
    private String number;

    @Size(min = 1, max = 50, message = "La marque doit avoir entre 1 et 50 caractères.")
    private String brand;

    @Size(min = 1, max = 50, message = "Le modèle doit avoir entre 1 et 50 caractères.")
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private VehicleCategory category;

    @URL(message = "L'URL de l'image doit être valide.")
    private String picUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "motor")
    private Motor motor;

    @Min(value = 1, message = "Le nombre de sièges doit être au moins 1.")
    @Max(value = 50, message = "Le nombre de sièges ne peut pas dépasser 50.")
    private int seats;

    // @Min(value = 0, message = "La valeur du CO2/km doit être positive.")
    @Column(name = "co2_per_km", nullable = true)
    private double co2PerKm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VehicleStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VehicleType type;

    @OneToMany(mappedBy = "vehicle")
    private List<RideShare> rideShares;

    @OneToMany(mappedBy = "companyVehicle", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VehicleBooking> vehicleBookings;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Vehicle(String number, String brand, String model, VehicleCategory category, String picUrl, Motor motor,
            int seats, double co2PerKm, VehicleStatus status, VehicleType type) {
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
    }

    public Vehicle() {
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

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public List<RideShare> getRideShares() {
        return rideShares;
    }

    public void setRideShares(List<RideShare> rideShares) {
        this.rideShares = rideShares;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<VehicleBooking> getVehicleBookings() {
        return vehicleBookings;
    }

    public void setVehicleBookings(List<VehicleBooking> vehicleBookings) {
        this.vehicleBookings = vehicleBookings;
    }

}
