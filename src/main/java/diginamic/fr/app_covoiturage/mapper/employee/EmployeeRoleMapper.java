package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.List;
import java.util.stream.Collectors;

import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRoleDTO;


/**
 * Utility class for mapping between Employee and EmployeeRoleDTO objects.
 *
 * This class provides functionality to convert an Employee entity to
 * an EmployeeRoleDTO object and vice versa. It is used to facilitate the
 * transfer of employee and role-related data between different layers of
 * an application. The mapping ensures the separation of entity and DTO representations.
 *
 * Methods:
 * - toEmployeeRoleDTO: Converts an Employee object to an EmployeeRoleDTO object.
 * - toEmployee: Converts an EmployeeRoleDTO object to an Employee object.
 */
public class EmployeeRoleMapper {

    public static EmployeeRoleDTO toEmployeeRoleDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        List<String> roleNames = employee.getRoles().stream()
                .map(role -> role.getRoleName().toString()) // Conversion en String du nom du rôle
                .collect(Collectors.toList());

        return new EmployeeRoleDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                roleNames);
    }

    public static Employee toEmployee(EmployeeRoleDTO employeeRoleDTO) {
        if (employeeRoleDTO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeRoleDTO.getId());
        employee.setFirstName(employeeRoleDTO.getFirstName());
        employee.setLastName(employeeRoleDTO.getLastName());
        employee.setEmail(employeeRoleDTO.getEmail());

        List<Role> roles = employeeRoleDTO.getRoles().stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setRoleName(RoleName.valueOf(roleName));
                    return role;
                })
                .collect(Collectors.toList());

        employee.setRoles(roles);

        return employee;
    }
}
