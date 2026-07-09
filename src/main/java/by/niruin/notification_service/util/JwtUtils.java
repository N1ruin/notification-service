package by.niruin.notification_service.util;

import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.exception.JwtException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;

public class JwtUtils {
    public static RecipientRole extractRole(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            throw new JwtException("realm_access claim is missing in JWT");
        }

        var rolesObj = realmAccess.get("roles");
        if (!(rolesObj instanceof List<?> roles) || roles.isEmpty()) {
            throw new JwtException("No roles found in realm_access");
        }

        for (Object r : roles) {
            String role = r.toString().toUpperCase();
            if (role.equals("ENGINEER") || role.equals("HEAD")) {
                return RecipientRole.valueOf(role);
            }
        }

        throw new JwtException("User does not have required role (ENGINEER or HEAD)");
    }

    public static String extractUsername(Jwt jwt) {
        var username = jwt.getSubject();

        if (username == null) {
            throw new JwtException("Username is missing in JWT");
        }

        return username;
    }
}
