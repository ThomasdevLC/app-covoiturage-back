package diginamic.fr.app_covoiturage.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.address.AddressDTO;
import diginamic.fr.app_covoiturage.dto.rideshare.RideShareBasicDTO;
import diginamic.fr.app_covoiturage.dto.rideshare.RideShareDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.exceptions.MessageException;
import diginamic.fr.app_covoiturage.mapper.address.AddressMapper;
import diginamic.fr.app_covoiturage.mapper.rideshare.RideShareBasicMapper;
import diginamic.fr.app_covoiturage.mapper.rideshare.RideShareMapper;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.Address;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.RideShare;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.repositories.AddressRepository;
import diginamic.fr.app_covoiturage.repositories.EmployeeRepository;
import diginamic.fr.app_covoiturage.repositories.PrivateVehicleRepository;
import diginamic.fr.app_covoiturage.repositories.RideShareRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RideShareService {

    @Autowired
    private RideShareMapper rideShareMapper;

    @Autowired
    private RideShareBasicMapper rideShareBasicMapper;

    @Autowired
    private PrivateVehicleMapper privateVehicleMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RideShareRepository rideShareRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PrivateVehicleRepository privateVehicleRepository;

    public RideShareDTO createNewRideShare(RideShareDTO rideShareDTO) {

        AddressDTO departureAddressDTO = rideShareDTO.getDepartureAddress();
        Address departureAddress = addressRepository.findByNumberAndStreetAndCity(
                departureAddressDTO.getNumber(),
                departureAddressDTO.getStreet(),
                departureAddressDTO.getCity())
                .orElseGet(() -> addressRepository.save(addressMapper.toEntity(departureAddressDTO)));
        rideShareDTO.setDepartureAddress(addressMapper.toDTO(departureAddress));

        AddressDTO arrivalAddressDTO = rideShareDTO.getArrivalAddress();
        Address arrivalAddress = addressRepository.findByNumberAndStreetAndCity(
                arrivalAddressDTO.getNumber(),
                arrivalAddressDTO.getStreet(),
                arrivalAddressDTO.getCity())
                .orElseGet(() -> addressRepository.save(addressMapper.toEntity(arrivalAddressDTO)));
        rideShareDTO.setArrivalAddress(addressMapper.toDTO(arrivalAddress));

        if (departureAddress.getId() == arrivalAddress.getId()) {
            throw new IllegalArgumentException("L'adresse de départ et l'adresse d'arrivée doivent être différentes.");
        }

        LocalDateTime departureTime = rideShareDTO.getDepartureTime();
        LocalDateTime arrivalTime = rideShareDTO.getArrivalTime();
        if (departureTime.isAfter(arrivalTime)) {
            throw new IllegalArgumentException("La date de départ nne peut pas être antérieure à la date d'arrivée.");
        }

        LocalDateTime now = LocalDateTime.now();

        if (departureTime.isBefore(now) || arrivalTime.isBefore(now)) {
            throw new IllegalArgumentException(
                    "La date de départ et la date d'arrivée ne peuvent pas être antérieures à la date actuelle.");
        }

        // Vérification et récupération de l'organisateur
        Integer organizerId = rideShareDTO.getOrganizer().getId(); // Récupérer l'ID de l'organisateur depuis le DTO

        // VERIFICATION Covoiturage pendant cette période
        LocalDateTime newDepartureTime = rideShareDTO.getDepartureTime();
        LocalDateTime newArrivalTime = rideShareDTO.getArrivalTime();

        List<RideShare> overlappingRides = rideShareRepository.findBySimilarPeriod(organizerId,
                newDepartureTime, newArrivalTime);
        if (!overlappingRides.isEmpty()) {
            throw new IllegalArgumentException("Vous avez déjà créé un covoiturage pendant cette période.");
        }

        Employee organizer = employeeRepository.findById(organizerId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non reconnu "));

        // Vérification et récupération du véhicule
        PrivateVehicleDTO vehicleDTO = rideShareDTO.getVehicle(); // Le véhicule est passé dans le corps de la requête

        // Vérifiez que le véhicule est fourni dans le DTO
        if (vehicleDTO == null) {
            throw new IllegalArgumentException("Un véhicule doit être spécifié.");
        }

        // Utilisez privateVehicleRepository.findById pour récupérer le véhicule basé
        // sur l'ID
        Vehicle vehicle = privateVehicleRepository.findById(vehicleDTO.getId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Véhicule non reconnu avec l'ID : " + vehicleDTO.getId()));

        // Mettez à jour le véhicule dans le DTO
        rideShareDTO.setVehicle(privateVehicleMapper.toDTO(vehicle));

        // Création de l'entité RideShare avec l'organisateur et le véhicule
        RideShare rideShare = rideShareMapper.toEntity(rideShareDTO);
        rideShare.setOrganizer(organizer); // Associez l'organisateur au covoiturage

        RideShare savedRideShare = rideShareRepository.save(rideShare);

        return rideShareMapper.toDTO(savedRideShare);
    }

    public RideShareDTO updateRideShare(int id, int organizerId, RideShareDTO rideShareDTO) {
        Optional<RideShare> optionalRideShare = rideShareRepository.findById(id);
        if (!optionalRideShare.isPresent()) {
            throw new EntityNotFoundException("Ce covoiturage n'existe pas.");
        }

        RideShare existingRideShare = optionalRideShare.get();

        if (existingRideShare.getOrganizer().getId() != organizerId) {
            throw new EntityNotFoundException("Vous n'êtes pas autorisé à mettre à jour ce covoiturage.");
        }

        if (existingRideShare.getPassengers() != null && !existingRideShare.getPassengers().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ce covoiturage ne peut pas être modifié car il y a déjà des passagers.");
        }

        rideShareDTO.setId(id);
        return createNewRideShare(rideShareDTO);
    }

    public RideShareDTO deleteById(Integer id, int organizerId) {
        // Récupérer le covoiturage ou lever une exception s'il n'existe pas
        RideShare rideShare = rideShareRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ce covoiturage n'existe pas"));

        // Vérifier les droits de l'organisateur
        if (rideShare.getOrganizer().getId() != organizerId) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à supprimer ce covoiturage.");
        }

        // Marquer le covoiturage comme supprimé
        rideShare.setDeleted(true);
        rideShareRepository.save(rideShare);

        // Retourner le DTO correspondant
        return rideShareMapper.toDTO(rideShare);
    }

    public RideShareDTO addPassengerToRideShare(int rideShareId, int employeeId) {
        RideShare rideShare = rideShareRepository.findById(rideShareId)
                .orElseThrow(() -> new EntityNotFoundException("Ce covoiturage n'existe pas."));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non reconnu."));

        if (rideShare.getOrganizer().getId() == employeeId) {
            throw new IllegalArgumentException("Vous êtes l'organisateur de ce covoiturage.");
        }

        if (rideShare.getPassengers().contains(employee)) {
            throw new IllegalArgumentException("Vous êtes déjà inscrit en tant que passager de ce covoiturage.");
        }

        rideShare.getPassengers().add(employee);
        rideShare.setAvailableSeats(rideShare.getAvailableSeats() - 1);
        RideShare updatedRideShare = rideShareRepository.save(rideShare);

        return rideShareMapper.toDTO(updatedRideShare);
    }

    public RideShareDTO cancelPassengerParticipation(int rideShareId, int employeeId) {
        RideShare rideShare = rideShareRepository.findById(rideShareId)
                .orElseThrow(() -> new EntityNotFoundException("Ce covoiturage n'existe pas."));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Cet utilisateur n'existe pas."));

        if (!rideShare.getPassengers().contains(employee)) {
            throw new IllegalArgumentException("Vous n'êtes pas passager de ce covoiturage.");
        }

        rideShare.getPassengers().remove(employee);
        rideShare.setAvailableSeats(rideShare.getAvailableSeats() + 1);
        RideShare updatedRideShare = rideShareRepository.save(rideShare);

        return rideShareMapper.toDTO(updatedRideShare);
    }

    public List<RideShareBasicDTO> findByAddresses(
            String departureCity,
            String arrivalCity,
            LocalDateTime departureDateTime) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        if (departureCity == null && arrivalCity == null) {
            throw new IllegalArgumentException(
                    "Vous devez préciser au moins la ville de départ ou la ville d'arrivée.");
        }

        if (departureDateTime != null && departureDateTime.isBefore(currentDateTime)) {
            throw new IllegalArgumentException("La date et l'heure de départ doivent être à venir.");
        }

        List<RideShare> rideShares = rideShareRepository.findByDepartureCityAndArrivalCityAndDate(
                departureCity,
                arrivalCity,
                currentDateTime,
                departureDateTime);

        return rideShares.stream()
                .sorted(Comparator.comparing(RideShare::getDepartureTime)) // Tri dates
                .map(rideShareBasicMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RideShareDTO> findRideSharesByOrganizerIdAndTime(Integer organizerId, boolean past) {
        LocalDateTime now = LocalDateTime.now();
        List<RideShare> rideShares;

        employeeRepository.findById(organizerId)
                .orElseThrow(() -> new EntityNotFoundException("Cet utilisateur n'existe pas."));

        if (past) {
            rideShares = rideShareRepository.findByOrganizerIdAndArrivalBefore(organizerId, now);

        } else {
            rideShares = rideShareRepository.findByOrganizerIdAndDepartureAfter(organizerId, now);
        }
        return rideShares.stream()
                .sorted(Comparator.comparing(RideShare::getDepartureTime)) // Tri dates
                .map(rideShareMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RideShareDTO> findRideSharesByPassengerIdAndTime(Integer passengerId, boolean past) {
        LocalDateTime now = LocalDateTime.now();
        List<RideShare> rideShares;

        employeeRepository.findById(passengerId)
                .orElseThrow(() -> new EntityNotFoundException("Cet utilisateur n'existe pas."));

        if (past) {
            rideShares = rideShareRepository.findByPassengerIdAndArrivalBefore(passengerId, now);
        } else {
            rideShares = rideShareRepository.findByPassengerIdAndDepartureAfter(passengerId, now);
        }

        return rideShares.stream()
                .sorted(Comparator.comparing(RideShare::getDepartureTime)) // Tri dates
                .map(rideShareMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RideShareDTO getRideShareById(int id) throws MessageException {
        RideShare rideShare = rideShareRepository.findById(id)
                .orElseThrow(() -> new MessageException("Ce covoiturage n'existe pas."));

        return rideShareMapper.toDTO(rideShare);
    }

}
