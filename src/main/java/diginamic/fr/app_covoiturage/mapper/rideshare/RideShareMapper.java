package diginamic.fr.app_covoiturage.mapper.rideshare;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.rideshare.RideShareDTO;
import diginamic.fr.app_covoiturage.mapper.address.AddressMapper;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.mapper.vehicle.PrivateVehicleMapper;
import diginamic.fr.app_covoiturage.models.RideShare;

import java.util.stream.Collectors;

@Component
public class RideShareMapper {

    private final AddressMapper addressMapper;
    private final EmployeeMapper employeeMapper;
    private final PrivateVehicleMapper privateVehicleMapper;

    public RideShareMapper(AddressMapper addressMapper, EmployeeMapper employeeMapper,
            PrivateVehicleMapper privateVehicleMapper) {
        this.addressMapper = addressMapper;
        this.employeeMapper = employeeMapper;
        this.privateVehicleMapper = privateVehicleMapper;
    }

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
        return rideShare;
    }
}