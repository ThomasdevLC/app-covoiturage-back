package diginamic.fr.app_covoiturage.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeProfileDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeProfileMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // @Autowired
    // private EmployeeRideSharesMapper employeeRideSharesMapper;

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

    // public Optional<EmployeeRideSharesDTO> getEmployeeById(int id) {
    // return employeeRepository.findById(id)
    // .map(employeeRideSharesMapper::toDTO);
    // }
}
