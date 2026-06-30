package com.hitachi.smartpark_service.auth.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.hitachi.smartpark_service.auth.service.AuthenticationService;
import com.hitachi.smartpark_service.config.JwtService;
import com.hitachi.smartpark_service.dto.LoginResponse;
import com.hitachi.smartpark_service.dto.LoginUserDto;
import com.hitachi.smartpark_service.model.User;

class AuthenticationControllerTest {

    private final JwtService jwtService = mock(JwtService.class);
    private final AuthenticationService authenticationService = mock(AuthenticationService.class);

    private final AuthenticationController controller =
            new AuthenticationController(jwtService, authenticationService);

    @Test
    void testAuthenticatePositive() {
        // Arrange
        LoginUserDto loginDto = new LoginUserDto();
        loginDto.setEmail("guest@example.com");
        loginDto.setPassword("guest123");

        User mockUser = new User();
        mockUser.setEmail("guest@example.com");

        when(authenticationService.authenticate(loginDto)).thenReturn(mockUser);
        when(jwtService.generateToken(mockUser)).thenReturn("mock-jwt-token");
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        // Act
        ResponseEntity<?> response = controller.authenticate(loginDto);

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isInstanceOf(LoginResponse.class);

        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertThat(loginResponse.getToken()).isEqualTo("mock-jwt-token");
        assertThat(loginResponse.getExpiresIn()).isEqualTo(3600L);

        verify(authenticationService).authenticate(loginDto);
        verify(jwtService).generateToken(mockUser);
    }

    @Test
    void testAuthenticateNegative() {
        // Arrange
        LoginUserDto loginDto = new LoginUserDto();
        loginDto.setEmail("wrong@example.com");
        loginDto.setPassword("badpass");

        when(authenticationService.authenticate(loginDto))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        try {
            controller.authenticate(loginDto);
        } catch (RuntimeException ex) {
            assertThat(ex.getMessage()).isEqualTo("Invalid credentials");
        }

        verify(authenticationService).authenticate(loginDto);
        verify(jwtService, never()).generateToken(any());
    }
}
