package diginamic.fr.app_covoiturage.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.repositories.PrivateVehicleRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for handling operations related to private vehicles.
 * This class manages creation, updating, retrieval, and deletion of private vehicles
 * as well as associations with employees.
 */
@Service
public class PrivateVehicleService {

    @Autowired
    private PrivateVehicleMapper privateVehicleMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PrivateVehicleRepository privateVehicleRepository;

    public PrivateVehicleDTO createVehicle(PrivateVehicleDTO privateVehicleDTO) {
        Optional<Vehicle> existingVehicle = privateVehicleRepository.findByNumber(privateVehicleDTO.getNumber());
        if (existingVehicle.isPresent()) {
            throw new IllegalArgumentException("Ce véhicule est déjà enregistré.");
        }

        Vehicle vehicle = privateVehicleMapper.toEntity(privateVehicleDTO);

        int employeeId = vehicle.getEmployee().getId();
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            vehicle.setEmployee(employee);
            Vehicle savedVehicle = privateVehicleRepository.save(vehicle);

            return privateVehicleMapper.toDTO(savedVehicle);
        } else {
            throw new RuntimeException("Utilisateur non reconnu");
        }
    }

    public PrivateVehicleDTO updateVehicle(int id, PrivateVehicleDTO vehicleDTO) {

        Optional<Vehicle> optionalVehicle = privateVehicleRepository.findById(id);

        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();

            vehicle.setNumber(vehicleDTO.getNumber());
            vehicle.setBrand(vehicleDTO.getBrand());
            vehicle.setType(vehicleDTO.getType());
            vehicle.setModel(vehicleDTO.getModel());
            vehicle.setSeats(vehicleDTO.getSeats());

            int employeeId = vehicleDTO.getEmployee().getId();
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
            if (optionalEmployee.isPresent()) {
                vehicle.setEmployee(optionalEmployee.get());
            } else {
                throw new RuntimeException("Utilisateur non reconnu");
            }

            Vehicle updatedVehicle = privateVehicleRepository.save(vehicle);

            return privateVehicleMapper.toDTO(updatedVehicle);
        } else {
            throw new RuntimeException("Véhicule non reconnu");
        }
    }

    public List<PrivateVehicleDTO> getVehiclesByEmployeeId(int employeeId) {
        List<Vehicle> vehicles = privateVehicleRepository.findVehiclesByEmployeeId(employeeId);

        if (vehicles.isEmpty()) {
            throw new IllegalArgumentException("Vous n'avez pas de véhicule lié à votre compte.");
        }
        return vehicles.stream()
                .map(privateVehicleMapper::toDTO)
                .collect(Collectors.toList());
    }


    public void deleteVehicle(int id, int employeeId) {
        Vehicle vehicle = privateVehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule non reconnu"));

        if (vehicle.getEmployee().getId() != employeeId) {
            throw new IllegalArgumentException("Utilisateur non autorisé à supprimer ce véhicule.");
        }

        if (privateVehicleRepository.isVehicleLinkedToRideShare(id)) {
            throw new IllegalArgumentException(
                    "Impossible de supprimer ce véhicule car il est lié à un trajet que vous avez organisé.");
        }

        vehicle.setIsDeleted(true);
        privateVehicleRepository.save(vehicle);
    }

    public PrivateVehicleDTO getVehicleById(int id) {
        Vehicle vehicle = privateVehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule non reconnu"));

        return privateVehicleMapper.toDTO(vehicle);
    }

}