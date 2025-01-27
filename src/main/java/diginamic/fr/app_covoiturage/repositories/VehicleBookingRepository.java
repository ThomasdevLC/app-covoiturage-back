package diginamic.fr.app_covoiturage.repositories;

import diginamic.fr.app_covoiturage.models.VehicleBooking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@code VehicleBooking} entities.
 * Provides CRUD operations and specific methods to retrieve vehicle bookings
 * based on different criteria and conditions such as time periods,
 * employee associations, or vehicle associations.
 *
 * The repository leverages Spring Data JPA's {@code CrudRepository} to
 * provide out-of-the-box data handling features while also including custom
 * queries for specific use cases.
 *
 * Methods:
 * - Retrieve bookings by their start time or end time, excluding deleted records.
 * - Find bookings associated with a specific employee or vehicle, excluding deleted records.
 * - Retrieve all bookings that are not marked as deleted.
 * - Retrieve current, historical, future, or overlapping bookings for a specific employee or vehicle.
 * - Fetch lists of bookings based on time periods such as past, current, or future bookings.
 */
@Repository
public interface VehicleBookingRepository extends CrudRepository<VehicleBooking, Integer> {


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
