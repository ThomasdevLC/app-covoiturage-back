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

@Service
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository; // Le repository pour gérer les rôles

    public AuthenticationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public EmployeeRegisterDTO signup(EmployeeRegisterDTO input) {
        // Récupérer le rôle par défaut "ROLE_USER"
        Role userRole = roleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Le rôle 'ROLE_USER' n'a pas été trouvé."));

        // Créer un nouvel employé à partir des informations fournies
        Employee employee = new Employee(
                input.getFirstName(),
                input.getLastName(),
                input.getGender(),
                input.getPhone(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                true); // Initialisation de l'employé avec "isActive" à "true" (actif par défaut)

        boolean emailExists = employeeRepository.findByEmail(input.getEmail()).isPresent();
        if (emailExists) {
            throw new IllegalArgumentException("Cette adresse e-mail est déjà associée à un compte.");
        }

        // Assigner le rôle par défaut "ROLE_USER" à l'employé
        employee.getRoles().add(userRole);

        // Sauvegarder l'employé dans la base de données
        employeeRepository.save(employee);

        // Retourner un DTO correspondant à l'employé nouvellement inscrit
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