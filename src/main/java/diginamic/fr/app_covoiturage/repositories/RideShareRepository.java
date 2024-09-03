package diginamic.fr.app_covoiturage.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import diginamic.fr.app_covoiturage.models.RideShare;

public interface RideShareRepository extends CrudRepository<RideShare, Integer> {

        Optional<RideShare> findById(int id);

        @Query("SELECT r FROM RideShare r WHERE r.organizer.id = :organizerId AND " +
                        "((r.departureTime < :newArrivalTime AND r.arrivalTime > :newDepartureTime) OR " +
                        "(r.departureTime <= :newDepartureTime AND r.arrivalTime >= :newDepartureTime) OR " +
                        "(r.departureTime <= :newArrivalTime AND r.arrivalTime >= :newArrivalTime))")
        List<RideShare> findBySimilarPeriod(
                        @Param("organizerId") Integer organizerId,
                        @Param("newDepartureTime") LocalDateTime newDepartureTime,
                        @Param("newArrivalTime") LocalDateTime newArrivalTime);

        @Query("SELECT r FROM RideShare r WHERE " +
                        "(:departureCity IS NULL OR r.departureAddress.city = :departureCity) " +
                        "AND (:arrivalCity IS NULL OR r.arrivalAddress.city = :arrivalCity) " +
                        "AND r.departureTime > :currentDateTime " +
                        "AND (:departureDateTime IS NULL OR r.departureTime >= :departureDateTime) " +
                        "ORDER BY r.departureTime ASC")
        List<RideShare> findByDepartureCityAndArrivalCityAndDate(
                        @Param("departureCity") String departureCity,
                        @Param("arrivalCity") String arrivalCity,
                        @Param("currentDateTime") LocalDateTime currentDateTime,
                        @Param("departureDateTime") LocalDateTime departureDateTime);

        @Query("SELECT r FROM RideShare r WHERE r.organizer.id = :organizerId AND r.arrivalTime < :now")
        List<RideShare> findByOrganizerIdAndArrivalBefore(@Param("organizerId") Integer organizerId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT r FROM RideShare r WHERE r.organizer.id = :organizerId AND r.departureTime > :now")
        List<RideShare> findByOrganizerIdAndDepartureAfter(@Param("organizerId") Integer organizerId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT r FROM RideShare r JOIN r.passengers p WHERE p.id = :passengerId AND r.arrivalTime < :now")
        List<RideShare> findByPassengerIdAndArrivalBefore(@Param("passengerId") Integer passengerId,
                        @Param("now") LocalDateTime now);

        @Query("SELECT r FROM RideShare r JOIN r.passengers p WHERE p.id = :passengerId AND r.departureTime > :now")
        List<RideShare> findByPassengerIdAndDepartureAfter(@Param("passengerId") Integer passengerId,
                        @Param("now") LocalDateTime now);

        void deleteById(int id);
}