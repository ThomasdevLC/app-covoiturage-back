package diginamic.fr.app_covoiturage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.enums.UserStatus;
import diginamic.fr.app_covoiturage.repositories.RolesManagementRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RolesManagementService {
    private RolesManagementRepository rolesManagementRepository;

    public List<Employee> getAllEmployees() {
        return rolesManagementRepository.findAll();
    }

    public List<Employee> searchEmployees(String keyword) {
        return rolesManagementRepository.searchByNameOrEmail(keyword);
    }

    public Employee updateUserRole(int employeeId, UserStatus newStatus) {
        Optional<Employee> employeeOpt = rolesManagementRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setUserStatus(newStatus);
            return rolesManagementRepository.save(employee);
        } else {
            throw new EntityNotFoundException("Employé non trouvé avec l'ID: " + employeeId);
        }
    }
}