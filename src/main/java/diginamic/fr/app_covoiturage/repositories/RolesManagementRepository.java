package diginamic.fr.app_covoiturage.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import diginamic.fr.app_covoiturage.models.Employee;

/**
 * Repository interface for managing operations related to the Employee entity.
 * Provides methods for retrieving and searching Employee data.
 * Extends the CrudRepository interface for basic CRUD operations.
 * Annotated with @Repository to indicate its role in the Spring framework dependency injection mechanism.
 */
@Repository
public interface RolesManagementRepository extends CrudRepository<Employee, Integer> {
    Optional<Employee> findById(int id);

    @Query("SELECT e FROM Employee e WHERE e.firstName LIKE %:search% OR e.lastName LIKE %:search% OR e.email LIKE %:search%")
    List<Employee> searchByNameOrEmail(@Param("search") String search);

    @Override
    List<Employee> findAll();
}