package diginamic.fr.app_covoiturage.dto.booking;

import java.time.LocalDateTime;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import diginamic.fr.app_covoiturage.models.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;

public class VehicleBookingDTO {

    private int id;

    @NotNull(message = "Veuillez renseigner la date de début d'emprunt")
    private LocalDateTime startTime;

    @NotNull(message = "Veuillez renseigner la date de fin d'emprunt")
    private LocalDateTime endTime;

    private CompanyVehicleDTO vehicle;
    private EmployeeDTO employee;

    // Ajoutez le champ 'status'
    private BookingStatus status;

    // Constructeurs
    public VehicleBookingDTO() {
    }

    public VehicleBookingDTO(int id, LocalDateTime startTime, LocalDateTime endTime, CompanyVehicleDTO vehicle,
            EmployeeDTO employee, BookingStatus status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vehicle = vehicle;
        this.employee = employee;
        this.status = status;
    }

    // Getters et setters
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
