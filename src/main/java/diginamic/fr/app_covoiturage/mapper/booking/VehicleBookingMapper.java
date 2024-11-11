package diginamic.fr.app_covoiturage.mapper.booking;

import diginamic.fr.app_covoiturage.dto.booking.VehicleBookingDTO;
import diginamic.fr.app_covoiturage.mapper.employee.EmployeeMapper;
import diginamic.fr.app_covoiturage.mapper.vehicle.CompanyVehicleMapper;
import diginamic.fr.app_covoiturage.models.VehicleBooking;
import diginamic.fr.app_covoiturage.models.enums.BookingStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleBookingMapper {

    @Autowired
    private CompanyVehicleMapper companyVehicleMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    public VehicleBookingDTO toDTO(VehicleBooking vehicleBooking) {
        if (vehicleBooking == null) {
            return null;
        }

        VehicleBookingDTO dto = new VehicleBookingDTO();
        dto.setId(vehicleBooking.getId());
        dto.setStartTime(vehicleBooking.getStartTime());
        dto.setEndTime(vehicleBooking.getEndTime());

        if (vehicleBooking.getCompanyVehicle() != null) {
            dto.setVehicle(companyVehicleMapper.toDTO(vehicleBooking.getCompanyVehicle()));
        }

        if (vehicleBooking.getEmployee() != null) {
            dto.setEmployee(employeeMapper.toDTO(vehicleBooking.getEmployee()));
        }

        dto.setStatus(vehicleBooking.getStatus());

        return dto;
    }

    public VehicleBooking toEntity(VehicleBookingDTO dto) {
        if (dto == null) {
            return null;
        }

        VehicleBooking vehicleBooking = new VehicleBooking();
        vehicleBooking.setId(dto.getId());
        vehicleBooking.setStartTime(dto.getStartTime());
        vehicleBooking.setEndTime(dto.getEndTime());

        if (dto.getVehicle() != null) {
            vehicleBooking.setCompanyVehicle(companyVehicleMapper.toEntity(dto.getVehicle()));
        }

        if (dto.getEmployee() != null) {
            vehicleBooking.setEmployee(employeeMapper.toEntity(dto.getEmployee()));
        }

        if (dto.getStatus() == null) {
            vehicleBooking.setStatus(BookingStatus.ACTIVE);
        } else {
            vehicleBooking.setStatus(dto.getStatus());
        }

        return vehicleBooking;
    }
}
