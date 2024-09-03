package diginamic.fr.app_covoiturage.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeLoginDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;

@Service
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Employee signup(EmployeeRegisterDTO input) {
        Employee employee = new Employee(
                input.getFirstName(),
                input.getLastName(),
                input.getGender(),
                input.getPhone(),
                input.isAdmin(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                true);
        return employeeRepository.save(employee);
    }

    public Employee authenticate(EmployeeLoginDTO input) {
        // Rechercher l'utilisateur par email
        Employee employee = employeeRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Vérifier si l'utilisateur est activé
        if (!employee.isActive()) {
            throw new RuntimeException("User not enabled");
        }

        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return employee;
    }
}