package diginamic.fr.app_covoiturage.mapper.address;

import diginamic.fr.app_covoiturage.dto.address.AddressDTO;
import diginamic.fr.app_covoiturage.models.Address;
import org.springframework.stereotype.Component;

/**
 * Component responsible for mapping between Address entity and AddressDTO objects.
 * This class provides methods for converting between the entity and DTO representations,
 * facilitating the transfer of address-related data between different layers of the application.
 *
 * Methods:
 * - {@code toDTO(Address address)}: Maps an Address entity to its AddressDTO representation. Returns null if the input is null.
 * - {@code toEntity(AddressDTO addressDTO)}: Maps an AddressDTO to its Address entity representation. Returns null if the input is null.
 */
@Component
public class AddressMapper {

    public AddressDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }

        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setNumber(address.getNumber());
        dto.setStreet(address.getStreet());
        dto.setCode(address.getCode());
        dto.setCity(address.getCity());

        return dto;
    }

    public Address toEntity(AddressDTO addressDTO) {
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
