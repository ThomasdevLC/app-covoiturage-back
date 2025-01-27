package diginamic.fr.app_covoiturage.mapper.rideshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import diginamic.fr.app_covoiturage.dto.rideshare.RideshareUpdateDTO;
import diginamic.fr.app_covoiturage.mapper.address.AddressMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.RideShare;


/**
 * Component responsible for mapping between RideShare entities and RideshareUpdateDTO objects.
 * This class facilitates the conversion of RideShare entity data to RideshareUpdateDTO and vice versa,
 * enabling seamless data transfer between different layers of the application during rideshare updates.
 *
 * Responsibilities:
 * - Mapping a RideShare entity to a RideshareUpdateDTO object, while handling null safety.
 * - Mapping a RideshareUpdateDTO object to a RideShare entity, ensuring all necessary fields
 *   are converted properly, including nested mappings through AddressMapper.
 *
 * Dependencies:
 * - AddressMapper: Converts departure and arrival addresses between Address entities and AddressDTO objects.
 *
 * Methods:
 * - {@code toDTO(RideShare rideShare)}: Maps the provided RideShare entity to a RideshareUpdateDTO
 *   object. Returns null if the input is null. Copies departure and arrival times, addresses,
 *   organizer ID, and available seats.
 * - {@code toEntity(RideshareUpdateDTO rideshareUpdateDTO)}: Maps the provided RideshareUpdateDTO
 *   object to a RideShare entity. Returns null if the input is null. Converts applicable fields and
 *   creates a new organizer Employee entity with the specified ID.
 */
@Component
public class RideshareUpdateMapper {

    @Autowired
    private AddressMapper addressMapper;

    public RideshareUpdateDTO toDTO(RideShare rideShare) {
        if (rideShare == null) {
            return null;
        }

        RideshareUpdateDTO dto = new RideshareUpdateDTO();
        dto.setDepartureTime(rideShare.getDepartureTime());
        dto.setArrivalTime(rideShare.getArrivalTime());
        dto.setDepartureAddress(addressMapper.toDTO(rideShare.getDepartureAddress()));
        dto.setArrivalAddress(addressMapper.toDTO(rideShare.getArrivalAddress()));

        if (rideShare.getOrganizer() != null) {
            dto.setOrganizerId(rideShare.getOrganizer().getId());
        }

        dto.setAvailableSeats(rideShare.getAvailableSeats());
        return dto;
    }

    public RideShare toEntity(RideshareUpdateDTO rideshareUpdateDTO) {
        if (rideshareUpdateDTO == null) {
            return null;
        }

        RideShare rideShare = new RideShare();
        rideShare.setDepartureTime(rideshareUpdateDTO.getDepartureTime());
        rideShare.setArrivalTime(rideshareUpdateDTO.getArrivalTime());
        rideShare.setDepartureAddress(addressMapper.toEntity(rideshareUpdateDTO.getDepartureAddress()));
        rideShare.setArrivalAddress(addressMapper.toEntity(rideshareUpdateDTO.getArrivalAddress()));

        Employee organizer = new Employee();
        organizer.setId(rideshareUpdateDTO.getOrganizerId());
        rideShare.setOrganizer(organizer);

        rideShare.setAvailableSeats(rideshareUpdateDTO.getAvailableSeats());
        return rideShare;
    }
}