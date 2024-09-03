package diginamic.fr.app_covoiturage.mapper.address;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.address.AddressRideSharesDTO;
import diginamic.fr.app_covoiturage.mapper.rideshare.RideShareBasicMapper;
import diginamic.fr.app_covoiturage.models.Address;

import java.util.stream.Collectors;

@Component
public class AddressRideSharesMapper {

    private final RideShareBasicMapper rideShareBasicMapper;

    public AddressRideSharesMapper(RideShareBasicMapper rideShareBasicMapper) {
        this.rideShareBasicMapper = rideShareBasicMapper;
    }

    public AddressRideSharesDTO toDTO(Address address) {

        AddressRideSharesDTO dto = new AddressRideSharesDTO();
        dto.setId(address.getId());
        dto.setNumber(address.getNumber());
        dto.setStreet(address.getStreet());
        dto.setCode(address.getCode());
        dto.setCity(address.getCity());

        if (address.getDepartures() != null) {
            dto.setRideShareDepartures(
                    address.getDepartures().stream()
                            .map(rideShareBasicMapper::toDTO)
                            .collect(Collectors.toList()));
        }

        if (address.getArrivals() != null) {
            dto.setRideShareArrivals(
                    address.getArrivals().stream()
                            .map(rideShareBasicMapper::toDTO)
                            .collect(Collectors.toList()));
        }

        return dto;
    }

    public Address toEntity(AddressRideSharesDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }

        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCode(addressDTO.getCode());
        address.setCity(addressDTO.getCity());

        return address;
    }
}
