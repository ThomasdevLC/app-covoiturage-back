package diginamic.fr.app_covoiturage.mapper.message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.message.MessageDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Message;

@Component
public class MessageMapper {

    /**
     * Convertit une entité Message en MessageDTO.
     *
     * @param message l'entité Message à convertir
     * @return le MessageDTO correspondant
     */
    public MessageDTO toDTO(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setDate(message.getDate());

        List<Integer> employeeIds = message.getEmployees()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
        dto.setEmployeeIds(employeeIds);

        return dto;
    }

    /**
     * Convertit un MessageDTO en entité Message.
     *
     * @param messageDTO le MessageDTO à convertir
     * @param employees  la liste des employés associés
     * @return l'entité Message correspondante
     */
    public Message toEntity(MessageDTO messageDTO, List<Employee> employees) {
        if (messageDTO == null) {
            return null;
        }

        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setContent(messageDTO.getContent());
        message.setDate(messageDTO.getDate());

        if (employees != null) {
            message.setEmployees(employees);
        }

        return message;
    }
}