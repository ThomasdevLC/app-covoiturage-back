package diginamic.fr.app_covoiturage.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    Optional<Address> findById(int id);
    Optional<Address> findByNumberAndStreetAndCity(int number, String street, String city);
}
