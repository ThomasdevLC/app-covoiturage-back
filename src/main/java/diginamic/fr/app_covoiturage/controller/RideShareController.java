package diginamic.fr.app_covoiturage.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.validation.Valid;

/**
 * RideShareController handles HTTP endpoints related to ride-sharing operations.
 * This controller processes requests for creating, updating, deleting, retrieving,
 * and searching rideshares, as well as for managing passengers in rideshares.
 */
@RestController
@RequestMapping("rideshares")
public class RideShareController {

    @Autowired
    private RideShareService rideShareService;

    @PostMapping
    public ResponseEntity<RideShareDTO> createRideShare(@Valid @RequestBody RideShareDTO rideShareDTO) {
        RideShareDTO createdRideShare = rideShareService.createNewRideShare(rideShareDTO);
        return new ResponseEntity<>(createdRideShare, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RideShareDTO> updateRideShare(
            @PathVariable int id,
            @RequestParam int organizerId,
            @Valid @RequestBody RideShareDTO rideShareDTO) {
        RideShareDTO updatedRideShare = rideShareService.updateRideShare(id, organizerId, rideShareDTO);
        return ResponseEntity.ok(updatedRideShare);
    }

    @DeleteMapping("/{id}/delete/{organizerId}")
    public ResponseEntity<RideShareDTO> delete(@PathVariable Integer id,
            @PathVariable("organizerId") Integer organizerId) {
        RideShareDTO deletedRideShare = rideShareService.deleteById(id, organizerId);
        return ResponseEntity.ok(deletedRideShare);
    }

    @PostMapping("/{rideShareId}/add-passenger/{employeeId}")
    public ResponseEntity<RideShareDTO> addPassenger(
            @PathVariable int rideShareId,
            @PathVariable int employeeId) {
        RideShareDTO updatedRideShare = rideShareService.addPassengerToRideShare(rideShareId, employeeId);
        return ResponseEntity.ok(updatedRideShare);
    }

    @DeleteMapping("/{rideShareId}/cancel-passenger/{employeeId}")
    public ResponseEntity<RideShareDTO> cancelPassengerParticipation(
            @PathVariable int rideShareId,
            @PathVariable int employeeId) {
        RideShareDTO updatedRideShare = rideShareService.cancelPassengerParticipation(rideShareId, employeeId);
        return ResponseEntity.ok(updatedRideShare);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RideShareBasicDTO>> findByAddresses(
            @RequestParam(required = false) String departureCity,
            @RequestParam(required = false) String arrivalCity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime) {
        List<RideShareBasicDTO> rideShares = rideShareService.findByAddresses(departureCity, arrivalCity,
                departureDateTime);
        return ResponseEntity.ok(rideShares);
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<RideShareDTO>> getRideSharesByOrganizer(
            @PathVariable Integer organizerId,
            @RequestParam boolean past) {
        List<RideShareDTO> rideShares = rideShareService.findRideSharesByOrganizerIdAndTime(organizerId, past);
        return ResponseEntity.ok(rideShares);
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<RideShareDTO>> getRideSharesByPassenger(
            @PathVariable Integer passengerId,
            @RequestParam boolean past) {
        List<RideShareDTO> rideShares = rideShareService.findRideSharesByPassengerIdAndTime(passengerId, past);
        return ResponseEntity.ok(rideShares);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideShareDTO> getRideShareById(@PathVariable int id) throws MessageException {
        RideShareDTO rideShareDTO = rideShareService.getRideShareById(id);
        return ResponseEntity.ok(rideShareDTO);
    }

}
