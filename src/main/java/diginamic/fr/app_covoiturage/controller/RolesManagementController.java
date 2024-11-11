package diginamic.fr.app_covoiturage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRoleDTO;
import diginamic.fr.app_covoiturage.services.RolesManagementService;

@RestController
@RequestMapping("roles-management")
public class RolesManagementController {

    @Autowired
    private RolesManagementService rolesManagementService;

    /**
     * Récupère la liste de tous les employés.
     */
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeRoleDTO>> getAllEmployees() {
        List<EmployeeRoleDTO> employees = rolesManagementService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Recherche des employés par nom ou email.
     *
     * @param keyword Le mot-clé pour la recherche.
     */
    @GetMapping("/employees/search")
    public ResponseEntity<List<EmployeeRoleDTO>> searchEmployees(@RequestParam("keyword") String keyword) {
        List<EmployeeRoleDTO> employees = rolesManagementService.searchEmployees(keyword);
        return ResponseEntity.ok(employees);
    }

    /**
     * Active ou désactive le rôle ADMIN pour un employé.
     * 
     * @param employeeId L'identifiant de l'employé à modifier.
     * @param isAdmin    Indique si le rôle ADMIN doit être attribué ou retiré.
     */
    @PutMapping("/employees/{employeeId}/toggle-admin-role")
    public ResponseEntity<EmployeeRoleDTO> toggleAdminRole(@PathVariable int employeeId,
            @RequestParam boolean isAdmin) {
        EmployeeRoleDTO updatedEmployee = rolesManagementService.toggleAdminRole(employeeId, isAdmin);
        return ResponseEntity.ok(updatedEmployee);
    }

}
