package com.hitachi.smartpark_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hitachi.smartpark_service.auth.repository.UserRepository;
import com.hitachi.smartpark_service.model.User;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setFullName("Admin User");
                admin.setEmail("admin@example.com");
                admin.setPassword(encoder.encode("password123"));
                userRepository.save(admin);

                User guest = new User();
                guest.setFullName("Guest User");
                guest.setEmail("guest@example.com");
                guest.setPassword(encoder.encode("guest123"));
                userRepository.save(guest);
            }
        };
    }
}

