package diginamic.fr.app_covoiturage.services;

import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.vehicle.CompanyVehicleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.models.VehicleBooking;
import diginamic.fr.app_covoiturage.models.enums.VehicleStatus;
import diginamic.fr.app_covoiturage.repositories.CompanyVehicleRepository;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.repositories.VehicleBookingRepository;
import diginamic.fr.app_covoiturage.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyVehicleService {

    @Autowired
    private CompanyVehicleRepository companyVehicleRepository;

    @Autowired
    private CompanyVehicleMapper companyVehicleMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VehicleBookingRepository vehicleBookingRepository;

    public CompanyVehicleDTO createCompanyVehicle(CompanyVehicleDTO companyVehicleDTO) {
        Optional<Vehicle> existingVehicle = companyVehicleRepository.findByNumber(companyVehicleDTO.getNumber());
        if (existingVehicle.isPresent()) {
            throw new RuntimeException("Ce véhicule est déjà enregistré");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(companyVehicleDTO.getEmployee().getId());
        if (!optionalEmployee.isPresent()) {
            throw new RuntimeException("Utilisateur non reconnu");
        }

        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }

        Vehicle vehicle = companyVehicleMapper.toEntity(companyVehicleDTO);
        vehicle.setStatus(VehicleStatus.AVAILABLE);

        Vehicle savedVehicle = companyVehicleRepository.save(vehicle);

        return companyVehicleMapper.toDTO(savedVehicle);
    }

    public CompanyVehicleDTO updateCompanyVehicle(int id, CompanyVehicleDTO companyVehicleDTO) {
        // Vérifier si le véhicule existe
        Vehicle existingVehicle = companyVehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non reconnu"));

        // Vérifier l'existence du véhicule avec le même numéro (sauf le véhicule
        // actuel)
        Optional<Vehicle> vehicleWithSameNumber = companyVehicleRepository.findByNumber(companyVehicleDTO.getNumber());
        if (vehicleWithSameNumber.isPresent() && vehicleWithSameNumber.get().getId() != id) {
            throw new RuntimeException("Ce véhicule est déjà enregistré");
        }

        // Vérifier l'existence de l'employé et ses droits
        Optional<Employee> optionalEmployee = employeeRepository.findById(companyVehicleDTO.getEmployee().getId());
        if (!optionalEmployee.isPresent()) {
            throw new RuntimeException("Utilisateur non reconnu");
        }

        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }

        existingVehicle.setNumber(companyVehicleDTO.getNumber());
        existingVehicle.setBrand(companyVehicleDTO.getBrand());
        existingVehicle.setModel(companyVehicleDTO.getModel());
        existingVehicle.setCategory(companyVehicleDTO.getCategory());
        existingVehicle.setPicUrl(companyVehicleDTO.getPicUrl());
        existingVehicle.setMotor(companyVehicleDTO.getMotor());
        existingVehicle.setSeats(companyVehicleDTO.getSeats());
        existingVehicle.setCo2PerKm(companyVehicleDTO.getCo2PerKm());
        existingVehicle.setType(companyVehicleDTO.getType());
        existingVehicle.setStatus(VehicleStatus.AVAILABLE);

        Vehicle updatedVehicle = companyVehicleRepository.save(existingVehicle);

        return companyVehicleMapper.toDTO(updatedVehicle);
    }

    public void deleteCompanyVehicle(int id) {
        Vehicle existingVehicle = companyVehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non reconnu : "));

        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }

        companyVehicleRepository.delete(existingVehicle);
    }

    public List<CompanyVehicleDTO> getAllVehicles(String brand, String number) {
        List<Vehicle> vehicles;

        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        if (brand != null) {
            vehicles = companyVehicleRepository.findByBrand(brand);
        } else if (number != null) {
            Optional<Vehicle> vehicleOptional = companyVehicleRepository.findByNumber(number);
            vehicles = vehicleOptional.isPresent() ? List.of(vehicleOptional.get()) : List.of();
        } else {
            vehicles = companyVehicleRepository.findAll();
        }
        return vehicles.stream()
                .map(companyVehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CompanyVehicleDTO updateVehicleStatus(int vehicleId, VehicleStatus newStatus, int employeeId) {
        // Vérifier si le véhicule existe
        Vehicle vehicle = companyVehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        // Vérifier si le statut change de AVAILABLE à un autre statut
        if (vehicle.getStatus() == VehicleStatus.AVAILABLE && newStatus != VehicleStatus.AVAILABLE) {
            // Annuler toutes les réservations associées
            cancelAllBookingsForVehicle(vehicle);
        }

        // Mettre à jour le statut du véhicule
        vehicle.setStatus(newStatus);
        Vehicle updatedVehicle = companyVehicleRepository.save(vehicle);

        // Convertir en DTO et retourner
        return companyVehicleMapper.toDTO(updatedVehicle);
    }

    private void cancelAllBookingsForVehicle(Vehicle vehicle) {
        List<VehicleBooking> bookings = vehicleBookingRepository.findByCompanyVehicleId(vehicle.getId());
        for (VehicleBooking booking : bookings) {
            vehicleBookingRepository.delete(booking);
        }
    }

    public List<CompanyVehicleDTO> getVehiclesByStatusAndBookingDates(
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Vehicle> vehicles;
        if (startTime != null && endTime != null) {
            vehicles = companyVehicleRepository.findByStatusAndBookingDates(startTime, endTime);
        } else {
            vehicles = companyVehicleRepository.findByStatus();
        }
        return vehicles.stream()
                .map(companyVehicleMapper::toDTO)
                .toList();
    }

    public CompanyVehicleDTO getVehicleById(int id) {
        Vehicle vehicle = companyVehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé "));
        return companyVehicleMapper.toDTO(vehicle);
    }

}
