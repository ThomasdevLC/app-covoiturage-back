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

        List<VehicleBooking> findByStartTimeAndIsDeletedFalse(LocalDateTime startTime);

        List<VehicleBooking> findByEndTimeAndIsDeletedFalse(LocalDateTime endTime);

        List<VehicleBooking> findByEmployeeIdAndIsDeletedFalse(int employeeId);

        List<VehicleBooking> findByCompanyVehicleIdAndIsDeletedFalse(int vehicleId);

        List<VehicleBooking> findAllByIsDeletedFalse();

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime >= :now AND vb.isDeleted = false")
        List<VehicleBooking> findCurrentByEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime < :now AND vb.isDeleted = false")
        List<VehicleBooking> findHistoryByEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.companyVehicle.id = :vehicleId " +
                        "AND vb.isDeleted = false " +
                        "AND ((:startTime BETWEEN vb.startTime AND vb.endTime) " +
                        "OR (:endTime BETWEEN vb.startTime AND vb.endTime) " +
                        "OR (vb.startTime BETWEEN :startTime AND :endTime))")
        Optional<VehicleBooking> findByBookingPeriod(@Param("vehicleId") int vehicleId,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime < :now AND vb.isDeleted = false")
        List<VehicleBooking> getPastBookingsByEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.employee.id = :employeeId AND vb.startTime > :now AND vb.isDeleted = false")
        List<VehicleBooking> getFutureBookingsByEmployeeId(@Param("employeeId") int employeeId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.endTime < :now AND vb.isDeleted = false")
        List<VehicleBooking> findAllPastBookings(@Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.startTime > :now AND vb.isDeleted = false")
        List<VehicleBooking> findAllFutureBookings(@Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.startTime <= :now AND vb.endTime >= :now AND vb.isDeleted = false")
        List<VehicleBooking> findAllCurrentBookings(@Param("now") LocalDateTime now);

        @Query("SELECT vb FROM VehicleBooking vb WHERE vb.companyVehicle.id = :vehicleId AND vb.isDeleted = false AND vb.startTime > :now")
        List<VehicleBooking> findFutureBookingsByVehicleId(@Param("vehicleId") int vehicleId,
                        @Param("now") LocalDateTime now);

}
