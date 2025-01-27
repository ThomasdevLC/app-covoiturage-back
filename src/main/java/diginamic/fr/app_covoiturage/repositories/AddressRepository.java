package diginamic.fr.app_covoiturage.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Address;

/**
 * AddressRepository interface for performing CRUD operations on Address entities.
 *
 * This repository extends the CrudRepository interface provided by Spring Data,
 * which enables basic CRUD operations on the Address entity. Additionally, custom
 * query methods are defined to retrieve specific Address entities based on their
 * attributes.
 *
 * Methods:
 * - findById(int id): Retrieves an Address entity by its unique identifier.
 * - findByNumberAndStreetAndCity(int number, String street, String city): Retrieves
 *   an Address entity based on its street number, street name, and city.
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    Optional<Address> findById(int id);
    Optional<Address> findByNumberAndStreetAndCity(int number, String street, String city);
}
