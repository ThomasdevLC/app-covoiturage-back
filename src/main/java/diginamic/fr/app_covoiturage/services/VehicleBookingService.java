package diginamic.fr.app_covoiturage.services;

import diginamic.fr.app_covoiturage.dto.booking.VehicleBookingDTO;
import diginamic.fr.app_covoiturage.mapper.booking.VehicleBookingMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.models.VehicleBooking;
import diginamic.fr.app_covoiturage.repositories.CompanyVehicleRepository;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.repositories.VehicleBookingRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleBookingService {

    @Autowired
    private VehicleBookingRepository vehicleBookingRepository;

    @Autowired
    private VehicleBookingMapper vehicleBookingMapper;

    @Autowired
    private CompanyVehicleRepository vehicleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public VehicleBookingDTO createBooking(VehicleBookingDTO vehicleBookingDTO) {
        // 1. Valider les dates de début et de fin
        if (vehicleBookingDTO.getStartTime().isAfter(vehicleBookingDTO.getEndTime())) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin.");
        }

        // 2. Vérifier si le véhicule existe
        Vehicle vehicle = vehicleRepository.findById(vehicleBookingDTO.getVehicle().getId())
                .orElseThrow(() -> new IllegalArgumentException("Ce véhicule n'existe pas."));

        // 3. Vérifier si l'employé existe
        Employee employee = employeeRepository.findById(vehicleBookingDTO.getEmployee().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cet utilisateur n'existe pas."));

        // 4. Vérifier si le véhicule est déjà réservé sur ce créneau
        Optional<VehicleBooking> BookingPeriod = vehicleBookingRepository.findByBookingPeriod(
                vehicle.getId(), vehicleBookingDTO.getStartTime(), vehicleBookingDTO.getEndTime());

        if (BookingPeriod.isPresent()) {
            throw new IllegalArgumentException("Le véhicule  est déjà réservé sur ce créneau.");
        }

        // 5. Créer l'entité de réservation
        VehicleBooking vehicleBooking = vehicleBookingMapper.toEntity(vehicleBookingDTO);
        vehicleBooking.setCompanyVehicle(vehicle);
        vehicleBooking.setEmployee(employee);

        // 6. Sauvegarder la réservation
        VehicleBooking savedBooking = vehicleBookingRepository.save(vehicleBooking);

        // 7. Mapper l'entité sauvegardée en DTO et retourner le résultat
        return vehicleBookingMapper.toDTO(savedBooking);
    }

    public void cancelBooking(int bookingId, int employeeId) {
        VehicleBooking booking = vehicleBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("La réservation n'existe pas."));

        if (booking.getEmployee().getId() != employeeId) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à annuler cette réservation.");
        }

        vehicleBookingRepository.delete(booking);
    }

    public VehicleBookingDTO updateBooking(int bookingId, VehicleBookingDTO vehicleBookingDTO) {
        // 1. Valider les dates de début et de fin
        if (vehicleBookingDTO.getStartTime().isAfter(vehicleBookingDTO.getEndTime())) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin.");
        }

        // 2. Récupérer la réservation existante par ID
        VehicleBooking existingBooking = vehicleBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Cette réservation n'existe pas."));

        // 3. Récupérer le nouveau véhicule et vérifier s'il existe
        Vehicle newVehicle = vehicleRepository.findById(vehicleBookingDTO.getVehicle().getId())
                .orElseThrow(() -> new IllegalArgumentException("Ce véhicule n'existe pas."));

        // 4. Vérifier si le véhicule est déjà réservé pendant la nouvelle période
        Optional<VehicleBooking> conflictingBooking = vehicleBookingRepository.findByBookingPeriod(
                newVehicle.getId(), vehicleBookingDTO.getStartTime(), vehicleBookingDTO.getEndTime());

        // Si une réservation conflictuelle existe et qu'elle n'est pas celle en cours
        // de mise à jour
        if (conflictingBooking.isPresent() && conflictingBooking.get().getId() != bookingId) {
            throw new IllegalArgumentException("Le véhicule est déjà réservé sur ce créneau.");
        }

        // 5. Mettre à jour les détails de la réservation
        existingBooking.setStartTime(vehicleBookingDTO.getStartTime());
        existingBooking.setEndTime(vehicleBookingDTO.getEndTime());
        existingBooking.setCompanyVehicle(newVehicle); // Mise à jour du véhicule
        existingBooking.setEmployee(employeeRepository.findById(vehicleBookingDTO.getEmployee().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cet utilisateur n'existe pas.")));

        // 6. Sauvegarder la réservation mise à jour
        VehicleBooking updatedBooking = vehicleBookingRepository.save(existingBooking);

        // 7. Retourner la réservation mise à jour en tant que DTO
        return vehicleBookingMapper.toDTO(updatedBooking);
    }

    public List<VehicleBookingDTO> getAllBookings() {
        List<VehicleBooking> bookings = vehicleBookingRepository.findAll();
        return bookings.stream()
                .map(vehicleBookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleBookingDTO> findBookingsByEmployeeAndTime(int employeeId, boolean past) {
        LocalDateTime now = LocalDateTime.now();
        List<VehicleBooking> vehicleBookings;

        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Cet utilisateur n'existe pas."));

        if (past) {
            vehicleBookings = vehicleBookingRepository.getPastBookingsByEmployeeId(employeeId, now);
        } else {
            vehicleBookings = vehicleBookingRepository.getFuturesBookingsByEmployeeId(employeeId, now);
        }

        return vehicleBookings.stream()
                .map(vehicleBookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleBookingDTO> getAllBookingsByTime(String type, LocalDateTime now) {
        if (now == null) {
            now = LocalDateTime.now();
        }

        List<VehicleBooking> vehicleBookings;

        switch (type.toLowerCase()) {
            case "past":
                vehicleBookings = vehicleBookingRepository.findAllPastBookings(now);
                break;
            case "future":
                vehicleBookings = vehicleBookingRepository.findAllFutureBookings(now);
                break;
            case "now":
                vehicleBookings = vehicleBookingRepository.findAllCurrentBookings(now);
                break;
            default:
                throw new IllegalArgumentException("Invalid booking type: " + type);
        }

        return vehicleBookings.stream()
                .map(vehicleBookingMapper::toDTO)
                .collect(Collectors.toList());
    }

}