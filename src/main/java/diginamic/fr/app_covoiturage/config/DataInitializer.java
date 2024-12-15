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
        if (!roleRepository.existsByRoleName(RoleName.USER)) {
            roleRepository.save(new Role(RoleName.USER));
        }

        if (!roleRepository.existsByRoleName(RoleName.ADMIN)) {
            roleRepository.save(new Role(RoleName.ADMIN));
        }
        if (!roleRepository.existsByRoleName(RoleName.SUPER_ADMIN)) {
            roleRepository.save(new Role(RoleName.SUPER_ADMIN));
        }
    }
}
