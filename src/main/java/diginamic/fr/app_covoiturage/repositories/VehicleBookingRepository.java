package diginamic.fr.app_covoiturage.repositories;

import diginamic.fr.app_covoiturage.models.VehicleBooking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleBookingRepository extends CrudRepository<VehicleBooking, Integer> {

        List<VehicleBooking> findByStartTime(LocalDateTime startTime);

        List<VehicleBooking> findByEndTime(LocalDateTime endTime);

        List<VehicleBooking> findByEmployeeId(int employeeId);

        List<VehicleBooking> findByCompanyVehicleId(int vehicleId);

        List<VehicleBooking> findAll();

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime >= :now")
        List<VehicleBooking> findCurrentByEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime < :now ")
        List<VehicleBooking> findHistoryEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.companyVehicle.id = :vehicleId " +
                        "AND ((:startTime BETWEEN vb.startTime AND vb.endTime) " +
                        "OR (:endTime BETWEEN vb.startTime AND vb.endTime) " +
                        "OR (vb.startTime BETWEEN :startTime AND :endTime))")
        Optional<VehicleBooking> findByBookingPeriod(int vehicleId, LocalDateTime startTime, LocalDateTime endTime);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime < :now")
        List<VehicleBooking> getPastBookingsByEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime > :now")
        List<VehicleBooking> getFuturesBookingsByEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.endTime < :now")
        List<VehicleBooking> findAllPastBookings(@Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.startTime > :now")
        List<VehicleBooking> findAllFutureBookings(@Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.startTime <= :now AND vb.endTime >= :now")
        List<VehicleBooking> findAllCurrentBookings(@Param("now") LocalDateTime now);

}
