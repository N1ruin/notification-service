package by.niruin.notification_service.util;

import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.exception.JwtException;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUtils {
    public static RecipientRole extractRole(Jwt jwt) {
        var roleClaim = jwt.getClaim("role");

        if (roleClaim == null) {
            throw new JwtException("Role claim is missing in JWT");
        }

        try {
            return RecipientRole.valueOf(roleClaim.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JwtException("Invalid role: " + roleClaim);
        }
    }

    public static String extractUsername(Jwt jwt) {
        var username = jwt.getSubject();

        if (username == null) {
            throw new JwtException("Username is missing in JWT");
        }

        return username;
    }
}
