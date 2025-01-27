package diginamic.fr.app_covoiturage.mapper.rideshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.rideshare.RideShareDTO;
import diginamic.fr.app_covoiturage.mapper.address.AddressMapper;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeProfileMapper;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.RideShare;

import java.util.stream.Collectors;

/**
 * Component responsible for mapping between `RideShare` entity objects and `RideShareDTO` objects.
 *
 * This class simplifies the transformation of `RideShare` data between the persistence layer and
 * the data transfer object (DTO). It ensures a consistent mapping of the relevant attributes and
 * associated entities, leveraging other mappers to handle nested data transformations.
 *
 * Features:
 * - Conversion from `RideShare` entity to `RideShareDTO`.
 * - Conversion from `RideShareDTO` back to `RideShare` entity.
 * - Handles nested mappings for associated objects such as addresses, organizer profiles, vehicles,
 *   and passengers using `AddressMapper`, `EmployeeProfileMapper`, and `PrivateVehicleMapper`.
 *
 * Responsibilities:
 * - Preserves relationships between ride shares and their attributes including departure and
 *   arrival addresses, organizer, available seats, passengers, and vehicles.
 * - Ensures the proper handling of collections, such as mapping a list of passengers, during
 *   conversion processes.
 *
 * Dependency:
 * This class depends on:
 * - `AddressMapper` for mapping address-related data.
 * - `EmployeeProfileMapper` for mapping employee-related data associated with organizers and passengers.
 * - `PrivateVehicleMapper` for mapping vehicle-related data.
 *
 * Methods:
 * - `toDTO(RideShare rideShare)`: Converts a `RideShare` entity to a `RideShareDTO` representation. Handles
 *   nested mappings for associated addresses, employee profiles, vehicles, and passengers.
 * - `toEntity(RideShareDTO rideShareDTO)`: Converts a `RideShareDTO` to a `RideShare` entity. Handles
 *   nested mappings for reconstructing entities for associated addresses, employees, vehicles, and passengers.
 */
@Component
public class RideShareMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EmployeeProfileMapper employeeMapper;

    @Autowired
    private PrivateVehicleMapper privateVehicleMapper;

    public RideShareDTO toDTO(RideShare rideShare) {

        RideShareDTO dto = new RideShareDTO();
        dto.setId(rideShare.getId());
        dto.setDepartureTime(rideShare.getDepartureTime());
        dto.setArrivalTime(rideShare.getArrivalTime());
        dto.setDepartureAddress(addressMapper.toDTO(rideShare.getDepartureAddress()));
        dto.setArrivalAddress(addressMapper.toDTO(rideShare.getArrivalAddress()));
        dto.setOrganizer(employeeMapper.toDTO(rideShare.getOrganizer()));
        dto.setAvailableSeats(rideShare.getAvailableSeats());
        dto.setVehicle(privateVehicleMapper.toDTO(rideShare.getVehicle()));
        if (rideShare.getPassengers() != null) {
            dto.setPassengers(rideShare.getPassengers().stream()
                    .map(employeeMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        dto.setDeleted(rideShare.isDeleted());

        return dto;
    }

    public RideShare toEntity(RideShareDTO rideShareDTO) {

        RideShare rideShare = new RideShare();
        rideShare.setId(rideShareDTO.getId());
        rideShare.setDepartureTime(rideShareDTO.getDepartureTime());
        rideShare.setArrivalTime(rideShareDTO.getArrivalTime());
        rideShare.setDepartureAddress(addressMapper.toEntity(rideShareDTO.getDepartureAddress()));
        rideShare.setArrivalAddress(addressMapper.toEntity(rideShareDTO.getArrivalAddress()));
        rideShare.setOrganizer(employeeMapper.toEntity(rideShareDTO.getOrganizer()));
        rideShare.setAvailableSeats(rideShareDTO.getAvailableSeats());
        rideShare.setVehicle(privateVehicleMapper.toEntity(rideShareDTO.getVehicle()));
        if (rideShareDTO.getPassengers() != null) {
            rideShare.setPassengers(rideShareDTO.getPassengers().stream()
                    .map(employeeMapper::toEntity)
                    .collect(Collectors.toList()));
        }
        rideShare.setDeleted(rideShareDTO.isDeleted());
        return rideShare;
    }
}