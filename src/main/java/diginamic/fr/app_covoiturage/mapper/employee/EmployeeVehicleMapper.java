package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeVehicleDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import diginamic.fr.app_covoiturage.mapper.vehicle.CompanyVehicleMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.models.enums.RoleName;
import diginamic.fr.app_covoiturage.repositories.RoleRepository;

@Component
public class EmployeeVehicleMapper {

    @Autowired
    private CompanyVehicleMapper companyVehicleMapper;

    @Autowired
    private RoleRepository roleRepository;

    public EmployeeVehicleDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeVehicleDTO dto = new EmployeeVehicleDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setGender(employee.getGender());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());

        // Convertir Set<Role> en Set<String> pour les rôles
        Set<String> roles = employee.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet());
        dto.setRoles(roles);

        // Mapper la liste des véhicules associés à l'employé
        if (employee.getVehicle() != null) {
            List<CompanyVehicleDTO> vehicleDTOs = employee.getVehicle().stream()
                    .map(companyVehicleMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setCompanyVehicle(vehicleDTOs);
        }

        return dto;
    }

    public Employee toEntity(EmployeeVehicleDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setGender(dto.getGender());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());

        // Ajouter ROLE_USER par défaut à l'employé
        Role defaultRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Rôle 'ROLE_USER' non trouvé."));
        employee.getRoles().add(defaultRole);

        // Mapper la liste des véhicules associés depuis le DTO
        if (dto.getCompanyVehicle() != null) {
            List<Vehicle> vehicles = dto.getCompanyVehicle().stream()
                    .map(companyVehicleMapper::toEntity)
                    .collect(Collectors.toList());
            employee.setVehicle(vehicles);
        }

        return employee;
    }
}