package diginamic.fr.app_covoiturage.mapper.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import diginamic.fr.app_covoiturage.dto.address.AddressRideSharesDTO;
import diginamic.fr.app_covoiturage.mapper.rideshare.RideShareBasicMapper;
import diginamic.fr.app_covoiturage.models.Address;

import java.util.stream.Collectors;

/**
 * Mapper class for converting between Address and AddressRideSharesDTO objects.
 * This class provides methods to map Address entities to their corresponding
 * AddressRideSharesDTO representations and vice versa.
 *
 * Responsibilities:
 * - To transform an Address entity into a DTO by including its associated data
 *   such as basic address properties and its ride-sharing departures and arrivals.
 * - To transform an AddressRideSharesDTO object back into an Address entity while excluding ride-sharing data.
 *
 * Features:
 * - Uses the RideShareBasicMapper to handle the conversion of ride-sharing departures and arrivals.
 * - Ensures correct mapping of associated ride-sharing data where applicable.
 *
 * Methods:
 * - toDTO(Address address): Converts an Address entity into an AddressRideSharesDTO.
 * - toEntity(AddressRideSharesDTO addressDTO): Converts an AddressRideSharesDTO into an Address entity.
 *
 * Dependencies:
 * - Relies on the RideShareBasicMapper to map ride share details between entity and DTO forms.
 *
 * Notes:
 * - Null checks are in place for optional fields to prevent null pointer exceptions.
 * - The toEntity method does not map ride-sharing data from the DTO to the Address entity.
 */
@Component
public class AddressRideSharesMapper {

    @Autowired
    private RideShareBasicMapper rideShareBasicMapper;

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
