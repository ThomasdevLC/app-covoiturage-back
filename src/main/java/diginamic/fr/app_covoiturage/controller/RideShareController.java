package diginamic.fr.app_covoiturage.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import diginamic.fr.app_covoiturage.dto.rideshare.RideShareBasicDTO;
import diginamic.fr.app_covoiturage.dto.rideshare.RideShareDTO;
import diginamic.fr.app_covoiturage.exceptions.MessageException;
import diginamic.fr.app_covoiturage.services.RideShareService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("rideshares")
public class RideShareController {

    @Autowired
    private RideShareService rideShareService;

    @PostMapping
    public ResponseEntity<?> createRideShare(@Valid @RequestBody RideShareDTO rideShareDTO) {
        try {
            RideShareDTO createdRideShare = rideShareService.createNewRideShare(rideShareDTO);
            return new ResponseEntity<>(createdRideShare, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRideShare(
            @PathVariable int id,
            @RequestParam int organizerId,
            @RequestBody RideShareDTO rideShareDTO) {
        try {
            RideShareDTO updatedRideShare = rideShareService.updateRideShare(id, organizerId, rideShareDTO);
            return ResponseEntity.ok(updatedRideShare);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete/{organizerId}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
            @PathVariable("organizerId") Integer organizerId) {
        try {
            RideShareDTO deletedRideShare = rideShareService.deleteById(id, organizerId);
            return new ResponseEntity<>(deletedRideShare, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/{rideShareId}/add-passenger/{employeeId}")
    public ResponseEntity<?> addPassenger(
            @PathVariable int rideShareId,
            @PathVariable int employeeId) {
        try {
            RideShareDTO updatedRideShare = rideShareService.addPassengerToRideShare(rideShareId, employeeId);
            return ResponseEntity.ok(updatedRideShare);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/{rideShareId}/cancel-passenger/{employeeId}")
    public ResponseEntity<?> cancelPassengerParticipation(
            @PathVariable int rideShareId,
            @PathVariable int employeeId) {
        try {
            RideShareDTO updatedRideShare = rideShareService.cancelPassengerParticipation(rideShareId, employeeId);
            return ResponseEntity.ok(updatedRideShare);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByAddresses(
            @RequestParam(required = false) String departureCity,
            @RequestParam(required = false) String arrivalCity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime) {
        try {
            List<RideShareBasicDTO> rideShares = rideShareService.findByAddresses(departureCity, arrivalCity,
                    departureDateTime);
            return ResponseEntity.ok(rideShares);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<?> getRideSharesByOrganizer(
            @PathVariable Integer organizerId,
            @RequestParam boolean past) {
        try {

            List<RideShareDTO> rideShares = rideShareService.findRideSharesByOrganizerIdAndTime(organizerId, past);
            return ResponseEntity.ok(rideShares);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<?> getRideSharesByPassenger(
            @PathVariable Integer passengerId,
            @RequestParam boolean past) {
        try {
            List<RideShareDTO> rideShares = rideShareService.findRideSharesByPassengerIdAndTime(passengerId, past);
            return ResponseEntity.ok(rideShares);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideShareDTO> getRideShareById(@PathVariable int id) throws MessageException {
        Optional<RideShareDTO> rideShareDTO = rideShareService.getRideShareById(id);

        if (rideShareDTO.isPresent()) {
            return new ResponseEntity<>(rideShareDTO.get(), HttpStatus.OK);
        } else {
            throw new MessageException("Le covoiturage avec l'id " + id + " n'existe pas.");
        }
    }

}
