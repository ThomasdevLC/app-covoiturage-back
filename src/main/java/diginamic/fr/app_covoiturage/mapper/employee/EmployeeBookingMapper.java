package diginamic.fr.app_covoiturage.mapper.employee;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeBookingDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.VehicleBooking;
import diginamic.fr.app_covoiturage.models.Vehicle;

@Component
public class EmployeeBookingMapper {

    public EmployeeBookingDTO toDTO(VehicleBooking vehicleBooking) {
        if (vehicleBooking == null || vehicleBooking.getEmployee() == null || vehicleBooking.getCompanyVehicle() == null) {
            return null;
        }

        EmployeeBookingDTO dto = new EmployeeBookingDTO();
        dto.setId(vehicleBooking.getEmployee().getId());
        dto.setFirstName(vehicleBooking.getEmployee().getFirstName());
        dto.setLastName(vehicleBooking.getEmployee().getLastName());
        dto.setGender(vehicleBooking.getEmployee().getGender());
        dto.setPhone(vehicleBooking.getEmployee().getPhone());
        dto.setEmail(vehicleBooking.getEmployee().getEmail());
        dto.setSeats(vehicleBooking.getCompanyVehicle().getSeats()); 

        return dto;
    }

    public VehicleBooking toEntity(EmployeeBookingDTO employeeBookingDTO) {
        if (employeeBookingDTO == null) {
            return null;
        }

        VehicleBooking vehicleBooking = new VehicleBooking();
        
        Employee employee = new Employee();
        employee.setId(employeeBookingDTO.getId());
        employee.setFirstName(employeeBookingDTO.getFirstName());
        employee.setLastName(employeeBookingDTO.getLastName());
        employee.setGender(employeeBookingDTO.getGender());
        employee.setPhone(employeeBookingDTO.getPhone());
        employee.setEmail(employeeBookingDTO.getEmail());

        Vehicle vehicle = new Vehicle();
        vehicle.setSeats(employeeBookingDTO.getSeats());

        vehicleBooking.setEmployee(employee);
        vehicleBooking.setCompanyVehicle(vehicle);

        return vehicleBooking;
    }
}
