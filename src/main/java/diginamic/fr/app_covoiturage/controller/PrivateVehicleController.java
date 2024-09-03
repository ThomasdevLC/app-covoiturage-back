package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import diginamic.fr.app_covoiturage.dto.vehicle.PrivateVehicleDTO;
import diginamic.fr.app_covoiturage.exceptions.MessageException;

import diginamic.fr.app_covoiturage.services.PrivateVehicleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/private-vehicles")
public class PrivateVehicleController {

    @Autowired
    private PrivateVehicleService privateVehicleService;

    @PostMapping
    public ResponseEntity<?> createVehicle(
            @Valid @RequestBody PrivateVehicleDTO vehicleDTO,
            BindingResult validation) throws MessageException {

        if (validation.hasErrors()) {
            throw new MessageException(validation.getAllErrors().get(0).getDefaultMessage());
        }

        try {
            PrivateVehicleDTO savedVehicleDTO = privateVehicleService.createVehicle(vehicleDTO);
            return new ResponseEntity<>(savedVehicleDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable int id,
            @RequestBody PrivateVehicleDTO vehicleDTO) {
        try {
            PrivateVehicleDTO updatedVehicle = privateVehicleService.updateVehicle(id, vehicleDTO);
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
