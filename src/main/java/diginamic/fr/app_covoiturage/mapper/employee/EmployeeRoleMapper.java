package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.Set;
import java.util.stream.Collectors;

import diginamic.fr.app_covoiturage.mapper.role.RoleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRoleDTO;
import diginamic.fr.app_covoiturage.dto.role.RoleDTO;

public class EmployeeRoleMapper {
    /**
     * Convertit un Employee en EmployeeRoleDTO.
     *
     * @param employee L'entité Employee à convertir.
     * @return Le DTO EmployeeRoleDTO correspondant.
     */
    public static EmployeeRoleDTO toEmployeeRoleDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        Set<RoleDTO> roleDTOs = employee.getRoles().stream()
                .map(RoleMapper::toRoleDTO)
                .collect(Collectors.toSet());

        return new EmployeeRoleDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                roleDTOs);
    }

    /**
     * Convertit un EmployeeRoleDTO en Employee.
     *
     * @param employeeRoleDTO Le DTO EmployeeRoleDTO à convertir.
     * @return L'entité Employee correspondant.
     */
    public static Employee toEmployee(EmployeeRoleDTO employeeRoleDTO) {
        if (employeeRoleDTO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeRoleDTO.getId());
        employee.setFirstName(employeeRoleDTO.getFirstName());
        employee.setLastName(employeeRoleDTO.getLastName());
        employee.setEmail(employeeRoleDTO.getEmail());

        Set<Role> roles = employeeRoleDTO.getRoles().stream()
                .map(RoleMapper::toRole)
                .collect(Collectors.toSet());

        employee.setRoles(roles);

        return employee;
    }
}