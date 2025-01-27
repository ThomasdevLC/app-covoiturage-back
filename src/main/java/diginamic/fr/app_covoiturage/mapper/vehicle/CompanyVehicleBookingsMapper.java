package diginamic.fr.app_covoiturage.mapper.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.booking.VehicleBookingDTO;
import diginamic.fr.app_covoiturage.dto.vehicle.CompanyVehicleBookingsDTO;
import diginamic.fr.app_covoiturage.mapper.booking.VehicleBookingMapper;
import diginamic.fr.app_covoiturage.models.Vehicle;
import diginamic.fr.app_covoiturage.models.VehicleBooking;

import java.util.List;
import java.util.stream.Collectors;


/**
 * The CompanyVehicleBookingsMapper class is responsible for mapping between the Vehicle entity
 * and its associated data transfer object (DTO), specifically the CompanyVehicleBookingsDTO.
 * This includes converting domain-level entities representing a company's vehicle and its
 * bookings into DTOs and vice versa.
 *
 * The class utilizes VehicleBookingMapper to map the nested vehicle booking information
 * within the Vehicle entity or CompanyVehicleBookingsDTO. This ensures that the structure
 * and details of bookings associated with a specific vehicle are accurately transformed
 * during the mapping process.
 *
 * Main functionalities include:
 * - Converting a Vehicle entity to a CompanyVehicleBookingsDTO, mapping relevant properties
 *   and nested collections.
 * - Converting a CompanyVehicleBookingsDTO back to a Vehicle entity, restoring associations
 *   and dependencies.
 */
@Component
public class CompanyVehicleBookingsMapper {

    @Autowired
    private VehicleBookingMapper vehicleBookingMapper;

    public CompanyVehicleBookingsDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        List<VehicleBookingDTO> bookings = vehicle.getVehicleBookings()
                .stream()
                .map(vehicleBookingMapper::toDTO)
                .collect(Collectors.toList());

        CompanyVehicleBookingsDTO dto = new CompanyVehicleBookingsDTO();
        dto.setId(vehicle.getId());
        dto.setNumber(vehicle.getNumber());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setCategory(vehicle.getCategory());
        dto.setPicUrl(vehicle.getPicUrl());
        dto.setMotor(vehicle.getMotor());
        dto.setSeats(vehicle.getSeats());
        dto.setCo2PerKm(vehicle.getCo2PerKm());
        dto.setType(vehicle.getType());
        dto.setStatus(vehicle.getStatus());
        dto.setVehicleBookings(bookings);

        return dto;
    }

    public Vehicle toEntity(CompanyVehicleBookingsDTO dto) {
        if (dto == null) {
            return null;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setNumber(dto.getNumber());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setCategory(dto.getCategory());
        vehicle.setPicUrl(dto.getPicUrl());
        vehicle.setMotor(dto.getMotor());
        vehicle.setSeats(dto.getSeats());
        vehicle.setCo2PerKm(dto.getCo2PerKm());
        vehicle.setType(dto.getType());
        vehicle.setStatus(dto.getStatus());

        List<VehicleBooking> bookings = dto.getVehicleBookings()
                .stream()
                .map(vehicleBookingMapper::toEntity)
                .collect(Collectors.toList());
        vehicle.setVehicleBookings(bookings);

        return vehicle;
    }
}
