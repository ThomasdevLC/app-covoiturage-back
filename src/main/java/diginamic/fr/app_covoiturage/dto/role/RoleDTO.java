package diginamic.fr.app_covoiturage.dto.role;

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
