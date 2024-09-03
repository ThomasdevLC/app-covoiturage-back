package diginamic.fr.app_covoiturage.mapper.employee;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeLoginDTO;
import diginamic.fr.app_covoiturage.models.Employee;

public class EmployeeLoginMapper {

    /**
     * Mappe un EmployeeLoginDTO vers une entité Employee.
     *
     * @param dto Le DTO de connexion
     * @return Une entité Employee avec les données du DTO
     */
    public static Employee toEntity(EmployeeLoginDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());

        return employee;
    }

    /**
     * Mappe une entité Employee vers un EmployeeLoginDTO.
     *
     * @param employee L'entité Employee à mapper
     * @return Un DTO EmployeeLoginDTO avec les données de l'entité
     */
    public static EmployeeLoginDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeLoginDTO dto = new EmployeeLoginDTO();
        dto.setEmail(employee.getEmail());
        dto.setPassword(employee.getPassword());

        return dto;
    }
}
