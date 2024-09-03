package diginamic.fr.app_covoiturage.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employee")

public class Employee implements UserDetails {

    /**
     * Attributs de la classe employés(-> Employee)
     * Identifiant unique auto-incrementé: int id
     * attribut FirstName de type string
     * attribut Lastname de type String
     * attribut type de type String
     * attribut admin de type boolean
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Veuillez renseigner votre prénom.")
    @Size(min = 1, max = 100, message = "Longueur maximale de 255 caracteres et minimale de 1.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Veuillez renseigner votre nom.")
    @Size(min = 1, max = 100, message = "Longueur maximale de 255 caracteres et minimale de 1.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Veuillez renseigner ce champ.")
    @Size(min = 1, max = 30, message = "Longueur maximale de 30 caracteres et minimale de 1.")
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotBlank(message = "Veuillez renseigner votre numéro de téléphone.")
    @Size(min = 10, message = "Le numéro de téléphone doit contenir au moins 10 chiffres")
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "admin", nullable = false)
    private boolean admin;

    @NotBlank(message = "Veuillez renseigner votre email.")
    @Email(message = "Le champ doit être un email valide")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Veuillez renseigner unmot de passe.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Column(name = "password")
    private String password;

    @NotNull
    @JsonProperty("isActive")
    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "organizer")
    private List<RideShare> organizedRides;

    @OneToMany(mappedBy = "employee")
    private List<VehicleBooking> vehicleBooking;

    @OneToMany(mappedBy = "employee")
    private List<Vehicle> vehicle;

    @ManyToMany
    @JoinTable(name = "employee_ride_share", joinColumns = @JoinColumn(name = "id_employee", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_ride_share", referencedColumnName = "id"))
    private List<RideShare> rideShares;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

    /**
     * Constructeur avec arguments pour les modifications
     * 
     * @param firstName
     * @param lastName
     * @param gender
     * @param phone
     * @param admin
     * @param email
     * @param password
     */

    public Employee(String firstName, String lastName, String gender, String phone, boolean admin, String email,
            String password, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.admin = admin;
        this.email = email;
        this.password = password;
        this.isActive = isActive;

    }

    public Employee() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RideShare> getOrganizedRides() {
        return organizedRides;
    }

    public void setOrganizedRides(List<RideShare> organizedRides) {
        this.organizedRides = organizedRides;
    }

    public List<VehicleBooking> getVehicleBooking() {
        return vehicleBooking;
    }

    public void setVehicleBooking(List<VehicleBooking> vehicleBooking) {
        this.vehicleBooking = vehicleBooking;
    }

    public List<RideShare> getRideShares() {
        return rideShares;
    }

    public void setRideShares(List<RideShare> rideShares) {
        this.rideShares = rideShares;
    }

    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
                + ", admin=" + admin + "]";
    }

}
