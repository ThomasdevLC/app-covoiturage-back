package diginamic.fr.app_covoiturage.dto.address;

import java.util.List;

import diginamic.fr.app_covoiturage.dto.rideshare.RideShareBasicDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for representing an address with associated ride-sharing details.
 * This class is intended to encapsulate data related to an address and its connections
 * to ride-sharing operations such as departures and arrivals.
 *
 * The AddressRideSharesDTO includes fields for basic address details (street number, street name,
 * postal code, city) along with lists of associated ride-sharing departures and arrivals.
 *
 * This class offers getter and setter methods for accessing and modifying its properties
 * and is equipped with validation annotations to ensure data integrity.
 *
 * Fields:
 * - id: Unique identifier for the address.
 * - number: Represents the street number, must be greater than zero.
 * - street: The name of the street, must be non-empty and have a length between 2 and 100 characters.
 * - code: The postal code, must have an exact length of 5 characters.
 * - city: The name of the city, must be non-empty and have a length between 2 and 100 characters.
 * - rideShareDepartures: List containing ride share departures associated with the address.
 * - rideShareArrivals: List containing ride share arrivals associated with the address.
 *
 * Constructors:
 * - A default no-argument constructor for creating an empty AddressRideSharesDTO.
 * - A parameterized constructor for initializing an AddressRideSharesDTO with specific values.
 *
 * Validation:
 * - Field level validation ensures requirements such as non-null values and field length constraints
 *   are adhered to during data operations.
 */
public class AddressRideSharesDTO {

    private int id;

    @NotNull(message = "Le numéro de la rue est obligatoire")
    @Min(value = 1, message = "Le numéro de rue doit être supérieur à zéro")
    private int number;

    @NotEmpty(message = "Le nom de la rue est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de la rue doit comporter entre 2 et 100 caractères")
    private String street;

    @NotEmpty(message = "Le code postal est obligatoire")
    @Size(min = 5, max = 5, message = "Le code postal doit comporter 5 caractères")
    private String code;

    @NotEmpty(message = "Le nom de la ville est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de la ville doit comporter entre 2 et 100 caractères")
    private String city;

    private List<RideShareBasicDTO> rideShareDepartures;
    private List<RideShareBasicDTO> rideShareArrivals;

    public AddressRideSharesDTO(int id, int number, String street, String code, String city,
            List<RideShareBasicDTO> rideShareDepartures, List<RideShareBasicDTO> rideShareArrivals) {
        this.id = id;
        this.number = number;
        this.street = street;
        this.code = code;
        this.city = city;
        this.rideShareDepartures = rideShareDepartures;
        this.rideShareArrivals = rideShareArrivals;
    }

    public AddressRideSharesDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<RideShareBasicDTO> getRideShareDepartures() {
        return rideShareDepartures;
    }

    public void setRideShareDepartures(List<RideShareBasicDTO> rideShareDepartures) {
        this.rideShareDepartures = rideShareDepartures;
    }

    public List<RideShareBasicDTO> getRideShareArrivals() {
        return rideShareArrivals;
    }

    public void setRideShareArrivals(List<RideShareBasicDTO> rideShareArrivals) {
        this.rideShareArrivals = rideShareArrivals;
    }
}
