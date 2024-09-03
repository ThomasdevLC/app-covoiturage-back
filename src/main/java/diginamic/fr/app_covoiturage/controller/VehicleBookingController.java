package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diginamic.fr.app_covoiturage.dto.booking.VehicleBookingDTO;
import diginamic.fr.app_covoiturage.services.VehicleBookingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vehicle-bookings")
public class VehicleBookingController {

    @Autowired
    private VehicleBookingService vehicleBookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody VehicleBookingDTO vehicleBookingDTO) {
        try {
            VehicleBookingDTO createdBooking = vehicleBookingService.createBooking(vehicleBookingDTO);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId, @RequestParam int employeeId) {
        try {
            vehicleBookingService.cancelBooking(bookingId, employeeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(
            @PathVariable("id") int id,
            @RequestBody VehicleBookingDTO vehicleBookingDTO) {
        try {
            VehicleBookingDTO updatedBooking = vehicleBookingService.updateBooking(id, vehicleBookingDTO);
            return ResponseEntity.ok(updatedBooking);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<VehicleBookingDTO>> getAllBookings() {
        List<VehicleBookingDTO> bookings = vehicleBookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/search/{employeeId}")
    public ResponseEntity<List<VehicleBookingDTO>> getBookingsByEmployeeAndTime(
            @PathVariable int employeeId,
            @RequestParam boolean past) {

        List<VehicleBookingDTO> bookings = vehicleBookingService.findBookingsByEmployeeAndTime(employeeId, past);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehicleBookingDTO>> getBookings(
            @RequestParam String type,
            @RequestParam(required = false) LocalDateTime now) {

        try {
            List<VehicleBookingDTO> bookings = vehicleBookingService.getAllBookingsByTime(type, now);
            return ResponseEntity.ok(bookings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
