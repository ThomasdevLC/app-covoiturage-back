package diginamic.fr.app_covoiturage.dto.vehicle;

import java.time.LocalDateTime;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for representing vehicle booking details.
 *
 * This class is used to encapsulate data related to vehicle bookings,
 * including the booking ID, start time, end time, the vehicle being booked,
 * and the employee associated with the booking.
 *
 * Fields:
 * - id: Unique identifier for the booking.
 * - startTime: The starting date and time of the booking (mandatory).
 * - endTime: The ending date and time of the booking (mandatory).
 * - vehicle: The vehicle being reserved, represented as a CompanyVehicleDTO.
 * - employee: The employee who has made the booking, represented as an EmployeeDTO.
 *
 * This DTO is typically used in scenarios like vehicle management and booking systems,
 * allowing for the storage and transfer of relevant booking information between
 * application components.
 */
public class VehicleBookingDTO {

    private int id;

    @NotNull(message = "Veuillez renseigner la date de début d'emprunt")
    private LocalDateTime startTime;

    @NotNull(message = "Veuillez renseigner la date de fin d'emprunt")
    private LocalDateTime endTime;

    private CompanyVehicleDTO vehicle;
    private EmployeeDTO employee;

    public VehicleBookingDTO(int id, LocalDateTime startTime, LocalDateTime endTime, CompanyVehicleDTO vehicle,
            EmployeeDTO employee) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vehicle = vehicle;
        this.employee = employee;

    }

    public VehicleBookingDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public CompanyVehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(CompanyVehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

}

