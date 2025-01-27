package diginamic.fr.app_covoiturage.dto.role;

/**
 * Data Transfer Object (DTO) for representing role information.
 *
 * This class is used to encapsulate data related to roles in a format suitable
 * for transfer between client and server or between different layers of the application.
 * It is designed to represent a simplified view of the Role entity and excludes
 * any relationships or behavior specific to the entity.
 *
 * Fields:
 * - id: The unique identifier of the role.
 * - roleName: The name of the role as a string.
 *
 * The class provides a zero-argument constructor for frameworks or libraries that
 * require it and a parameterized constructor for manual instantiation and initialization.
 * It also includes getter and setter methods to access and modify the attributes.
 */
public class RoleDTO {
    private int id;
    private String roleName;

    public RoleDTO(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public RoleDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
