package diginamic.fr.app_covoiturage.mapper.employee;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeConnectedDTO;
import diginamic.fr.app_covoiturage.models.Employee;

@Component
public class EmployeeConnectedMapper {

    // Convertit un Employee en EmployeeConnectedDTO
    public EmployeeConnectedDTO toDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeConnectedDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getGender(),
                employee.isAdmin());
    }

    public Employee toEntity(EmployeeConnectedDTO dto) {
        if (dto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setGender(dto.getGender());
        employee.setAdmin(dto.isAdmin());
        return employee;
    }
}
