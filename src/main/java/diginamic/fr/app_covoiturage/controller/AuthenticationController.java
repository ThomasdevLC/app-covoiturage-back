package diginamic.fr.app_covoiturage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeLoginDTO;
import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.responses.LoginResponse;
import diginamic.fr.app_covoiturage.services.AuthenticationService;
import diginamic.fr.app_covoiturage.services.JwtService;

/**
 * Controller class for handling authentication-related operations.
 * This class provides endpoints for registering new users and authenticating existing users.
 *
 * The controller is mapped to the "/auth" URL path and handles HTTP POST requests
 * for user registration and login functionalities.
 *
 * Key Endpoints:
 * - "/signup": Handles user registration.
 * - "/login": Handles user login and JWT token generation.
 *
 * The authentication process relies on two main services:
 * - {@link AuthenticationService}: Responsible for user registration
 *   and authentication logic.
 * - {@link JwtService}: Responsible for generating and managing JWT tokens.
 */
@RequestMapping("/auth")
@RestController

public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<EmployeeRegisterDTO> register(@RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        EmployeeRegisterDTO registeredEmployee = authenticationService.signup(employeeRegisterDTO);
        return ResponseEntity.ok(registeredEmployee);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        Employee authenticatedUser = authenticationService.authenticate(employeeLoginDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}