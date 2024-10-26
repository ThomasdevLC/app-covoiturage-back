package diginamic.fr.app_covoiturage.mapper.rideshare;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.rideshare.RideshareUpdateDTO;
import diginamic.fr.app_covoiturage.mapper.address.AddressMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.RideShare;

@Component
public class RideshareUpdateMapper {

    private final AddressMapper addressMapper;

    public RideshareUpdateMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

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