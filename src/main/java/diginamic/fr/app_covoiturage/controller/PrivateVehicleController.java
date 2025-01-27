package diginamic.fr.app_covoiturage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.exceptions.MessageException;
import diginamic.fr.app_covoiturage.services.PrivateVehicleService;
import jakarta.validation.Valid;

/**
 * Controller for managing private vehicles.
 * Provides endpoints for creating, updating, retrieving, and deleting private vehicles,
 * as well as retrieving vehicles associated with a specific employee.
 */
@RestController
@RequestMapping("/private-vehicles")
public class PrivateVehicleController {

    @Autowired
    private PrivateVehicleService privateVehicleService;

    @PostMapping
    public ResponseEntity<PrivateVehicleDTO> createVehicle(@Valid @RequestBody PrivateVehicleDTO vehicleDTO)
            throws MessageException {
        PrivateVehicleDTO savedVehicleDTO = privateVehicleService.createVehicle(vehicleDTO);
        return new ResponseEntity<>(savedVehicleDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrivateVehicleDTO> updateVehicle(@PathVariable int id,
            @Valid @RequestBody PrivateVehicleDTO vehicleDTO) {

        PrivateVehicleDTO updatedVehicle = privateVehicleService.updateVehicle(id, vehicleDTO);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}")
    public List<PrivateVehicleDTO> getVehiclesByEmployee(@PathVariable int employeeId) {
        return privateVehicleService.getVehiclesByEmployeeId(employeeId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int id,
            @RequestParam("employeeId") int employeeId) {

        privateVehicleService.deleteVehicle(id, employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivateVehicleDTO> getVehicleById(@PathVariable int id) {
        PrivateVehicleDTO vehicle = privateVehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

}
