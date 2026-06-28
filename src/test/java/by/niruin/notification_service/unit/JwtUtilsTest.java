package by.niruin.notification_service.unit;

import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.exception.JwtException;
import by.niruin.notification_service.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JwtUtilsTest {
    @Test
    void extractRole_shouldReturnRole() {
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .claim("role", "HEAD")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        var role = JwtUtils.extractRole(jwt);

        assertThat(role).isEqualTo(RecipientRole.HEAD);
    }

    @Test
    void extractRole_shouldThrowJwtException_whenRoleClaimMissing() {
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        assertThatThrownBy(() -> JwtUtils.extractRole(jwt))
                .isInstanceOf(JwtException.class)
                .hasMessage("Role claim is missing in JWT");
    }

    @Test
    void extractRole_shouldThrowJwtException_whenRoleInvalid() {
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .claim("role", "INVALID_ROLE")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        assertThatThrownBy(() -> JwtUtils.extractRole(jwt))
                .isInstanceOf(JwtException.class)
                .hasMessage("Invalid role: INVALID_ROLE");
    }

    @Test
    void extractRole_shouldHandleCaseInsensitive() {
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .claim("role", "head")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        var role = JwtUtils.extractRole(jwt);

        assertThat(role).isEqualTo(RecipientRole.HEAD);
    }

    @Test
    void extractRole_shouldReturnEngineer() {
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .claim("role", "ENGINEER")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        var role = JwtUtils.extractRole(jwt);

        assertThat(role).isEqualTo(RecipientRole.ENGINEER);
    }
}
