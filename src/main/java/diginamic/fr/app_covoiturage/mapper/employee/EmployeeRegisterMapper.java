package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;

@Component
public class EmployeeRegisterMapper {

    /**
     * Mappe un EmployeeRegisterDTO vers une entité Employee.
     *
     * @param dto            Le DTO d'inscription
     * @param roleRepository Le repository pour accéder aux rôles
     * @return Une entité Employee avec les données du DTO
     */
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

    /**
     * Mappe une entité Employee vers un EmployeeRegisterDTO.
     *
     * @param employee L'entité Employee à mapper
     * @return Un DTO EmployeeRegisterDTO avec les données de l'entité
     */
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

        // Convertir Set<Role> en Set<String>
        Set<String> roles = employee.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet());
        dto.setRoles(roles);

        return dto;
    }
}