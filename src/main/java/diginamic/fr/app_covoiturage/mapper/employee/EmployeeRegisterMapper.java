package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;


/**
 * The EmployeeRegisterMapper is a utility class responsible for mapping data
 * between Employee and EmployeeRegisterDTO, enabling conversions between data
 * transfer objects and entity objects. This class is typically used in the
 * context of registering or managing employee-related data.
 *
 * Methods:
 * - toEntity: Converts an EmployeeRegisterDTO object into an Employee entity.
 *   It initializes a new Employee instance, populating its attributes with the
 *   values from the given DTO. Additionally, it assigns a default role (`ROLE_USER`)
 *   to the employee using the provided RoleRepository.
 *
 * - toDTO: Converts an Employee entity into an EmployeeRegisterDTO. It extracts
 *   the attributes of the Employee and maps them to the corresponding fields
 *   in the DTO, including a list of role names.
 *
 * This class ensures that the data structures for employees remain consistent
 * between application layers and simplifies the population of complex entity relations.
 */
@Component
public class EmployeeRegisterMapper {


    public static Employee toEntity(EmployeeRegisterDTO dto, RoleRepository roleRepository) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setGender(dto.getGender());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
        employee.setActive(dto.isActive());

        // Ajouter ROLE_USER par défaut
        Role defaultRole = roleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Rôle 'ROLE_USER' non trouvé."));
        employee.getRoles().add(defaultRole);

        return employee;
    }

    public static EmployeeRegisterDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeRegisterDTO dto = new EmployeeRegisterDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setGender(employee.getGender());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());
        dto.setPassword(employee.getPassword());
        dto.setActive(employee.isActive());

        List<String> roles = employee.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList());

        dto.setRoles(roles);

        return dto;
    }
}