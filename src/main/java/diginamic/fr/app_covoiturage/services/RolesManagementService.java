package diginamic.fr.app_covoiturage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;
import diginamic.fr.app_covoiturage.repositories.RolesManagementRepository;
import diginamic.fr.app_covoiturage.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RolesManagementService {

    @Autowired
    private RolesManagementRepository rolesManagementRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Retourne tous les employés enregistrés.
     */
    public List<Employee> getAllEmployees() {
        if (!SecurityUtils.hasRole("ROLE_SUPER_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        return rolesManagementRepository.findAll();
    }

    /**
     * Recherche des employés par nom ou email.
     *
     * @param keyword Le mot-clé pour la recherche (nom ou email).
     */
    public List<Employee> searchEmployees(String keyword) {
        if (!SecurityUtils.hasRole("ROLE_SUPER_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        return rolesManagementRepository.searchByNameOrEmail(keyword);
    }

    /**
     * Met à jour les rôles d'un employé.
     *
     * @param employeeId  L'identifiant de l'employé dont on veut mettre à jour le
     *                    rôle.
     * @param newRoleName Le nom du nouveau rôle (par exemple, "ROLE_ADMIN",
     *                    "ROLE_USER").
     */
    public Employee updateUserRole(int employeeId, String newRoleName) {
        // Validation du rôle à ce niveau
        if (newRoleName == null || (!newRoleName.equals("ROLE_ADMIN") &&
                !newRoleName.equals("ROLE_SUPER_ADMIN") &&
                !newRoleName.equals("ROLE_USER"))) {
            throw new IllegalArgumentException("Le rôle spécifié est invalide.");
        }

        // Vérification des permissions
        if (!SecurityUtils.hasRole("ROLE_SUPER_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }

        // Recherche de l'employé
        Employee employee = rolesManagementRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employé non reconnu"));

        // Recherche du nouveau rôle par son nom
        Role newRole = roleRepository.findByRoleName(RoleName.valueOf(newRoleName))
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + newRoleName));

        // Ajouter le nouveau rôle à l'employé
        employee.getRoles().add(newRole);

        // Sauvegarder les modifications
        return rolesManagementRepository.save(employee);
    }

}