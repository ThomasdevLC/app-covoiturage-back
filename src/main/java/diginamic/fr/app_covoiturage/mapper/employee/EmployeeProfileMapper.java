package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeProfileDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Vehicle;


/**
 * A mapper class for converting between Employee entity and EmployeeProfileDTO.
 *
 * This class provides methods to map an Employee entity to its corresponding
 * EmployeeProfileDTO and vice versa. The mapping ensures that all relevant
 * employee attributes, including personal details and associated private
 * vehicles, are correctly transformed between the entity and DTO representations.
 *
 * Features:
 * - Converts an Employee entity into an EmployeeProfileDTO for use in contexts
 *   that require structured employee profile data.
 * - Converts an EmployeeProfileDTO back into an Employee entity for operations
 *   that involve persistence or domain-specific logic.
 * - Handles nested mapping for associated private vehicles using the
 *   PrivateVehicleMapper.
 *
 * This class depends on the PrivateVehicleMapper for transforming vehicle-related
 * data, ensuring modular and reusable code for vehicle mappings.
 */
@Component
public class EmployeeProfileMapper {

    @Autowired
    private PrivateVehicleMapper privateVehicleMapper;

    public EmployeeProfileDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setGender(employee.getGender());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());

        if (employee.getVehicle() != null) {
            List<PrivateVehicleDTO> vehiclesDTO = employee.getVehicle().stream()
                    .map(privateVehicleMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setVehicle(vehiclesDTO);
        }

        return dto;
    }

    public Employee toEntity(EmployeeProfileDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setGender(dto.getGender());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());

        if (dto.getVehicle() != null) {
            List<Vehicle> vehicles = dto.getVehicle().stream()
                    .map(privateVehicleMapper::toEntity)
                    .collect(Collectors.toList());
            employee.setVehicle(vehicles);
        }

        return employee;
    }
}