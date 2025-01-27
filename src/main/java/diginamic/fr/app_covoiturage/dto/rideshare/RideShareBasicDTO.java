package diginamic.fr.app_covoiturage.dto.rideshare;

import java.time.Duration;
import java.time.LocalDateTime;

import diginamic.fr.app_covoiturage.dto.address.AddressDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) representing the basic details of a ride-sharing operation.
 * This class encapsulates data related to a single ride-sharing event, including relevant
 * information such as departure and arrival details, the organizer, available seats, and the vehicle used.
 * It is primarily used to facilitate the transfer of ride-sharing data between different layers
 * of the application.
 *
 * Fields:
 * - id: Unique identifier for the ride-sharing event.
 * - departureTime: The date and time when the ride is scheduled to start.
 * - arrivalTime: The date and time when the ride is expected to end.
 * - departureAddress: The starting address of the ride.
 * - arrivalAddress: The destination address of the ride.
 * - organizer: The employee organizing and managing the ride.
 * - availableSeats: The number of seats available for passengers in the ride.
 * - vehicle: The private vehicle used for the ride.
 * - isDeleted: A flag indicating whether the ride has been logically deleted.
 *
 * Constructors:
 * - A default constructor for creating an empty instance of the class.
 * - A parameterized constructor for initializing all fields.
 *
 * Getter and Setter Methods:
 * - Provides access to and allows modification of all fields.
 *
 * Additional Methods:
 * - getTripDuration(): Calculates the duration of the trip based on the departure and arrival times.
 * - getFormattedTripDuration(): Returns the trip duration as a formatted string in hours and minutes.
 */
public class RideShareBasicDTO {

    private int id;
    @NotNull(message = "La date de départ est obligatoire")
    private LocalDateTime departureTime;

    @NotNull(message = "La date d'arrivée est obligatoire")
    private LocalDateTime arrivalTime;

    @NotNull(message = "L'adresse de départ est obligatoire")
    private AddressDTO departureAddress;

    @NotNull(message = "L'adresse d'arrivée est obligatoire")
    private AddressDTO arrivalAddress;

    private EmployeeDTO organizer;

    @NotNull(message = "Le nombre de sièges disponible est obligatoire")
    private int availableSeats;

    private PrivateVehicleDTO vehicle;

    private boolean isDeleted;

    public RideShareBasicDTO() {
    }

    public RideShareBasicDTO(int id, LocalDateTime departureTime, LocalDateTime arrivalTime,
            AddressDTO departureAddress,
            AddressDTO arrivalAddress, EmployeeDTO organizer, int availableSeats, PrivateVehicleDTO vehicle,
            boolean isDeleted) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.organizer = organizer;
        this.availableSeats = availableSeats;
        this.vehicle = vehicle;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public AddressDTO getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(AddressDTO departureAddress) {
        this.departureAddress = departureAddress;
    }

    public AddressDTO getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(AddressDTO arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public EmployeeDTO getOrganizer() {
        return organizer;
    }

    public void setOrganizer(EmployeeDTO organizer) {
        this.organizer = organizer;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public PrivateVehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(PrivateVehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Duration getTripDuration() {
        if (departureTime != null && arrivalTime != null) {
            return Duration.between(departureTime, arrivalTime);
        }
        return Duration.ZERO;
    }

    public String getFormattedTripDuration() {
        Duration duration = getTripDuration();
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%02d heures %02d minutes", hours, minutes);
    }
}