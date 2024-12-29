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
