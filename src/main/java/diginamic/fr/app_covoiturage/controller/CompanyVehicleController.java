package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import diginamic.fr.app_covoiturage.models.enums.VehicleStatus;
import diginamic.fr.app_covoiturage.services.CompanyVehicleService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/company-vehicles")
public class CompanyVehicleController {

    @Autowired
    private CompanyVehicleService companyVehicleService;

    @PostMapping
    public ResponseEntity<?> createVehicle(
            @RequestBody CompanyVehicleDTO companyVehicleDTO, BindingResult validation) {

        if (validation.hasErrors()) {
            String errorMessage = validation.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        try {
            CompanyVehicleDTO createdVehicle = companyVehicleService.createCompanyVehicle(companyVehicleDTO);
            return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(
            @PathVariable int id,
            @RequestBody CompanyVehicleDTO companyVehicleDTO,
            BindingResult validation) {

        if (validation.hasErrors()) {
            String errorMessage = validation.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        try {
            CompanyVehicleDTO updatedVehicle = companyVehicleService.updateCompanyVehicle(id, companyVehicleDTO);
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyVehicle(@PathVariable int id) {
        try {
            companyVehicleService.deleteCompanyVehicle(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllVehiclesAdminOnly(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String number) {
        List<CompanyVehicleDTO> vehicles = companyVehicleService.getAllVehicles(brand, number);
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateVehicleStatus(
            @PathVariable int id,
            @RequestParam VehicleStatus newStatus,
            @RequestParam int employeeId) {

        try {
            CompanyVehicleDTO updatedVehicle = companyVehicleService.updateVehicleStatus(id, newStatus, employeeId);
            return ResponseEntity.ok(updatedVehicle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/status-and-booking-dates")
    public ResponseEntity<?> getVehiclesByStatusAndBookingDates(
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime) {
        List<CompanyVehicleDTO> vehicles = companyVehicleService.getVehiclesByStatusAndBookingDates(startTime, endTime);
        return ResponseEntity.ok(vehicles);
    }

}
