package diginamic.fr.app_covoiturage.mapper.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;


/**
 * A utility component for mapping between Employee and EmployeeDTO objects.
 *
 * This mapper provides methods to convert Employee entities to their corresponding
 * Data Transfer Objects (DTO) and vice versa. The mapping process involves
 * transferring fields such as personal details, contact information, and user roles
 * between the entity and DTO representations.
 *
 * Fields Mapped:
 * - id: Employee's unique identifier.
 * - firstName: Employee's first name.
 * - lastName: Employee's last name.
 * - gender: Employee's gender.
 * - phone: Employee's phone number.
 * - email: Employee's email address.
 * - roles: List of roles associated with the employee, converted between
 *   String representations in DTO and Role entities in the entity model.
 *
 * Key Functionalities:
 * - Converting an Employee entity to EmployeeDTO.
 * - Converting an EmployeeDTO to an Employee entity, with roles being
 *   resolved using a RoleRepository.
 *
 * Dependencies:
 * - RoleRepository: Used for retrieving Role entities when converting
 *   from EmployeeDTO to Employee.
 */
@Component
public class EmployeeMapper {

    @Autowired
    private RoleRepository roleRepository;

    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setGender(employee.getGender());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());

        List<String> roles = employee.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList());
        dto.setRoles(roles);

        return dto;
    }

    public Employee toEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setPhone(employeeDTO.getPhone());
        employee.setEmail(employeeDTO.getEmail());

        // Convertir List<String> en List<Role> pour les rôles
        if (employeeDTO.getRoles() != null) {
            List<Role> roles = employeeDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(RoleName.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + roleName)))
                    .collect(Collectors.toList());
            employee.setRoles(roles);
        }

        return employee;
    }
}