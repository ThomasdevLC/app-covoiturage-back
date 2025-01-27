package diginamic.fr.app_covoiturage.dto.booking;

import java.time.LocalDateTime;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import jakarta.validation.constraints.NotNull;



/**
 * The VehicleBookingDTO class represents the data transfer object used for vehicle bookings.
 * It encapsulates the information needed to manage vehicle booking operations, such as booking
 * time details, associated vehicle, associated employee, and deletion status.
 */
public class VehicleBookingDTO {

    private int id;

    @NotNull(message = "Veuillez renseigner la date de début d'emprunt")
    private LocalDateTime startTime;

    @NotNull(message = "Veuillez renseigner la date de fin d'emprunt")
    private LocalDateTime endTime;

    private boolean isDeleted;

    private CompanyVehicleDTO vehicle;
    private EmployeeDTO employee;

    public VehicleBookingDTO(int id, LocalDateTime startTime, LocalDateTime endTime, CompanyVehicleDTO vehicle,
            EmployeeDTO employee, boolean isDeleted) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vehicle = vehicle;
        this.employee = employee;
        this.isDeleted = isDeleted;

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

}
