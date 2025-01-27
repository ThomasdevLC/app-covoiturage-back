package diginamic.fr.app_covoiturage.mapper.rideshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.rideshare.RideShareBasicDTO;
import diginamic.fr.app_covoiturage.mapper.address.AddressMapper;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.RideShare;

/**
 * A component responsible for mapping RideShare entity objects to RideShareBasicDTO objects and vice versa.
 *
 * This mapper facilitates the conversion between the entity and DTO representations,
 * ensuring data transfer occurs seamlessly between different layers of the application.
 * It utilizes other mappers such as AddressMapper, EmployeeMapper, and PrivateVehicleMapper
 * to handle nested fields during the mapping process.
 *
 * Key Functionalities:
 * - Converts a RideShare entity to a RideShareBasicDTO object.
 * - Converts a RideShareBasicDTO object to a RideShare entity.
 *
 * Uses:
 * - AddressMapper: Maps between Address and AddressDTO within RideShare attributes.
 * - EmployeeMapper: Maps between Employee and EmployeeDTO for the organizer field.
 * - PrivateVehicleMapper: Maps between Vehicle and PrivateVehicleDTO for the vehicle field.
 *
 * Mapping Details:
 * - id: Maps the unique identifier of the ride share.
 * - departureTime: Maps the departure time of the ride share.
 * - arrivalTime: Maps the arrival time of the ride share.
 * - departureAddress: Uses AddressMapper to map between Address and AddressDTO.
 * - arrivalAddress: Uses AddressMapper to map between Address and AddressDTO.
 * - organizer: Uses EmployeeMapper to map between Employee and EmployeeDTO.
 * - availableSeats: Maps the number of available seats in the ride share.
 * - vehicle: Uses PrivateVehicleMapper to map between Vehicle and PrivateVehicleDTO.
 * - deleted: Maps the deleted status of the ride share.
 */
@Component
public class RideShareBasicMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PrivateVehicleMapper privateVehicleMapper;

    public RideShareBasicDTO toDTO(RideShare rideShare) {

        RideShareBasicDTO dto = new RideShareBasicDTO();
        dto.setId(rideShare.getId());
        dto.setDepartureTime(rideShare.getDepartureTime());
        dto.setArrivalTime(rideShare.getArrivalTime());
        dto.setDepartureAddress(addressMapper.toDTO(rideShare.getDepartureAddress()));
        dto.setArrivalAddress(addressMapper.toDTO(rideShare.getArrivalAddress()));
        dto.setOrganizer(employeeMapper.toDTO(rideShare.getOrganizer()));
        dto.setAvailableSeats(rideShare.getAvailableSeats());
        dto.setVehicle(privateVehicleMapper.toDTO(rideShare.getVehicle()));
        dto.setDeleted(rideShare.isDeleted());
        return dto;
    }

    public RideShare toEntity(RideShareBasicDTO rideShareDTO) {

        RideShare rideShare = new RideShare();
        rideShare.setId(rideShareDTO.getId());
        rideShare.setDepartureTime(rideShareDTO.getDepartureTime());
        rideShare.setArrivalTime(rideShareDTO.getArrivalTime());
        rideShare.setDepartureAddress(addressMapper.toEntity(rideShareDTO.getDepartureAddress()));
        rideShare.setArrivalAddress(addressMapper.toEntity(rideShareDTO.getArrivalAddress()));
        rideShare.setOrganizer(employeeMapper.toEntity(rideShareDTO.getOrganizer()));
        rideShare.setAvailableSeats(rideShareDTO.getAvailableSeats());
        rideShare.setVehicle(privateVehicleMapper.toEntity(rideShareDTO.getVehicle()));
        rideShare.setDeleted(rideShareDTO.isDeleted());
        return rideShare;
    }
}