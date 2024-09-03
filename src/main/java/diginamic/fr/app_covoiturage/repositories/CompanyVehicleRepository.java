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

        @Query("SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE' AND v.type = 'COMPANY'")
        List<Vehicle> findByStatus();

        @Query("SELECT v FROM Vehicle v WHERE v.brand = :brand AND v.type = 'COMPANY'")
        List<Vehicle> findByBrand(@Param("brand") String brand);

        @Query("SELECT v FROM Vehicle v WHERE v.number = :number AND v.type = 'COMPANY'")
        Optional<Vehicle> findByNumber(@Param("number") String number);

        @Query("SELECT v FROM Vehicle v WHERE v.type = 'COMPANY'")
        List<Vehicle> findAll();

        Optional<Vehicle> findById(int number);

        @Query("SELECT v FROM Vehicle v " +
                        "WHERE v.status = 'AVAILABLE' AND v.type = 'COMPANY' " +
                        "AND NOT EXISTS (SELECT b FROM VehicleBooking b " +
                        "WHERE b.companyVehicle = v " +
                        "AND (:startTime <= b.endTime AND :endTime >= b.startTime))")
        List<Vehicle> findByStatusAndBookingDates(
                        @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
