package diginamic.fr.app_covoiturage.controlleradvice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import diginamic.fr.app_covoiturage.exceptions.MessageException;
import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de gestion globale des exceptions qui gère diverses exceptions
 * et renvoie une réponse d'erreur.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

     // Gère les exceptions EntityNotFoundException et renvoie une réponse 404
     @ExceptionHandler(EntityNotFoundException.class)
     public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
     }

     // Gère les exceptions IllegalArgumentException et renvoie une réponse 400 (au
     // lieu de 403 qui était incorrect)
     @ExceptionHandler(IllegalArgumentException.class)
     public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
     }

     // Gère les exceptions personnalisées MessageException et renvoie une réponse
     // 400
     @ExceptionHandler(MessageException.class)
     public ResponseEntity<String> handleCustomMessageException(MessageException e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
     }

     // Gère les exceptions RuntimeException pour capturer les erreurs inattendues
     @ExceptionHandler(RuntimeException.class)
     public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
          return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
     }

     // Gère les exceptions AccessDeniedException pour les erreurs d'accès interdit
     @ExceptionHandler(AccessDeniedException.class)
     public ResponseEntity<String> handleAccessDenied(AccessDeniedException e) {
          return new ResponseEntity<>("Vous n'avez pas les droits nécessaires pour accéder à cette ressource.",
                    HttpStatus.FORBIDDEN);
     }

     // Gère les exceptions MethodArgumentNotValidException pour la validation des
     // arguments
     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
          Map<String, String> errors = new HashMap<>();

          // Parcourt chaque erreur et les ajoute dans le map
          ex.getBindingResult().getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
     }
}
