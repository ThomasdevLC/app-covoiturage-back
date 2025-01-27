package diginamic.fr.app_covoiturage.repositories;

import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoleRepository interface for performing CRUD operations on Role entities.
 *
 * This repository extends the CrudRepository interface provided by Spring Data,
 * enabling basic create, read, update, and delete operations on the Role entity.
 * Additionally, custom query methods are defined to retrieve or check the existence
 * of Role entities based on their attributes.
 *
 * Methods:
 * - findByRoleName(RoleName roleName): Retrieves a Role entity based on its role name.
 * - existsByRoleName(RoleName roleName): Checks if a Role entity exists with the specified role name.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);

    boolean existsByRoleName(RoleName roleName);

}