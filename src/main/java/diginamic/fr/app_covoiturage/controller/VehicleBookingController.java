package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diginamic.fr.app_covoiturage.dto.booking.VehicleBookingDTO;
import diginamic.fr.app_covoiturage.services.VehicleBookingService;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vehicle-bookings")
public class VehicleBookingController {

    @Autowired
    private VehicleBookingService vehicleBookingService;

    @PostMapping
    public ResponseEntity<VehicleBookingDTO> createBooking(@Valid @RequestBody VehicleBookingDTO vehicleBookingDTO) {
        VehicleBookingDTO createdBooking = vehicleBookingService.createBooking(vehicleBookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleBookingDTO> updateBooking(
            @PathVariable("id") int id,
            @Valid @RequestBody VehicleBookingDTO vehicleBookingDTO) {
        VehicleBookingDTO updatedBooking = vehicleBookingService.updateBooking(id, vehicleBookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable int bookingId, @RequestParam int employeeId) {
        vehicleBookingService.cancelBooking(bookingId, employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VehicleBookingDTO>> getAllBookings() {
        List<VehicleBookingDTO> bookings = vehicleBookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<VehicleBookingDTO> getBookingById(@PathVariable int bookingId) {
        VehicleBookingDTO booking = vehicleBookingService.findById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/search/{employeeId}")
    public ResponseEntity<List<VehicleBookingDTO>> getBookingsByEmployeeAndTime(
            @PathVariable int employeeId,
            @RequestParam boolean past) {

        List<VehicleBookingDTO> bookings = vehicleBookingService.findBookingsByEmployeeAndTime(employeeId, past);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/admin/search")
    public ResponseEntity<List<VehicleBookingDTO>> getBookings(
            @RequestParam String type,
            @RequestParam(required = false) LocalDateTime now,
            @RequestParam int employeeId) {

        List<VehicleBookingDTO> bookings = vehicleBookingService.getAllBookingsByTime(type, now, employeeId);
        return ResponseEntity.ok(bookings);
    }
}
