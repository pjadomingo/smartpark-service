package com.hitachi.smartpark_service.auth.repository;

import com.hitachi.smartpark_service.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    void testFindByEmailNegative() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        Optional<User> found = userRepository.findByEmail("missing@example.com");

        assertThat(found).isNotPresent();
        verify(userRepository).findByEmail("missing@example.com");
    }
}
