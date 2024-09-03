package diginamic.fr.app_covoiturage.dto.vehicle;

import java.time.LocalDateTime;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import jakarta.validation.constraints.NotNull;

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

