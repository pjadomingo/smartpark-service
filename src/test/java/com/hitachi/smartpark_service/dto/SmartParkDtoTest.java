package com.hitachi.smartpark_service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SmartParkDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDto() {
        SmartParkDto dto = new SmartParkDto("ABC-123", "LOT-A1");

        Set<ConstraintViolation<SmartParkDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void testBlankLicensePlate() {
        SmartParkDto dto = new SmartParkDto("", "LOT-A1");

        Set<ConstraintViolation<SmartParkDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getMessage().contains("licensePlate is required field"));
    }

    @Test
    void testInvalidLicensePlatePattern() {
        SmartParkDto dto = new SmartParkDto("ABC@123", "LOT-A1");

        Set<ConstraintViolation<SmartParkDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getMessage().contains("License plate must contain only letters, numbers, and dashes"));
    }

    @Test
    void testBlankLotId() {
        SmartParkDto dto = new SmartParkDto("ABC-123", "");

        Set<ConstraintViolation<SmartParkDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getMessage().contains("lotId is required field"));
    }

    @Test
    void testLotIdTooLong() {
        String longLotId = "X".repeat(60);
        SmartParkDto dto = new SmartParkDto("ABC-123", longLotId);

        Set<ConstraintViolation<SmartParkDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getMessage().contains("Lot ID must not exceed 50 characters"));
    }
}
