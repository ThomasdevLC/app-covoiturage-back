package diginamic.fr.app_covoiturage.mapper.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.models.Vehicle;

/**
 * A utility component for mapping between Vehicle entities and
 * CompanyVehicleDTO objects.
 *
 * This mapper provides methods to convert Vehicle entities to their
 * corresponding Data Transfer Objects (DTO) and vice versa. The mapping
 * covers all essential fields including details about the vehicle and
 * its associated employee.
 *
 * Fields Mapped:
 * - id: The vehicle's unique identifier.
 * - number: The vehicle's registration number.
 * - brand: The brand of the vehicle.
 * - model: The model of the vehicle.
 * - category: The category of the vehicle.
 * - picUrl: The URL of the vehicle's picture.
 * - motor: Details about the vehicle's motor.
 * - seats: Number of seats in the vehicle.
 * - co2PerKm: CO2 emissions per kilometer for the vehicle.
 * - type: The type of the vehicle.
 * - status: The operational status of the vehicle.
 * - isDeleted: A flag indicating if the vehicle is deleted.
 * - employee: The employee associated with the vehicle.
 *
 * Key Functionalities:
 * - Conversion from Vehicle entity to CompanyVehicleDTO: Transfers all
 *   fields including related Employee data if available.
 * - Conversion from CompanyVehicleDTO to Vehicle entity: Transfers all
 *   fields and maps related EmployeeDTO to Employee entity if provided.
 *
 * Dependencies:
 * - EmployeeMapper: Used for mapping the associated employee between
 *   Employee and EmployeeDTO representations.
 */
@Component
public class CompanyVehicleMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    public CompanyVehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        CompanyVehicleDTO dto = new CompanyVehicleDTO();
        dto.setId(vehicle.getId());
        dto.setNumber(vehicle.getNumber());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setCategory(vehicle.getCategory());
        dto.setPicUrl(vehicle.getPicUrl());
        dto.setMotor(vehicle.getMotor());
        dto.setSeats(vehicle.getSeats());
        dto.setCo2PerKm(vehicle.getCo2PerKm());
        dto.setType(vehicle.getType());
        dto.setStatus(vehicle.getStatus());
        dto.setIsDeleted(vehicle.getIsDeleted());

        if (vehicle.getEmployee() != null) {
            dto.setEmployee(employeeMapper.toDTO(vehicle.getEmployee()));
        }

        return dto;
    }

    public Vehicle toEntity(CompanyVehicleDTO dto) {
        if (dto == null) {
            return null;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setNumber(dto.getNumber());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setCategory(dto.getCategory());
        vehicle.setPicUrl(dto.getPicUrl());
        vehicle.setMotor(dto.getMotor());
        vehicle.setSeats(dto.getSeats());
        vehicle.setCo2PerKm(dto.getCo2PerKm());
        vehicle.setType(dto.getType());
        vehicle.setStatus(dto.getStatus());
        vehicle.setIsDeleted(dto.getIsDeleted());

        if (dto.getEmployee() != null) {
            vehicle.setEmployee(employeeMapper.toEntity(dto.getEmployee()));
        }

        return vehicle;
    }
}
