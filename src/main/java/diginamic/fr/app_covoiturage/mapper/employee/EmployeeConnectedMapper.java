package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeConnectedDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;

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
            Set<Role> roles = dto.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(RoleName.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + roleName)))
                    .collect(Collectors.toSet());
            employee.setRoles(roles);
        }

        return employee;
    }
}