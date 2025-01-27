package diginamic.fr.app_covoiturage.dto.rideshare;

import java.time.LocalDateTime;
import diginamic.fr.app_covoiturage.dto.address.AddressDTO;

/**
 * Data Transfer Object (DTO) for updating rideshare details.
 *
 * This class is specifically designed to facilitate the transfer of data related to the
 * update operation of a rideshare. It contains information about the timing, location,
 * organizer, and seat availability of a rideshare.
 *
 * Fields:
 * - departureTime: The updated departure time for the rideshare.
 * - arrivalTime: The updated arrival time for the rideshare.
 * - departureAddress: The updated address where the rideshare starts, represented as an AddressDTO.
 * - arrivalAddress: The updated address where the rideshare ends, represented as an AddressDTO.
 * - organizerId: The ID of the rideshare organizer.
 * - availableSeats: The updated number of seats available in the rideshare.
 *
 * This class includes constructors for initializing instances with or without field values,
 * as well as getter and setter methods for accessing and modifying each field.
 */
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
