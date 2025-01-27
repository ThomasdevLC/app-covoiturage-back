package diginamic.fr.app_covoiturage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Message;

/**
 * MessageRepository is a data access interface that manages Message entities.
 * It extends the CrudRepository interface provided by Spring Data, offering
 * basic CRUD operations and custom query methods for Message entities.
 *
 * It provides methods to:
 * - Retrieve a list of non-deleted messages associated with a given employee.
 * - Retrieve a non-deleted message by its unique identifier.
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    /**
     * Récupère la liste de messages non supprimés associés à un employé.
     */
    @Query("SELECT m FROM Message m JOIN m.employees e WHERE e.id = :employeeId AND m.isDeleted = false ORDER BY m.date DESC")
    List<Message> findByEmployeeId(@Param("employeeId") int employeeId);

    /**
     * Récupère un message non supprimé par son ID.
     */
    @Query("SELECT m FROM Message m WHERE m.id = :messageId AND m.isDeleted = false")
    Optional<Message> findByIdAndIsDeletedFalse(@Param("messageId") int messageId);
}