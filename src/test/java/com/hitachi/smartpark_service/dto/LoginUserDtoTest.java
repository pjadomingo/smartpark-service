package com.hitachi.smartpark_service.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginUserDtoTest {

    @Test
    void testSettersAndGetters() {
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("test@example.com");
        dto.setPassword("secret123");

        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getPassword()).isEqualTo("secret123");
    }

    @Test
    void testDefaultValues() {
        LoginUserDto dto = new LoginUserDto();

        assertThat(dto.getEmail()).isNull();
        assertThat(dto.getPassword()).isNull();
    }

    @Test
    void testUpdateValues() {
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("first@example.com");
        dto.setPassword("firstPass");

        dto.setEmail("second@example.com");
        dto.setPassword("secondPass");

        assertThat(dto.getEmail()).isEqualTo("second@example.com");
        assertThat(dto.getPassword()).isEqualTo("secondPass");
    }
}
