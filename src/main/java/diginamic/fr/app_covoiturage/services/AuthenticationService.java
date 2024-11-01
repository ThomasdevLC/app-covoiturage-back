package diginamic.fr.app_covoiturage.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeLoginDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeRegisterMapper;
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

    public EmployeeRegisterDTO signup(EmployeeRegisterDTO input) {
        Employee employee = new Employee(
                input.getFirstName(),
                input.getLastName(),
                input.getGender(),
                input.getPhone(),
                input.isAdmin(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                true);
        employeeRepository.save(employee);
        return EmployeeRegisterMapper.toDTO(employee);

    }

    public Employee authenticate(EmployeeLoginDTO input) {
        // Rechercher l'utilisateur par email
        Employee employee = employeeRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non reconnu"));

        // Vérifier si l'utilisateur est activé
        if (!employee.isActive()) {
            throw new RuntimeException("Votre compte n'est pas activé");
        }

        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return employee;
    }
}