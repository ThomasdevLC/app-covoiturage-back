package diginamic.fr.app_covoiturage.services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.dto.message.MessageDTO;
import diginamic.fr.app_covoiturage.mapper.message.MessageMapper;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.Message;
import diginamic.fr.app_covoiturage.models.RideShare;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.models.VehicleBooking;
import diginamic.fr.app_covoiturage.repositories.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    /**
     * Crée et sauvegarde un message pour un employé.
     *
     * @param employee       l'employé à notifier
     * @param vehicle        le véhicule concerné
     * @param vehicleBooking la réservation affectée
     */

    public void notifyEmployeeForBookingCancelation(Employee employee, Vehicle vehicle, VehicleBooking vehicleBooking) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String content = "Bonjour " + employee.getFirstName() + ",\n\n" +
                "Nous vous informons que votre réservation pour le véhicule " + vehicle.getNumber() +
                " (" + vehicle.getBrand() + "), prévue du " + vehicleBooking.getStartTime().format(formatter) +
                " jusqu'au " + vehicleBooking.getEndTime().format(formatter) +
                ", a été annulée en raison d'un problème technique.\n\n" +
                "Nous nous excusons pour la gêne occasionnée.\n\n" +
                "Cordialement,\nL'équipe administrative.";

        Message message = new Message(content);
        message.getEmployees().add(employee);
        messageRepository.save(message);
    }

    public void notifyEmployeeForRideshareCancellation(Employee employee, Vehicle vehicle, RideShare rideShare) {

        String content = "Bonjour " + employee.getFirstName() + ",\n\n" +
                "Nous vous informons que le covoiturage organisé par " + rideShare.getOrganizer().getFirstName() + " " +
                rideShare.getOrganizer().getLastName() + " depuis " + rideShare.getDepartureAddress().getCity() +
                " à destination de " + rideShare.getArrivalAddress().getCity() + " avec le véhicule "
                + vehicle.getNumber() +
                " (" + vehicle.getBrand() + "), prévu le "
                + rideShare.getDepartureTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                ", a été annulé.\n\n" +
                "Nous nous excusons pour la gêne occasionnée.\n\n" +
                "Cordialement,\nL'équipe administrative.";

        Message message = new Message(content);
        message.getEmployees().add(employee);
        messageRepository.save(message);
    }

    /**
     * Récupère tous les messages non supprimés pour un employé spécifique.
     */
    public List<MessageDTO> getMessagesForEmployee(int employeeId) {
        List<Message> messages = messageRepository.findByEmployeeId(employeeId);
        return messages.stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Marque un message comme lu.
     */
    public boolean markMessageAsRead(int messageId) {
        Message message = messageRepository.findByIdAndIsDeletedFalse(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Ce message n'existe pas"));
        message.setRead(true);
        messageRepository.save(message);
        return true;
    }

    /**
     * Marque un message comme supprimé.
     */
    public boolean deleteMessage(int messageId) {
        Message message = messageRepository.findByIdAndIsDeletedFalse(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Ce message n'existe pas"));

        message.setDeleted(true);
        messageRepository.save(message);
        return true;
    }

    /**
     * Récupère un message non supprimé par son ID.
     */
    public MessageDTO getMessageById(int messageId) {
        Message message = messageRepository.findByIdAndIsDeletedFalse(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Ce message n'existe pas"));
        return messageMapper.toDTO(message);
    }
}