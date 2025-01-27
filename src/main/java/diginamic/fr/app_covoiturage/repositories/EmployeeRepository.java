package diginamic.fr.app_covoiturage.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import diginamic.fr.app_covoiturage.models.Employee;


/**
 * Repository interface for managing Employee entities.
 * Provides methods for CRUD operations and custom query executions.
 * Extends the CrudRepository interface for generic CRUD functionalities.
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);

    Employee findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Employee> findById(int id);

}