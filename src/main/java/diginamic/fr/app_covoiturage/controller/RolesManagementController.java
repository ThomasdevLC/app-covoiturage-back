package diginamic.fr.app_covoiturage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.enums.UserStatus;
import diginamic.fr.app_covoiturage.services.RolesManagementService;
import diginamic.fr.app_covoiturage.utils.SecurityUtils;

@RestController
@RequestMapping("roles-management")
public class RolesManagementController {

    @Autowired
    private RolesManagementService rolesManagementService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = rolesManagementService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam("keyword") String keyword) {
        List<Employee> employees = rolesManagementService.searchEmployees(keyword);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/employees/{id}/update-role")
    public ResponseEntity<Employee> updateUserRole(@PathVariable("id") int id,
            @RequestBody Map<String, String> request) {
        if (!SecurityUtils.hasRole("ROLE_SUPER_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String newRole = request.get("newRole");
        if (newRole == null || (!newRole.equals("ADMIN") && !newRole.equals("USER"))) {
            return ResponseEntity.badRequest().build();
        }

        UserStatus newStatus = UserStatus.valueOf(newRole);
        Employee updatedEmployee = rolesManagementService.updateUserRole(id, newStatus);
        return ResponseEntity.ok(updatedEmployee);
    }
}
