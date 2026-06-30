package com.hitachi.smartpark_service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoginResponseTest {

    @Test
    void testSettersAndGetters() {
        LoginResponse response = new LoginResponse()
                .setToken("mock-token")
                .setExpiresIn(3600L);

        assertThat(response.getToken()).isEqualTo("mock-token");
        assertThat(response.getExpiresIn()).isEqualTo(3600L);
    }

    @Test
    void testFluentSettersChaining() {
        LoginResponse response = new LoginResponse();
        response.setToken("chain-token").setExpiresIn(7200L);

        assertThat(response.getToken()).isEqualTo("chain-token");
        assertThat(response.getExpiresIn()).isEqualTo(7200L);
    }

    @Test
    void testDefaultValues() {
        LoginResponse response = new LoginResponse();

        assertThat(response.getToken()).isNull();
        assertThat(response.getExpiresIn()).isEqualTo(0L);
    }
}
