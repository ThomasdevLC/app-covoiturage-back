package diginamic.fr.app_covoiturage.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.repositories.PrivateVehicleRepository;

@Service
public class PrivateVehicleService {

    @Autowired
    private PrivateVehicleMapper privateVehicleMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PrivateVehicleRepository privateVehicleRepository;

    public PrivateVehicleDTO createVehicle(PrivateVehicleDTO vehicleDTO) {
        Optional<Vehicle> existingVehicle = privateVehicleRepository.findByNumber(vehicleDTO.getNumber());
        if (existingVehicle.isPresent()) {
            throw new RuntimeException("Ce véhicule est déjà enregistré.");
        }

        Vehicle vehicle = privateVehicleMapper.toEntity(vehicleDTO);

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
}