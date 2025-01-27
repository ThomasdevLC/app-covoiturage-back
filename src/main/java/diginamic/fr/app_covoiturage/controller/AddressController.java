package diginamic.fr.app_covoiturage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import diginamic.fr.app_covoiturage.exceptions.MessageException;
import diginamic.fr.app_covoiturage.models.Address;

import diginamic.fr.app_covoiturage.services.AddressService;

import jakarta.validation.Valid;

/**
 * REST Controller for managing address-related operations.
 *
 * This controller provides an endpoint for creating or retrieving an address.
 * It is mapped to the "/addresses" URL and interacts with the AddressService
 * to handle business logic related to the Address entity.
 */
@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) throws MessageException {
        Address savedAddress = addressService.createOrGetAddress(address);
        return ResponseEntity.ok(savedAddress);
    }

}
