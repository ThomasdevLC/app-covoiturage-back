package diginamic.fr.app_covoiturage.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import diginamic.fr.app_covoiturage.exceptions.MessageException;
import diginamic.fr.app_covoiturage.models.Address;
import diginamic.fr.app_covoiturage.repositories.AddressRepository;

import diginamic.fr.app_covoiturage.services.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/addresses")
public class AddressController{

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;

    @PostMapping 
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address, BindingResult validation)   
        throws MessageException {
        if (validation.hasErrors()) {
            throw new MessageException(validation.getAllErrors().get(0).getDefaultMessage());
            
        }
        
        Optional<Address> existingAddress = addressRepository.findByNumberAndStreetAndCity(address.getNumber(),address.getStreet(), address.getCity());
        
        if ( existingAddress.isPresent()) {
            
            return ResponseEntity.ok(existingAddress.get());
        }
         else{
            Address savedAddress = addressService.save(address);
            return ResponseEntity.ok(savedAddress);
         }   
        }
     

}
