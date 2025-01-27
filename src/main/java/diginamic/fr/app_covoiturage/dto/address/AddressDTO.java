package diginamic.fr.app_covoiturage.dto.address;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * Data Transfer Object (DTO) representing an address.
 * This class is designed to encapsulate details regarding
 * an address including its ID, street number, street name, postal code, and city.
 * It is primarily used to transfer address-related data between application layers.
 *
 * Each field is annotated with appropriate validation constraints to ensure
 * data integrity during input or output operations.
 *
 * Fields:
 * - id: Unique identifier for the address.
 * - number: Represents the street number, must be greater than zero.
 * - street: The name of the street, must be non-empty and have a length between 2 and 100 characters.
 * - code: The postal code, must have an exact length of 5 characters.
 * - city: The name of the city, must be non-empty and have a length between 2 and 100 characters.
 *
 * This class includes getter and setter methods for all fields,
 * as well as parameterized and default constructors.
 */
public class AddressDTO {

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

    public AddressDTO(int id, int number, String street, String code, String city) {
        this.id = id;
        this.number = number;
        this.street = street;
        this.code = code;
        this.city = city;
    }

    public AddressDTO() {
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
}
