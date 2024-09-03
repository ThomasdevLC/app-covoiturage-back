package diginamic.fr.app_covoiturage.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diginamic.fr.app_covoiturage.repositories.AddressRepository;
import diginamic.fr.app_covoiturage.models.Address;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address save(Address address) {
        
        return addressRepository.save(address);
    }

    public Optional<Address> findById(int id) {
        return addressRepository.findById(id);
    }
    
   
}
