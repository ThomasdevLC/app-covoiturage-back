package diginamic.fr.app_covoiturage.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeProfileDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeProfileMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;

/**
 * Service class for managing employees.
 *
 * This class provides methods for saving, deleting, and retrieving employee information.
 * It acts as a layer between the controllers and the repositories, containing business logic
 * related to employee management.
 *
 * Features:
 * - Save an employee entity to the database.
 * - Deactivate an employee account by marking it as inactive.
 * - Retrieve employee profile data, including mapped DTO representations.
 *
 * Dependencies:
 * - EmployeeRepository: Provides CRUD operations for Employee entities.
 * - EmployeeProfileMapper: Maps Employee entities to EmployeeProfileDTO and vice versa.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeProfileMapper employeeProfileMapper;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee deleteEmployeeAccount(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setActive(false);
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Utilisateur non reconnu");
        }
    }

    public Optional<EmployeeProfileDTO> getEmployeeProfile(int id) {
        return employeeRepository.findById(id)
                .map(employeeProfileMapper::toDTO);
    }

}
