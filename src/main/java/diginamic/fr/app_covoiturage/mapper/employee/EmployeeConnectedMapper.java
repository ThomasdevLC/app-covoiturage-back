package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeConnectedDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;

/**
 * Mapper class for converting between Employee and EmployeeConnectedDTO objects.
 *
 * This class provides utility methods to:
 * - Transform an Employee entity to its corresponding Data Transfer Object (EmployeeConnectedDTO).
 * - Transform an EmployeeConnectedDTO back to its corresponding Employee entity.
 *
 * The relationships between employees and their roles are considered during the mapping
 * process. Role names are extracted and represented as strings in the DTO and resolved
 * back to Role entities when converting to an Employee.
 *
 * Responsibilities:
 * - Simplify the transfer of employee data between different application layers.
 * - Handle the mapping of complex relationships such as roles.
 *
 * Dependencies:
 * - RoleRepository is required for fetching roles during DTO to entity conversion.
 */
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
                employee.getRoles());
    }

    public Employee toEntity(EmployeeConnectedDTO dto, RoleRepository roleRepository) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setGender(dto.getGender());

        if (dto.getRoles() != null) {
            List<Role> roles = dto.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(RoleName.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + roleName)))
                    .collect(Collectors.toList());
            employee.setRoles(roles);
        }

        return employee;
    }
}