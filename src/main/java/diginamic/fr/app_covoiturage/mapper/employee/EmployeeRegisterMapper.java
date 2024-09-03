package diginamic.fr.app_covoiturage.mapper.employee;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeRegisterDTO;
import diginamic.fr.app_covoiturage.models.Employee;

public class EmployeeRegisterMapper {

    /**
     * Mappe un EmployeeRegisterDTO vers une entité Employee.
     *
     * @param dto Le DTO d'inscription
     * @return Une entité Employee avec les données du DTO
     */
    public static Employee toEntity(EmployeeRegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setGender(dto.getGender());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
        employee.setActive(true);
        employee.setAdmin(dto.isAdmin());

        return employee;
    }

    /**
     * Mappe une entité Employee vers un EmployeeRegisterDTO.
     *
     * @param employee L'entité Employee à mapper
     * @return Un DTO EmployeeRegisterDTO avec les données de l'entité
     */
    public static EmployeeRegisterDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeRegisterDTO dto = new EmployeeRegisterDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setGender(employee.getGender());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());
        dto.setPassword(employee.getPassword());
        dto.setAdmin(employee.isAdmin());

        return dto;
    }
}
