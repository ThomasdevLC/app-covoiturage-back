package diginamic.fr.app_covoiturage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT m FROM Message m JOIN m.recipients e WHERE e.id = :employeeId")
    List<Message> findMessagesByEmployeeId(@Param("employeeId") int employeeId);

    List<Message> findByContentContaining(String keyword);

}
