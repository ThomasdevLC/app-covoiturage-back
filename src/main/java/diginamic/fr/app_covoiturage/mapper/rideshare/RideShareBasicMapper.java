package diginamic.fr.app_covoiturage.mapper.rideshare;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.rideshare.RideShareBasicDTO;
import diginamic.fr.app_covoiturage.mapper.address.AddressMapper;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.RideShare;

@Component
public class RideShareBasicMapper {

    private final AddressMapper addressMapper;
    private final EmployeeMapper employeeMapper;
    private final PrivateVehicleMapper privateVehicleMapper;

    public RideShareBasicMapper(AddressMapper addressMapper, EmployeeMapper employeeMapper,
            PrivateVehicleMapper privateVehicleMapper) {
        this.addressMapper = addressMapper;
        this.employeeMapper = employeeMapper;
        this.privateVehicleMapper = privateVehicleMapper;
    }

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

        return rideShare;
    }
}