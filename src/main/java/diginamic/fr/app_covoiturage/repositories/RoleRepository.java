package diginamic.fr.app_covoiturage.repositories;

import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);

    boolean existsByRoleName(RoleName roleName);

}