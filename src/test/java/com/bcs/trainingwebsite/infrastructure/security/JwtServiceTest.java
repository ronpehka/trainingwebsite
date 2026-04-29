package com.bcs.trainingwebsite.infrastructure.security;

import com.bcs.trainingwebsite.persistence.role.Role;
import com.bcs.trainingwebsite.persistence.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final String SECRET = "this-is-a-32-byte-or-longer-test-secret-key!";

    private static User testUser() {
        Role role = new Role();
        role.setId(2);
        role.setName("COACH");

        User user = new User();
        user.setId(42);
        user.setEmail("coach@test.ee");
        user.setRole(role);
        return user;
    }

    @Test
    void issuedToken_canBeParsedAndCarriesExpectedClaims() {
        JwtService service = new JwtService(SECRET, 3600);

        String token = service.issueToken(testUser());
        Jws<Claims> parsed = service.parse(token);
        Claims claims = parsed.getPayload();

        assertThat(claims.getSubject()).isEqualTo("42");
        assertThat(claims.get("email", String.class)).isEqualTo("coach@test.ee");
        assertThat(claims.get("role", String.class)).isEqualTo("COACH");
        assertThat(claims.getIssuedAt()).isNotNull();
        assertThat(claims.getExpiration()).isAfter(claims.getIssuedAt());
    }

    @Test
    void parse_rejectsTokenSignedWithDifferentSecret() {
        JwtService issuer = new JwtService(SECRET, 3600);
        JwtService verifier = new JwtService("a-completely-different-32-byte-secret-key!!", 3600);

        String token = issuer.issueToken(testUser());

        assertThatThrownBy(() -> verifier.parse(token))
                .isInstanceOf(SignatureException.class);
    }

    @Test
    void parse_rejectsExpiredToken() throws InterruptedException {
        JwtService service = new JwtService(SECRET, 1);
        String token = service.issueToken(testUser());

        // jjwt accepts a small clock-skew window; sleep long enough to be safely past expiration.
        Thread.sleep(1200);

        assertThatThrownBy(() -> service.parse(token))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void constructor_rejectsTooShortSecret() {
        assertThatThrownBy(() -> new JwtService("too-short", 3600))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("32 bytes");
    }
}
