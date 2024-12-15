package diginamic.fr.app_covoiturage.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.RideShare;

@Repository
public interface RideShareRepository extends CrudRepository<RideShare, Integer> {

        // Recherche par ID
        @Query("SELECT r FROM RideShare r WHERE r.id = :id AND r.isDeleted = false")
        Optional<RideShare> findById(@Param("id") int id);

        // Recherche de trajets par période et organisateur
        @Query("SELECT r FROM RideShare r WHERE r.organizer.id = :organizerId AND r.isDeleted = false AND " +
                        "((r.departureTime < :newArrivalTime AND r.arrivalTime > :newDepartureTime) OR " +
                        "(r.departureTime <= :newDepartureTime AND r.arrivalTime >= :newDepartureTime) OR " +
                        "(r.departureTime <= :newArrivalTime AND r.arrivalTime >= :newArrivalTime))")
        List<RideShare> findBySimilarPeriod(
                        @Param("organizerId") Integer organizerId,
                        @Param("newDepartureTime") LocalDateTime newDepartureTime,
                        @Param("newArrivalTime") LocalDateTime newArrivalTime);

        // Recherche de trajets par ville de départ/arrivée et date
        @Query("SELECT r FROM RideShare r WHERE r.isDeleted = false AND " +
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

        // Recherche des trajets passés par organisateur
        @Query("SELECT r FROM RideShare r WHERE r.organizer.id = :organizerId AND r.arrivalTime < :now AND r.isDeleted = false")
        List<RideShare> findByOrganizerIdAndArrivalBefore(@Param("organizerId") Integer organizerId,
                        @Param("now") LocalDateTime now);

        // Recherche des trajets futurs par organisateur
        @Query("SELECT r FROM RideShare r WHERE r.organizer.id = :organizerId AND r.departureTime > :now AND r.isDeleted = false")
        List<RideShare> findByOrganizerIdAndDepartureAfter(@Param("organizerId") Integer organizerId,
                        @Param("now") LocalDateTime now);

        // Recherche des trajets passés par passager
        @Query("SELECT r FROM RideShare r JOIN r.passengers p WHERE p.id = :passengerId AND r.arrivalTime < :now AND r.isDeleted = false")
        List<RideShare> findByPassengerIdAndArrivalBefore(@Param("passengerId") Integer passengerId,
                        @Param("now") LocalDateTime now);

        // Recherche des trajets futurs par passager
        @Query("SELECT r FROM RideShare r JOIN r.passengers p WHERE p.id = :passengerId AND r.departureTime > :now AND r.isDeleted = false")
        List<RideShare> findByPassengerIdAndDepartureAfter(@Param("passengerId") Integer passengerId,
                        @Param("now") LocalDateTime now);

}
