package diginamic.fr.app_covoiturage.dto.rideshare;

import java.time.LocalDateTime;
import java.util.List;

import diginamic.fr.app_covoiturage.dto.address.AddressDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeProfileDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object representing details of a ride-share.
 *
 * This class is designed to encapsulate information about a ride-share,
 * including the ride's timing, addresses, organizer, available seats, and vehicle details.
 * It serves as a data container for transferring ride-share-related
 * information between application layers.
 *
 * Fields:
 * - id: Unique identifier for the ride-share.
 * - departureTime: Departure time of the ride (mandatory).
 * - arrivalTime: Arrival time of the ride (mandatory).
 * - departureAddress: Address where the ride starts (mandatory).
 * - arrivalAddress: Address where the ride concludes (mandatory).
 * - organizer: The organizer of the ride, representing an employee profile.
 * - availableSeats: Count of seats available for passengers (mandatory).
 * - vehicle: The private vehicle assigned for this ride.
 * - passengers: List of passengers participating in the ride, represented as employee profiles.
 * - isDeleted: A boolean flag indicating whether the ride-share record is marked as deleted.
 *
 * The class includes constructors, getters, and setters providing access
 * and mutability to these fields.
 */
public class RideShareDTO {

    private int id;
    @NotNull(message = "La date de départ est obligatoire")
    private LocalDateTime departureTime;

    @NotNull(message = "La date d'arrivée est obligatoire")
    private LocalDateTime arrivalTime;

    @NotNull(message = "L'adresse de départ est obligatoire")
    private AddressDTO departureAddress;

    @NotNull(message = "L'adresse d'arrivée est obligatoire")
    private AddressDTO arrivalAddress;

    private EmployeeProfileDTO organizer;

    @NotNull(message = "Le nombre de sièges disponible est obligatoire")
    private int availableSeats;

    private PrivateVehicleDTO vehicle;

    private List<EmployeeProfileDTO> passengers;

    private boolean isDeleted;

    public RideShareDTO() {
    }

    public RideShareDTO(int id, LocalDateTime departureTime, LocalDateTime arrivalTime, AddressDTO departureAddress,
            AddressDTO arrivalAddress, EmployeeProfileDTO organizer, int availableSeats, PrivateVehicleDTO vehicle,
            List<EmployeeProfileDTO> passengers, boolean isDeleted) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.organizer = organizer;
        this.availableSeats = availableSeats;
        this.vehicle = vehicle;
        this.passengers = passengers;
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

    public EmployeeProfileDTO getOrganizer() {
        return organizer;
    }

    public void setOrganizer(EmployeeProfileDTO organizer) {
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

    public List<EmployeeProfileDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<EmployeeProfileDTO> passengers) {
        this.passengers = passengers;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}