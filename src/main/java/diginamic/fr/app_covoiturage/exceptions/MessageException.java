package diginamic.fr.app_covoiturage.exceptions;



/**
 * Represents an exception that is thrown when an error related to messages occurs.
 * This exception is a subclass of {@link Exception} and is used to encapsulate
 * error details specific to message handling.
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
