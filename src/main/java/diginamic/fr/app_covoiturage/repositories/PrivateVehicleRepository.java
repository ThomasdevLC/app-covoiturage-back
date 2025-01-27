package diginamic.fr.app_covoiturage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Vehicle;

/**
 * Repository interface for managing Private Vehicles within the application.
 * Provides database access and query methods for the Vehicle entity.
 */
@Repository

public interface PrivateVehicleRepository extends CrudRepository<Vehicle, Integer> {

        @Query("SELECT v FROM Vehicle v WHERE v.number = :number AND v.isDeleted = false")
        Optional<Vehicle> findByNumber(@Param("number") String number);

        @Query("SELECT v FROM Vehicle v WHERE v.id = :id AND v.isDeleted = false")
        Optional<Vehicle> findById(@Param("id") int id);

        @Query("SELECT v FROM Vehicle v WHERE v.employee.id = :employeeId AND v.type = 'PRIVATE' AND v.isDeleted = false")
        List<Vehicle> findVehiclesByEmployeeId(@Param("employeeId") int employeeId);

        @Query("SELECT COUNT(r) > 0 FROM RideShare r " +
                        "WHERE r.vehicle.id = :vehicleId " +
                        "AND r.isDeleted = false " +
                        "AND r.departureTime > CURRENT_TIMESTAMP")
        boolean isVehicleLinkedToRideShare(@Param("vehicleId") int vehicleId);
}
