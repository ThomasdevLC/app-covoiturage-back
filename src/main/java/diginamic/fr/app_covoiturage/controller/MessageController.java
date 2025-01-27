package diginamic.fr.app_covoiturage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import diginamic.fr.app_covoiturage.dto.message.MessageDTO;
import diginamic.fr.app_covoiturage.services.MessageService;

/**
 * Contrôleur des messages liés aux employés, permettant de gérer les
 * opérations sur les messages comme récupération, lecture, et suppression.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Endpoint pour récupérer les messages d'un employé.
     *
     * @param employeeId l'ID de l'employé
     * @return une liste de MessageDTO
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<List<MessageDTO>> getMessagesForEmployee(@PathVariable("id") int employeeId) {
        List<MessageDTO> messages = messageService.getMessagesForEmployee(employeeId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Endpoint pour marquer un message comme lu.
     *
     * @param messageId l'ID du message à marquer comme lu
     * @return un code 200 (OK) si l'opération réussit
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markMessageAsRead(@PathVariable("id") int messageId) {
        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour supprimer un message.
     *
     * @param messageId l'ID du message à supprimer
     * @return un code 200 (OK) si l'opération réussit
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") int messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour récupérer un message spécifique non supprimé par son ID.
     *
     * @param messageId l'ID du message
     * @return le MessageDTO correspondant
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable("id") int messageId) {
        MessageDTO message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }
}