package by.niruin.notification_service.util;

import by.niruin.notification_service.domain.RecipientRole;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUtils {
    public static RecipientRole extractRole(Jwt jwt) {
        var roleClaim = jwt.getClaim("role");

        if (roleClaim == null) {
            throw new SecurityException("Role claim is missing in JWT");
        }

        try {
            return RecipientRole.valueOf(roleClaim.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new SecurityException("Invalid role: " + roleClaim);
        }
    }
}
