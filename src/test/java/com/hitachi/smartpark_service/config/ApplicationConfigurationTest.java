package com.hitachi.smartpark_service.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hitachi.smartpark_service.auth.repository.UserRepository;
import com.hitachi.smartpark_service.model.User;

class ApplicationConfigurationTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final ApplicationConfiguration config = new ApplicationConfiguration(userRepository);

    @Test
    void testUserDetailsServicePositive() {
        // Arrange
        User user = new User().setEmail("test@example.com").setPassword("encoded");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetailsService uds = config.userDetailsService();

        // Act
        var details = uds.loadUserByUsername("test@example.com");

        // Assert
        assertThat(details.getUsername()).isEqualTo("test@example.com");
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void testUserDetailsServiceNegative() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        UserDetailsService uds = config.userDetailsService();

        assertThrows(UsernameNotFoundException.class,
                () -> uds.loadUserByUsername("missing@example.com"));
    }

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder encoder = config.passwordEncoder();
        String raw = "secret123";
        String encoded = encoder.encode(raw);

        assertThat(encoder.matches(raw, encoded)).isTrue();
    }

    @Test
    void testAuthenticationProvider() {
        AuthenticationProvider provider = config.authenticationProvider();

        assertThat(provider).isNotNull();
        assertThat(provider).isInstanceOf(org.springframework.security.authentication.dao.DaoAuthenticationProvider.class);
    }

    @Test
    void testAuthenticationManagerBean() throws Exception {
        // AuthenticationConfiguration is normally provided by Spring context.
        // Here we just verify that the bean method exists and can be called.
        AuthenticationConfiguration mockConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        when(mockConfig.getAuthenticationManager()).thenReturn(mockManager);

        AuthenticationManager manager = new ApplicationConfiguration(userRepository)
                .authenticationManager(mockConfig);

        assertThat(manager).isEqualTo(mockManager);
    }
}
