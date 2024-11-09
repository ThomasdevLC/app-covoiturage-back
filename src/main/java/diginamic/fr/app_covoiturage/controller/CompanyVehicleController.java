package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleDTO;
import diginamic.fr.app_covoiturage.models.enums.VehicleStatus;
import diginamic.fr.app_covoiturage.services.CompanyVehicleService;
import diginamic.fr.app_covoiturage.utils.SecurityUtils;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/company-vehicles")
public class CompanyVehicleController {

    @Autowired
    private CompanyVehicleService companyVehicleService;

    @GetMapping("/admin/")
    public ResponseEntity<?> getAllVehiclesAdminOnly(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String number) {
        List<CompanyVehicleDTO> vehicles = companyVehicleService.getAllVehicles(brand, number);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/admin")
    public ResponseEntity<CompanyVehicleDTO> createVehicle(@Valid @RequestBody CompanyVehicleDTO companyVehicleDTO) {
        CompanyVehicleDTO createdVehicle = companyVehicleService.createCompanyVehicle(companyVehicleDTO);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<CompanyVehicleDTO> updateVehicle(@PathVariable int id,
            @Valid @RequestBody CompanyVehicleDTO companyVehicleDTO) {

        CompanyVehicleDTO updatedVehicle = companyVehicleService.updateCompanyVehicle(id, companyVehicleDTO);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteCompanyVehicle(@PathVariable int id) {
        companyVehicleService.deleteCompanyVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/{id}/status")
    public ResponseEntity<CompanyVehicleDTO> updateVehicleStatus(
            @PathVariable int id,
            @RequestParam VehicleStatus newStatus,
            @RequestParam int employeeId) {

        CompanyVehicleDTO updatedVehicle = companyVehicleService.updateVehicleStatus(id, newStatus, employeeId);
        return ResponseEntity.ok(updatedVehicle);
    }

    @GetMapping("/admin/{id}")
    public CompanyVehicleDTO getVehicleByIdAdminOnly(@PathVariable int id) {
        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            throw new AccessDeniedException("Vous ne disposez pas des droits nécessaires");
        }
        return companyVehicleService.getVehicleById(id);
    }

    @GetMapping("/status-and-booking-dates")
    public ResponseEntity<List<CompanyVehicleDTO>> getVehiclesByStatusAndBookingDates(
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime) {
        List<CompanyVehicleDTO> vehicles = companyVehicleService.getVehiclesByStatusAndBookingDates(startTime, endTime);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public CompanyVehicleDTO getVehicleById(@PathVariable int id) {
        return companyVehicleService.getVehicleById(id);
    }

}
