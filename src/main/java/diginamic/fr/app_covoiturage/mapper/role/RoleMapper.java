package diginamic.fr.app_covoiturage.mapper.role;

import diginamic.fr.app_covoiturage.dto.role.RoleDTO;
import diginamic.fr.app_covoiturage.models.Role;
import diginamic.fr.app_covoiturage.models.enums.RoleName;

public class RoleMapper {

    /**
     * Convertit un Role en RoleDTO.
     *
     * @param role L'entité Role à convertir.
     * @return Le DTO RoleDTO correspondant.
     */
    public static RoleDTO toRoleDTO(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleDTO(role.getId(), role.getRoleName().toString());
    }

    /**
     * Convertit un RoleDTO en Role.
     *
     * @param roleDTO Le DTO RoleDTO à convertir.
     * @return L'entité Role correspondant.
     */
    public static Role toRole(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRoleName(RoleName.valueOf(roleDTO.getRoleName()));
        return role;
    }
}
