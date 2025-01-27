package diginamic.fr.app_covoiturage.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeLoginDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeRegisterMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;

/**
 * Service class for handling authentication and user signup functionalities.
 * This service is responsible for managing the signup of new employees and
 * authenticating existing employees.
 *
 * Key Responsibilities:
 * - Registers a new employee with default roles.
 * - Encrypts passwords before storing them in the database.
 * - Authenticates an employee's credentials during login.
 */
@Service
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    public AuthenticationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public EmployeeRegisterDTO signup(EmployeeRegisterDTO input) {
        Role userRole = roleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Le rôle 'ROLE_USER' n'a pas été trouvé."));

        Employee employee = new Employee(
                input.getFirstName(),
                input.getLastName(),
                input.getGender(),
                input.getPhone(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                true);

        boolean emailExists = employeeRepository.findByEmail(input.getEmail()).isPresent();
        if (emailExists) {
            throw new IllegalArgumentException("Cette adresse e-mail est déjà associée à un compte.");
        }

        employee.getRoles().add(userRole);

        employeeRepository.save(employee);

        return EmployeeRegisterMapper.toDTO(employee);
    }

    public Employee authenticate(EmployeeLoginDTO input) {
        Employee employee = employeeRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non reconnu"));

        if (!employee.isActive()) {
            throw new RuntimeException("Votre compte n'est pas activé");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new RuntimeException("Email ou mot de passe incorrect", e);
        }

        return employee;
    }
}