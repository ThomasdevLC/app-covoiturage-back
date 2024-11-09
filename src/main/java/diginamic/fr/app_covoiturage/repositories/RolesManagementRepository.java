package diginamic.fr.app_covoiturage.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import diginamic.fr.app_covoiturage.models.Employee;

@Repository
public interface RolesManagementRepository extends CrudRepository<Employee, Integer> {
    Optional<Employee> findById(int id);

    @Query("SELECT e FROM Employee e WHERE e.firstName LIKE %:name% OR e.lastName LIKE %:name% OR e.email LIKE %:name%")
    List<Employee> searchByNameOrEmail(@Param("name") String name);

    @Override
    List<Employee> findAll();
}