package diginamic.fr.app_covoiturage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import diginamic.fr.app_covoiturage.models.Employee;
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
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = rolesManagementService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Recherche des employés par nom ou email.
     *
     * @param keyword Le mot-clé pour la recherche.
     */
    @GetMapping("/employees/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam("keyword") String keyword) {
        List<Employee> employees = rolesManagementService.searchEmployees(keyword);
        return ResponseEntity.ok(employees);
    }

    /**
     * Met à jour le rôle d'un employé.
     *
     * @param id      L'identifiant de l'employé dont on veut mettre à jour le rôle.
     * @param request Contient le nouveau rôle sous forme de chaîne de caractères.
     */
    @PutMapping("/employees/{id}/update-role")
    public ResponseEntity<Employee> updateUserRole(@PathVariable("id") int id,
            @RequestBody Map<String, String> request) {
        String newRole = request.get("newRole");
        Employee updatedEmployee = rolesManagementService.updateUserRole(id, newRole);
        return ResponseEntity.ok(updatedEmployee);
    }
}
