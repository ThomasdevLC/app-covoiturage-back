package diginamic.fr.app_covoiturage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Vehicle;

@Repository

public interface PrivateVehicleRepository extends
                CrudRepository<Vehicle, Integer> {
        Optional<Vehicle> findByNumber(String number);

        Optional<Vehicle> findById(int number);

        @Query("SELECT v FROM Vehicle v WHERE v.employee.id = :employeeId")
        List<Vehicle> findVehiclesByEmployeeId(@Param("employeeId") int employeeId);

}
