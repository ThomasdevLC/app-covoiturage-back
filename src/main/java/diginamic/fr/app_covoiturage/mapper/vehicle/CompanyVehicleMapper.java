package diginamic.fr.app_covoiturage.mapper.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.models.Vehicle;

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

        if (dto.getEmployee() != null) {
            vehicle.setEmployee(employeeMapper.toEntity(dto.getEmployee()));
        }

        return vehicle;
    }
}
