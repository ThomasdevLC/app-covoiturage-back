package diginamic.fr.app_covoiturage.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rideShare")
public class RideShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "departure_time")
    @NotNull(message = "La date de départ est obligatoire")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    @NotNull(message = "La date d'arrivée est obligatoire")
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "departure_address_id")
    @NotNull(message = "L'adresse de départ est obligatoire")
    private Address departureAddress;

    @ManyToOne
    @JoinColumn(name = "arrival_address_id")
    @NotNull(message = "L'adresse d'arrivée est obligatoire")
    private Address arrivalAddress;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Employee organizer;

    @Column(name = "available_seats")
    @NotNull(message = "Le nombre de sièges disponible est obligatoire")
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToMany
    @JoinTable(name = "employee_ride_share", joinColumns = @JoinColumn(name = "id_ride_share", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_employee", referencedColumnName = "id"))
    private List<Employee> passengers;

    public RideShare(LocalDateTime departureTime, LocalDateTime arrivalTime, Address departureAddress,
            Address arrivalAddress, int availableSeats, Employee organizer, List<Employee> passengers) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.organizer = organizer;
        this.availableSeats = availableSeats;
    }

    public RideShare() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Address getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
    }

    public Address getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(Address arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public Employee getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Employee organizer) {
        this.organizer = organizer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Employee> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Employee> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "RideShare [id=" + id + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime
                + ", departureAddress=" + departureAddress + ", arrivalAddress=" + arrivalAddress + "]";
    }

}
