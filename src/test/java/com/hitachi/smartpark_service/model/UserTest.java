package com.hitachi.smartpark_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId(1);
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("secret123");
        Date created = new Date();
        Date updated = new Date();
        user.setCreatedAt(created);
        user.setUpdatedAt(updated);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFullName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getPassword()).isEqualTo("secret123");
        assertThat(user.getCreatedAt()).isEqualTo(created);
        assertThat(user.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testFluentSetters() {
        User user = new User()
                .setFullName("Jane Smith")
                .setEmail("jane@example.com")
                .setPassword("pass456");

        assertThat(user.getFullName()).isEqualTo("Jane Smith");
        assertThat(user.getEmail()).isEqualTo("jane@example.com");
        assertThat(user.getPassword()).isEqualTo("pass456");
    }

    @Test
    void testUserDetailsContract() {
        User user = new User()
                .setEmail("user@example.com")
                .setPassword("pwd");

        assertThat(user.getUsername()).isEqualTo("user@example.com");
        assertThat(user.getAuthorities()).isEmpty();
        assertThat(user.isAccountNonExpired()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isCredentialsNonExpired()).isTrue();
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    void testAuthoritiesAreEmptyList() {
        User user = new User();
        assertThat(user.getAuthorities()).isInstanceOf(java.util.List.class);
        assertThat(user.getAuthorities()).isEmpty();
    }
}
