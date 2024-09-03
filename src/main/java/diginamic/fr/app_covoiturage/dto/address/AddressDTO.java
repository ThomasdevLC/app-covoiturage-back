package diginamic.fr.app_covoiturage.dto.address;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
