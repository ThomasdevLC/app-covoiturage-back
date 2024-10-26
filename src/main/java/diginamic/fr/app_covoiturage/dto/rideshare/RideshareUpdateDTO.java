package diginamic.fr.app_covoiturage.dto.rideshare;

import java.time.LocalDateTime;
import diginamic.fr.app_covoiturage.dto.address.AddressDTO;

public class RideshareUpdateDTO {

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private AddressDTO departureAddress;
    private AddressDTO arrivalAddress;
    private int organizerId;
    private int availableSeats;

    public RideshareUpdateDTO() {
    }

    public RideshareUpdateDTO(LocalDateTime departureTime, LocalDateTime arrivalTime, AddressDTO departureAddress,
            AddressDTO arrivalAddress, int organizerId, int availableSeats) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.organizerId = organizerId;
        this.availableSeats = availableSeats;
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

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
