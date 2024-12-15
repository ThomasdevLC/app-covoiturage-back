package diginamic.fr.app_covoiturage.repositories;

import diginamic.fr.app_covoiturage.models.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyVehicleRepository extends CrudRepository<Vehicle, Integer> {

        @Query("SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE' AND v.type = 'COMPANY' AND v.isDeleted = false")
        List<Vehicle> findByStatus();

        @Query("SELECT v FROM Vehicle v WHERE v.brand = :brand AND v.type = 'COMPANY' AND v.isDeleted = false")
        List<Vehicle> findByBrand(@Param("brand") String brand);

        @Query("SELECT v FROM Vehicle v WHERE v.number = :number AND v.type = 'COMPANY' AND v.isDeleted = false")
        Optional<Vehicle> findByNumber(@Param("number") String number);

        @Query("SELECT v FROM Vehicle v WHERE v.type = 'COMPANY' AND v.isDeleted = false")
        List<Vehicle> findAll();

        @Query("SELECT v FROM Vehicle v WHERE v.id = :id AND v.isDeleted = false")
        Optional<Vehicle> findById(@Param("id") int id);

        @Query("SELECT v FROM Vehicle v " +
                        "WHERE v.status = 'AVAILABLE' AND v.type = 'COMPANY' AND v.isDeleted = false " +
                        "AND NOT EXISTS (SELECT b FROM VehicleBooking b " +
                        "WHERE b.companyVehicle = v " +
                        "AND (:startTime <= b.endTime AND :endTime >= b.startTime))")
        List<Vehicle> findByStatusAndBookingDates(
                        @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

        @Query("SELECT COUNT(b) > 0 FROM VehicleBooking b " +
                        "WHERE b.companyVehicle.id = :vehicleId " +
                        "AND b.isDeleted = false " +
                        "AND b.startTime >= CURRENT_TIMESTAMP")
        boolean isVehicleLinkedToBooking(@Param("vehicleId") int vehicleId);

}
