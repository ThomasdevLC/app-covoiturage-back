package diginamic.fr.app_covoiturage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    /**
     * Récupère tous les messages associés à un employé spécifique.
     *
     * @param employeeId l'ID de l'employé
     * @return une liste de Message
     */
    @Query("SELECT m FROM Message m JOIN m.employees e WHERE e.id = :employeeId")
    List<Message> findByEmployeeId(int employeeId);
}