package diginamic.fr.app_covoiturage.repositories;

import java.util.Optional;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Vehicle;

@Repository

public interface PrivateVehicleRepository extends
        CrudRepository<Vehicle, Integer> {
        Optional<Vehicle> findByNumber(String number);
}

