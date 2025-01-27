package diginamic.fr.app_covoiturage.mapper.employee;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeLoginDTO;
import diginamic.fr.app_covoiturage.models.Employee;

/**
 * A utility class that provides methods for mapping between {@code Employee} entities
 * and {@code EmployeeLoginDTO} objects. This class is primarily used to convert
 * data between the persistence layer and the application layer for employee login details.
 *
 * The mapping methods ensure that only pertinent fields related to login credentials,
 * such as email and password, are transferred between the {@code Employee} entity
 * and a corresponding {@code EmployeeLoginDTO}.
 *
 * Methods:
 * - {@code toEntity(EmployeeLoginDTO dto)}: Converts an {@code EmployeeLoginDTO} object
 *   to an {@code Employee} entity while populating relevant fields.
 * - {@code toDTO(Employee employee)}: Converts an {@code Employee} entity to
 *   an {@code EmployeeLoginDTO} object, extracting login-related fields.
 */
public class EmployeeLoginMapper {


    public static Employee toEntity(EmployeeLoginDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());

        return employee;
    }

    public static EmployeeLoginDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeLoginDTO dto = new EmployeeLoginDTO();
        dto.setEmail(employee.getEmail());
        dto.setPassword(employee.getPassword());

        return dto;
    }
}
