package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeRideSharesDTO;
import diginamic.fr.app_covoiturage.mapper.rideshare.RideShareBasicMapper;
import diginamic.fr.app_covoiturage.models.Employee;

@Component
public class EmployeeRideSharesMapper {

    private final RideShareBasicMapper rideShareBasicMapper;

    public EmployeeRideSharesMapper(RideShareBasicMapper rideShareBasicMapper) {
        this.rideShareBasicMapper = rideShareBasicMapper;
    }

    public EmployeeRideSharesDTO toDTO(Employee employee) {

        EmployeeRideSharesDTO dto = new EmployeeRideSharesDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setGender(employee.getGender());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());

        if (employee.getRideShares() != null) {
            dto.setRideShares(employee.getRideShares().stream()
                    .map(rideShareBasicMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        if (employee.getOrganizedRides() != null) {
            dto.setOrganizedRides(employee.getOrganizedRides().stream()
                    .map(rideShareBasicMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Employee toEntity(EmployeeRideSharesDTO employeeDTO) {

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setPhone(employeeDTO.getPhone());
        employee.setEmail(employeeDTO.getEmail());

        return employee;
    }
}
