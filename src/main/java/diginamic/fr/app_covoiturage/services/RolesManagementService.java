package diginamic.fr.app_covoiturage.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeRoleDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeRoleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;
import diginamic.fr.app_covoiturage.repositories.RolesManagementRepository;
import diginamic.fr.app_covoiturage.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for managing roles and permissions of employees.
 * Provides methods to retrieve all employees, search employees by keyword,
 * and toggle the assignment of the ADMIN role.
 *
 * Business logic enforces that only users with the ROLE_SUPER_ADMIN permission
 * are authorized to access these operations.
 */
@Service
public class RolesManagementService {

    @Autowired
    private RolesManagementRepository rolesManagementRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Retourne tous les employés enregistrés sous forme de DTO.
     */
    public List<EmployeeRoleDTO> getAllEmployees() {
        if (!SecurityUtils.hasRole("ROLE_SUPER_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        List<Employee> employees = rolesManagementRepository.findAll();
        return employees.stream()
                .map(EmployeeRoleMapper::toEmployeeRoleDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recherche des employés par nom ou email et les retourne sous forme de DTO.
     *
     * @param keyword Le mot-clé pour la recherche (nom ou email).
     */
    public List<EmployeeRoleDTO> searchEmployees(String keyword) {
        if (!SecurityUtils.hasRole("ROLE_SUPER_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        List<Employee> employees = rolesManagementRepository.searchByNameOrEmail(keyword);
        return employees.stream()
                .map(EmployeeRoleMapper::toEmployeeRoleDTO)
                .collect(Collectors.toList());
    }

    /**
     * Active ou désactive le rôle ADMIN pour un employé donné.
     * 
     * @param employeeId L'identifiant de l'employé à modifier.
     * @param isAdmin    Indique si le rôle ADMIN doit être attribué ou retiré.
     * @return L'employé mis à jour sous forme de DTO.
     */
    public EmployeeRoleDTO toggleAdminRole(int employeeId, boolean isAdmin) {
        if (!SecurityUtils.hasRole("ROLE_SUPER_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }

        Employee employee = rolesManagementRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employé non reconnu"));

        Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé : ADMIN"));

        if (isAdmin) {
            if (!employee.getRoles().contains(adminRole)) {
                employee.getRoles().add(adminRole);
            }
        } else {
            employee.getRoles().remove(adminRole);
        }

        Employee updatedEmployee = rolesManagementRepository.save(employee);

        return EmployeeRoleMapper.toEmployeeRoleDTO(updatedEmployee);
    }

}