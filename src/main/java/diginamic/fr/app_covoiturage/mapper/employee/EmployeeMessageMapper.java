package diginamic.fr.app_covoiturage.mapper.employee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeMessageDTO;
import diginamic.fr.app_covoiturage.dto.message.MessageDTO;
import diginamic.fr.app_covoiturage.mapper.message.MessageMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Message;


/**
 * Mapper class to convert between Employee entities and EmployeeMessageDTO objects.
 *
 * This class provides methods to perform conversions between Employee objects
 * and their corresponding EmployeeMessageDTO representations. It also handles
 * the association with Message entities and their respective DTOs using the
 * MessageMapper component.
 *
 * Responsibilities:
 * - Convert Employee entities into EmployeeMessageDTO objects, including associated messages.
 * - Convert EmployeeMessageDTO objects into Employee entities, linking the provided message entities.
 */
@Component
public class EmployeeMessageMapper {
    @Autowired
    private MessageMapper messageMapper;

    /**
     * Convertit une entité Employee en EmployeeMessageDTO.
     *
     * @param employee l'entité Employee à convertir
     * @return le EmployeeMessageDTO correspondant
     */
    public EmployeeMessageDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeMessageDTO dto = new EmployeeMessageDTO();
        dto.setEmployeeId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());

        List<MessageDTO> messageDTOs = employee.getMessages()
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
        dto.setMessages(messageDTOs);

        return dto;
    }

    /**
     * Convertit un EmployeeMessageDTO en entité Employee avec ses messages.
     *
     * @param dto      le EmployeeMessageDTO à convertir
     * @param messages la liste des entités Message associées
     * @return l'entité Employee correspondante
     */
    public Employee toEntity(EmployeeMessageDTO dto, List<Message> messages) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getEmployeeId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());

        if (messages != null) {
            employee.setMessages(messages);
        }

        return employee;
    }
}