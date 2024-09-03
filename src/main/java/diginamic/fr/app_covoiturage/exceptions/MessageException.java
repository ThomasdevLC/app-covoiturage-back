package diginamic.fr.app_covoiturage.exceptions;

/**
 * Exception personnalisée utilisée pour signaler des erreurs liées aux messages
 * dans l'application.
 * 
 * Cette exception qui étend la classe {@code java.lang.Exception}
 * est utilisée lorsque des erreurs de validation ou d'autres problèmes liés aux
 * messages sont détectés
 * 
 * Lors de la création d'une instance de {@code MessageException}, un message
 * détaillé peut être spécifié,
 * qui sera accessible via la méthode {@code getMessage()}.
 */
public class MessageException extends Exception {

    /**
     * Construit une instance de MessageException .
     *
     * @param message le message décrivant l'erreur.
     * 
     */
    public MessageException(String message) {
        super(message);
    }
}
