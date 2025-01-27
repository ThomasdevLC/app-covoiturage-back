package diginamic.fr.app_covoiturage.mapper.vehicle;

import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.models.Vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A utility component for mapping between Vehicle entities and PrivateVehicleDTO objects.
 *
 * This mapper provides methods to convert Vehicle entities into their corresponding
 * Data Transfer Objects (DTO) and vice versa. The mapping process includes transferring
 * all relevant vehicle attributes and handling associated Employee mapping using the
 * EmployeeMapper component.
 *
 * Key Functionalities:
 * - Converts a Vehicle entity to a PrivateVehicleDTO.
 * - Converts a PrivateVehicleDTO back to a Vehicle entity.
 * - Ensures proper handling of nested objects, such as mapping the associated Employee
 *   using provided EmployeeMapper.
 *
 * Dependencies:
 * - EmployeeMapper: Used for mapping the associated Employee entity to EmployeeDTO and
 *   vice versa.
 *
 * Method Behavior:
 * - toDTO(Vehicle vehicle): Maps a Vehicle entity to a PrivateVehicleDTO. If the input
 *   is null, the method returns null.
 * - toEntity(PrivateVehicleDTO dto): Maps a PrivateVehicleDTO to a Vehicle entity. If
 *   the input is null, the method returns null.
 */
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
        dto.setIsDeleted(vehicle.getIsDeleted());

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
        vehicle.setIsDeleted(dto.getIsDeleted());

        if (dto.getEmployee() != null) {
            vehicle.setEmployee(employeeMapper.toEntity(dto.getEmployee()));
        }

        return vehicle;
    }
}