package diginamic.fr.app_covoiturage.mapper.vehicle;

import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.models.Vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrivateVehicleMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    public PrivateVehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        PrivateVehicleDTO dto = new PrivateVehicleDTO();
        dto.setId(vehicle.getId());
        dto.setNumber(vehicle.getNumber());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setSeats(vehicle.getSeats());
        dto.setType(vehicle.getType());

        if (vehicle.getEmployee() != null) {
            dto.setEmployee(employeeMapper.toDTO(vehicle.getEmployee()));
        }

        return dto;
    }

    public Vehicle toEntity(PrivateVehicleDTO dto) {
        if (dto == null) {
            return null;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setNumber(dto.getNumber());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setSeats(dto.getSeats());
        vehicle.setType(dto.getType());

        if (dto.getEmployee() != null) {
            vehicle.setEmployee(employeeMapper.toEntity(dto.getEmployee()));
        }

        return vehicle;
    }
}