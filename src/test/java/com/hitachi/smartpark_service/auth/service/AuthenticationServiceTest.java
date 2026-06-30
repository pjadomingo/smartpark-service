package com.hitachi.smartpark_service.auth.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hitachi.smartpark_service.auth.repository.UserRepository;
import com.hitachi.smartpark_service.dto.LoginUserDto;
import com.hitachi.smartpark_service.dto.RegisterUserDto;
import com.hitachi.smartpark_service.model.User;

class AuthenticationServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final AuthenticationService service =
            new AuthenticationService(userRepository, authenticationManager, passwordEncoder);

    @Test
    void testSignupPositive() {
        // Arrange
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFullName("Test User");
        dto.setEmail("test@example.com");
        dto.setPassword("plainPassword");

        User savedUser = new User()
                .setFullName("Test User")
                .setEmail("test@example.com")
                .setPassword("encodedPassword");

        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = service.signup(dto);

        // Assert
        assertThat(result.getFullName()).isEqualTo("Test User");
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getPassword()).isEqualTo("encodedPassword");

        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAuthenticatePositive() {
        // Arrange
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("guest@example.com");
        dto.setPassword("guest123");

        User mockUser = new User().setEmail("guest@example.com");

        when(userRepository.findByEmail("guest@example.com")).thenReturn(Optional.of(mockUser));

        // Act
        User result = service.authenticate(dto);

        // Assert
        assertThat(result.getEmail()).isEqualTo("guest@example.com");

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("guest@example.com", "guest123")
        );
        verify(userRepository).findByEmail("guest@example.com");
    }

    @Test
    void testAuthenticateNegative() {
        // Arrange
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("missing@example.com");
        dto.setPassword("badpass");

        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> service.authenticate(dto));

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("missing@example.com", "badpass")
        );
        verify(userRepository).findByEmail("missing@example.com");
    }
}
