package diginamic.fr.app_covoiturage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import diginamic.fr.app_covoiturage.dto.message.MessageDTO;
import diginamic.fr.app_covoiturage.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Endpoint pour récupérer les messages d'un employé .
     *
     * @param employeeId l'ID de l'employé
     * @return une liste de MessageDTO
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<List<MessageDTO>> getMessagesForEmployee(@PathVariable("id") int employeeId) {
        List<MessageDTO> messages = messageService.getMessagesForEmployee(employeeId);
        return ResponseEntity.ok(messages);
    }
}