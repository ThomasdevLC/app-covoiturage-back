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
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The CompanyVehicleService class provides methods for managing company vehicles
 * in the system. This includes creating, updating, retrieving, and deleting
 * vehicles, as well as updating vehicle status and handling vehicle booking records.
 * The service ensures proper access control and enforces business rules.
 *
 * Responsibilities of this service include:
 * - Creating and registering new company vehicles.
 * - Updating existing vehicle details and status.
 * - Deleting vehicles (logical deletion) after validation checks.
 * - Retrieving vehicles based on various criteria such as brand, number, status, and booking dates.
 * - Cancelling future bookings associated with a vehicle.
 * - Ensuring administrative roles and responsibilities are upheld during operations.
 *
 * This service interacts with the following components:
 * - CompanyVehicleRepository: Handles operations related to vehicle persistence.
 * - CompanyVehicleMapper: Maps between entity and DTO representations.
 * - EmployeeRepository: Verifies the existence of employees.
 * - VehicleBookingRepository: Handles bookings associated with vehicles.
 * - MessageService: Sends notifications about booking cancellations.
 *
 * Access to certain operations is restricted to users with administrative privileges.
 */
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

    @Autowired
    private MessageService messageService;

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
        Vehicle existingVehicle = companyVehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non reconnu"));

        Optional<Vehicle> vehicleWithSameNumber = companyVehicleRepository.findByNumber(companyVehicleDTO.getNumber());
        if (vehicleWithSameNumber.isPresent() && vehicleWithSameNumber.get().getId() != id) {
            throw new RuntimeException("Ce véhicule est déjà enregistré");
        }

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

        boolean isLinkedToBooking = companyVehicleRepository.isVehicleLinkedToBooking(id);
        if (isLinkedToBooking) {
            throw new IllegalArgumentException(
                    "Impossible de supprimer ce véhicule car il est  lié à une réservation en cours.");
        }

        existingVehicle.setIsDeleted(true);
        companyVehicleRepository.save(existingVehicle);
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

        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }

        // Vérifier si le véhicule existe
        Vehicle vehicle = companyVehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule non trouvé"));

        if (vehicle.getStatus() == VehicleStatus.AVAILABLE && newStatus != VehicleStatus.AVAILABLE) {
            cancelAllBookingsForVehicle(vehicle);
        }

        vehicle.setStatus(newStatus);
        Vehicle updatedVehicle = companyVehicleRepository.save(vehicle);

        return companyVehicleMapper.toDTO(updatedVehicle);
    }

    /**
     * Annule toutes les réservations futures pour un véhicule et notifie les
     * employés concernés.
     *
     * @param vehicle le véhicule dont les réservations doivent être annulées
     */
    private void cancelAllBookingsForVehicle(Vehicle vehicle) {
        LocalDateTime now = LocalDateTime.now();

        List<VehicleBooking> futureBookings = vehicleBookingRepository.findFutureBookingsByVehicleId(vehicle.getId(),
                now);

        if (!futureBookings.isEmpty()) {
            List<Employee> affectedEmployees = futureBookings.stream()
                    .map(VehicleBooking::getEmployee)
                    .distinct()
                    .collect(Collectors.toList());

            for (Employee employee : affectedEmployees) {
                List<VehicleBooking> employeeBookings = futureBookings.stream()
                        .filter(vb -> vb.getEmployee().getId() == employee.getId())
                        .collect(Collectors.toList());

                for (VehicleBooking booking : employeeBookings) {
                    messageService.notifyEmployeeForBookingCancelation(employee, vehicle, booking);
                }

                employeeBookings.forEach(vb -> {
                    vb.setDeleted(true); // Suppression logique
                    vehicleBookingRepository.save(vb);
                });
            }
        }
    }

    public List<CompanyVehicleDTO> getVehiclesByStatusAndBookingDates(
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Vehicle> vehicles;
        LocalDateTime now = LocalDateTime.now();

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException(
                    "Vous devez spécifier une date de début et une date de fin de réservation.");
        }

        if (endTime != null && startTime != null && endTime.isBefore(startTime)) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début de réservation.");
        }

        if ((startTime != null && startTime.isBefore(now)) ||
                (endTime != null && endTime.isBefore(now))) {
            throw new IllegalArgumentException(
                    "La date de début et la date de fin ne peuvent pas être antérieures à la date actuelle.");
        }

        if (startTime != null && endTime != null) {
            vehicles = companyVehicleRepository.findByStatusAndBookingDates(startTime, endTime);
        } else {
            vehicles = companyVehicleRepository.findByStatus();
        }
        return vehicles.stream()
                .map(companyVehicleMapper::toDTO)
                .toList();
    }

    public CompanyVehicleDTO getVehicleByIdAdminOnly(int id) {
        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        Vehicle vehicle = companyVehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé "));
        return companyVehicleMapper.toDTO(vehicle);
    }

    public CompanyVehicleDTO getVehicleById(int id) {
        Vehicle vehicle = companyVehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé "));
        return companyVehicleMapper.toDTO(vehicle);
    }
}
