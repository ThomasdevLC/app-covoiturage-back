package diginamic.fr.app_covoiturage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByRoleName(RoleName.ROLE_USER)) {
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }

        if (!roleRepository.existsByRoleName(RoleName.ROLE_ADMIN)) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
        if (!roleRepository.existsByRoleName(RoleName.ROLE_SUPER_ADMIN)) {
            roleRepository.save(new Role(RoleName.ROLE_SUPER_ADMIN));
        }
    }
}
