package diginamic.fr.app_covoiturage.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRideSharesDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeRideSharesMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRideSharesMapper employeeRideSharesMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee registerEmployee(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRegisterDTO.getFirstName());
        employee.setLastName(employeeRegisterDTO.getLastName());
        employee.setGender(employeeRegisterDTO.getGender());
        employee.setPhone(employeeRegisterDTO.getPhone());
        employee.setEmail(employeeRegisterDTO.getEmail());
        employee.setPassword(bCryptPasswordEncoder.encode(employeeRegisterDTO.getPassword()));
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(int id, Employee updateEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setFirstName(updateEmployee.getFirstName());
            employee.setLastName(updateEmployee.getLastName());
            employee.setGender(updateEmployee.getGender());
            employee.setPhone(updateEmployee.getPhone());
            employee.setAdmin(updateEmployee.isAdmin());
            employee.setEmail(updateEmployee.getEmail());
            employee.setPassword(updateEmployee.getPassword());
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }

    public Employee deleteEmployeeAccount(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setActive(false);
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }

    public Optional<EmployeeRideSharesDTO> getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .map(employeeRideSharesMapper::toDTO);
    }

    public Optional<EmployeeDTO> getEmployee(int id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDTO);
    }

}
