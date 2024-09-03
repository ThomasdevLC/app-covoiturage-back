package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeConnectedDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRideSharesDTO;
import diginamic.fr.app_covoiturage.exceptions.MessageException;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeConnectedMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.services.EmployeeService;
import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeConnectedMapper employeeConnectedMapper;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRegisterDTO employeeRegisterDTO,
            BindingResult validation)
            throws MessageException {
        if (validation.hasErrors()) {
            throw new MessageException(validation.getAllErrors().get(0).getDefaultMessage());
        }

        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employeeRegisterDTO.getEmail());
        if (existingEmployee.isPresent()) {
            throw new MessageException(
                    "Un utilisateur avec l'email : " + employeeRegisterDTO.getEmail() + " existe déjà.");
        }

        Employee savedEmployee = employeeService.registerEmployee(employeeRegisterDTO);
        return ResponseEntity.ok(savedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @Valid @RequestBody Employee updateEmployee,
            BindingResult validation)
            throws MessageException {
        if (validation.hasErrors()) {
            throw new MessageException(validation.getAllErrors().get(0).getDefaultMessage());
        }
        Employee updatedEmployee = employeeService.updateEmployee(id, updateEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PutMapping("/{id}/delete-account")
    public ResponseEntity<Employee> deleteEmployeeAccount(@PathVariable int id) {
        Employee deletedEmployeeAccount = employeeService.deleteEmployeeAccount(id);
        return ResponseEntity.ok(deletedEmployeeAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRideSharesDTO> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id)
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

}