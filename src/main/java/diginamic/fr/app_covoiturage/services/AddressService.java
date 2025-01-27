package diginamic.fr.app_covoiturage.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.repositories.AddressRepository;
import diginamic.fr.app_covoiturage.models.Address;

/**
 * Service class for handling business logic related to the Address entity.
 *
 * This class provides methods for basic storage, retrieval, and management
 * of Address objects within the application by interacting with the AddressRepository.
 * The main functionalities include saving an address, finding an address by its ID,
 * and ensuring that an address is either created or retrieved if it already exists.
 *
 * The class is annotated with @Service to indicate that it is a Spring service,
 * allowing for dependency injection and integration into the application context.
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address save(Address address) {

        return addressRepository.save(address);
    }

    public Optional<Address> findById(int id) {
        return addressRepository.findById(id);
    }

    public Address createOrGetAddress(Address address) {
        Optional<Address> existingAddress = addressRepository.findByNumberAndStreetAndCity(
                address.getNumber(), address.getStreet(), address.getCity());

        return existingAddress.orElseGet(() -> addressRepository.save(address));
    }

}
