package diginamic.fr.app_covoiturage.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Utility class providing security-related helper methods.
 */
public class SecurityUtils {

    public static boolean hasRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (String role : roles) {
            if (authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role))) {
                return true;
            }
        }
        return false;
    }
}