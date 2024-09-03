package diginamic.fr.app_covoiturage.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.address.AddressDTO;
import diginamic.fr.app_covoiturage.dto.rideshare.RideShareBasicDTO;
import diginamic.fr.app_covoiturage.dto.rideshare.RideShareDTO;
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

        Integer organizerId = rideShareDTO.getOrganizer().getId();

        // VERIFICATION Covoiturage pendant cette période //
        // LocalDateTime newDepartureTime = rideShareDTO.getDepartureTime();
        // LocalDateTime newArrivalTime = rideShareDTO.getArrivalTime();

        // List<RideShare> overlappingRides =
        // rideShareRepository.findBySimilarPeriod(organizerId,
        // newDepartureTime, newArrivalTime);
        // if (!overlappingRides.isEmpty()) {
        // throw new IllegalArgumentException("Vous avez déjà créé un covoiturage
        // pendant cette période.");
        // }

        Employee organizer = employeeRepository.findById(organizerId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non reconnu."));
        Vehicle vehicle = organizer.getVehicle().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucun véhicule ne correspond à cet utilisateur."));
        rideShareDTO.setVehicle(privateVehicleMapper.toDTO(vehicle));

        RideShare rideShare = rideShareMapper.toEntity(rideShareDTO);
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
        Optional<RideShare> optionalRideShare = rideShareRepository.findById(id);
        if (!optionalRideShare.isPresent()) {
            throw new EntityNotFoundException("Ce covoiturage n'existe pas");
        }

        RideShare rideShare = optionalRideShare.get();

        if (rideShare.getOrganizer().getId() != organizerId) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à supprimer ce covoiturage.");
        }

        RideShareDTO rideShareDTO = rideShareMapper.toDTO(rideShare);
        rideShareRepository.deleteById(id);
        return rideShareDTO;
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
                .map(rideShareMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ----------------------------------------------------//

    public Optional<RideShareDTO> getRideShareById(int id) {
        return rideShareRepository.findById(id)
                .map(rideShareMapper::toDTO);
    }
}
