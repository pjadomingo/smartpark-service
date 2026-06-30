package com.hitachi.smartpark_service.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Inject test values
        jwtService.setSecretKey("YXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYXNkZmFzZGZhc2Rm"); // base64 string
        jwtService.setJwtExpiration(1000 * 60); // 1 minute
    }

    @Test
    void testGenerateAndExtractUsername() {
        UserDetails userDetails = new User("test@example.com", "password", Collections.emptyList());

        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertThat(username).isEqualTo("test@example.com");
    }

    @Test
    void testTokenIsValid() {
        UserDetails userDetails = new User("valid@example.com", "password", Collections.emptyList());

        String token = jwtService.generateToken(userDetails);

        boolean valid = jwtService.isTokenValid(token, userDetails);

        assertThat(valid).isTrue();
    }

    @Test
    void testTokenIsInvalidForWrongUser() {
        UserDetails userDetails = new User("user@example.com", "password", Collections.emptyList());
        UserDetails otherUser = new User("other@example.com", "password", Collections.emptyList());

        String token = jwtService.generateToken(userDetails);

        boolean valid = jwtService.isTokenValid(token, otherUser);

        assertThat(valid).isFalse();
    }

}
