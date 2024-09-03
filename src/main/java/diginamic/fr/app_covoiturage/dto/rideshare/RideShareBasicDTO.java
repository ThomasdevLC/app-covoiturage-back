package diginamic.fr.app_covoiturage.dto.rideshare;

import java.time.Duration;
import java.time.LocalDateTime;

import diginamic.fr.app_covoiturage.dto.address.AddressDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import jakarta.validation.constraints.NotNull;

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

    public RideShareBasicDTO() {
    }

    public RideShareBasicDTO(int id, LocalDateTime departureTime, LocalDateTime arrivalTime,
            AddressDTO departureAddress,
            AddressDTO arrivalAddress, EmployeeDTO organizer, int availableSeats, PrivateVehicleDTO vehicle) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.organizer = organizer;
        this.availableSeats = availableSeats;
        this.vehicle = vehicle;

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

    // Nouvelle propriété pour calculer la durée du trajet
    public Duration getTripDuration() {
        if (departureTime != null && arrivalTime != null) {
            return Duration.between(departureTime, arrivalTime);
        }
        return Duration.ZERO;
    }

    // Optionnel : Méthode pour obtenir la durée sous forme de chaîne formatée
    public String getFormattedTripDuration() {
        Duration duration = getTripDuration();
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%02d heures %02d minutes", hours, minutes);
    }
}