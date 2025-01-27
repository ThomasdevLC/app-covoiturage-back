package diginamic.fr.app_covoiturage.private_vehicle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.repositories.PrivateVehicleRepository;
import diginamic.fr.app_covoiturage.services.PrivateVehicleService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Unit test class for the PrivateVehicleService.
 *
 * This class contains test cases to ensure the reliable and correct behavior of the
 * PrivateVehicleService functionalities. Several scenarios are tested, including success
 * cases and exception handling for operations like creating, retrieving, deleting,
 * or querying vehicles.
 *
 * Dependencies:
 * - PrivateVehicleService: The service being tested.
 * - PrivateVehicleMapper: A mocked mapper used during vehicle entity and DTO conversions.
 * - EmployeeRepository: A mocked repository used to manage employee-related operations.
 * - PrivateVehicleRepository: A mocked repository used to manage vehicle-related persistence.
 *
 * Test Scenarios:
 * - Test creating a vehicle with valid data.
 * - Test retrieving a vehicle by ID when no vehicle is found.
 * - Test unauthorized deletion of a vehicle.
 * - Test querying vehicles by employee ID when no vehicles are linked to the employee.
 *
 * Mocking:
 * - Dependencies are mocked using Mockito. The `@InjectMocks` annotation is used to inject
 *   mocked collaborators into the PrivateVehicleService instance under test.
 * - The `MockitoAnnotations.openMocks(this)` method initializes these mocks before each test.
 *
 * Assertions:
 * - Various assertions are used to verify the expected output or exception for each test case.
 * - Verifies interactions with mocked dependencies to ensure correct behavior.
 */
class PrivateVehicleServiceTest {

    @InjectMocks
    private PrivateVehicleService privateVehicleService;

    @Mock
    private PrivateVehicleMapper privateVehicleMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PrivateVehicleRepository privateVehicleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVehicle() {
        // Arrange
        PrivateVehicleDTO vehicleDTO = new PrivateVehicleDTO();
        vehicleDTO.setNumber("AB-123-CD");
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setFirstName("Mark");
        employeeDTO.setLastName("Brown");
        vehicleDTO.setEmployee(employeeDTO);

        Vehicle vehicle = new Vehicle();
        vehicle.setNumber("AB-123-CD");

        Employee employee = new Employee();
        employee.setId(1);
        vehicle.setEmployee(employee);

        when(privateVehicleRepository.findByNumber("AB-123-CD")).thenReturn(Optional.empty());
        when(privateVehicleMapper.toEntity(vehicleDTO)).thenReturn(vehicle);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(privateVehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(privateVehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);

        // Act
        PrivateVehicleDTO result = privateVehicleService.createVehicle(vehicleDTO);

        // Assert
        assertNotNull(result);
        assertEquals("AB-123-CD", result.getNumber());
        verify(privateVehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testGetVehicleById_NotFound() {
        // Arrange
        int vehicleId = 1;
        when(privateVehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> privateVehicleService.getVehicleById(vehicleId));
    }

    @Test
    void testDeleteVehicle_Unauthorized() {
        // Arrange
        int vehicleId = 1;
        int employeeId = 2;

        Vehicle vehicle = new Vehicle();
        Employee owner = new Employee();
        owner.setId(1);
        vehicle.setEmployee(owner);

        when(privateVehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            privateVehicleService.deleteVehicle(vehicleId, employeeId);
        });

        assertEquals("Utilisateur non autorisé à supprimer ce véhicule.", exception.getMessage());
    }

    @Test
    void testGetVehiclesByEmployeeId_NoVehicles() {
        // Arrange
        int employeeId = 1;
        when(privateVehicleRepository.findVehiclesByEmployeeId(employeeId)).thenReturn(Arrays.asList());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            privateVehicleService.getVehiclesByEmployeeId(employeeId);
        });

        assertEquals("Vous n'avez pas de véhicule lié à votre compte.", exception.getMessage());
    }
}
