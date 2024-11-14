package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeConnectedDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeProfileDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeConnectedMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConnectedMapper employeeConnectedMapper;

    @PutMapping("/{id}/delete-account")
    public ResponseEntity<Employee> deleteEmployeeAccount(@PathVariable int id) {
        Employee deletedEmployeeAccount = employeeService.deleteEmployeeAccount(id);
        return ResponseEntity.ok(deletedEmployeeAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfileDTO> getEmployeeProfileById(@PathVariable int id) {
        return employeeService.getEmployeeProfile(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user")
    public ResponseEntity<EmployeeConnectedDTO> authenticatedEmployee() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) authentication.getPrincipal();
        EmployeeConnectedDTO employeeDTO = employeeConnectedMapper.toDto(currentEmployee);
        return ResponseEntity.ok(employeeDTO);
    }

    // @GetMapping("/rideshares/{id}")
    // public ResponseEntity<EmployeeRideSharesDTO> getEmployeeById(@PathVariable
    // int id) {
    // return employeeService.getEmployeeById(id)
    // .map(ResponseEntity::ok)
    // .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

}