package diginamic.fr.app_covoiturage.mapper.employee;

import org.springframework.stereotype.Component;

import diginamic.fr.app_covoiturage.dto.employee.EmployeeBookingDTO;
import diginamic.fr.app_covoiturage.models.Employee;
import diginamic.fr.app_covoiturage.models.VehicleBooking;
import diginamic.fr.app_covoiturage.models.Vehicle;

/**
 * The EmployeeBookingMapper class is responsible for mapping between the
 * EmployeeBookingDTO and VehicleBooking entities. It provides methods to
 * convert entity objects to DTO objects and vice versa. This is useful
 * for transferring data between the database layer and the application layer.
 *
 * This class ensures that the data transformation between domain entities
 * (VehicleBooking, Employee, Vehicle) and the Data Transfer Object
 * (EmployeeBookingDTO) is managed effectively while maintaining data
 * consistency and correctness.
 *
 * Methods:
 * - toDTO: Converts a VehicleBooking entity to an EmployeeBookingDTO object.
 * - toEntity: Converts an EmployeeBookingDTO object back to a VehicleBooking entity.
 *
 * The methods handle null checks to avoid NullPointerExceptions during the
 * mapping process. Both methods ensure that only valid and existing data
 * elements are processed for conversion.
 *
 * This class is marked as a Spring component to allow dependency injection
 * and easy integration within the service layer or wherever data transformation
 * is required.
 */
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
