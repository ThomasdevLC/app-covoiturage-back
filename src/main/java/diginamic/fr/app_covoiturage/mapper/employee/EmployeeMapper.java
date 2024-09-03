package diginamic.fr.app_covoiturage.mapper.employee;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.models.Employee;

@Component
public class EmployeeMapper {

    public EmployeeDTO toDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setGender(employee.getGender());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());
        dto.setAdmin(employee.isAdmin());
        return dto;
    }

    public Employee toEntity(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setPhone(employeeDTO.getPhone());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAdmin(employeeDTO.isAdmin());

        return employee;
    }
}
