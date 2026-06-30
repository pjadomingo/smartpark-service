package com.hitachi.smartpark_service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hitachi.smartpark_service.enums.VehicleType;
import com.hitachi.smartpark_service.model.Vehicle;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class VehicleDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testSettersAndGetters() {
        VehicleDto dto = new VehicleDto();
        dto.setId(1L);
        dto.setLicensePlate("ABC-123");
        dto.setOwnerName("John Doe");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getLicensePlate()).isEqualTo("ABC-123");
        assertThat(dto.getOwnerName()).isEqualTo("John Doe");
    }

    @Test
    void testConstructor() {
        VehicleDto dto = new VehicleDto(2L, "XYZ-999", "Jane Smith", "Truck");

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getLicensePlate()).isEqualTo("XYZ-999");
        assertThat(dto.getOwnerName()).isEqualTo("Jane Smith");
    }

    @Test
    void testFromEntity() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(3L);
        vehicle.setLicensePlate("CAR-456");
        vehicle.setOwnerName("Alice");
        vehicle.setType(VehicleType.Motorcycle);

        VehicleDto dto = VehicleDto.fromEntity(vehicle);

        assertThat(dto).isNotNull();
        assertThat(dto.getLicensePlate()).isEqualTo("CAR-456");
        assertThat(dto.getOwnerName()).isEqualTo("Alice");
    }

    @Test
    void testToEntity() {
        VehicleDto dto = new VehicleDto(4L, "TRK-789", "Bob", "Truck");

        Vehicle vehicle = dto.toEntity();

        assertThat(vehicle.getLicensePlate()).isEqualTo("TRK-789");
        assertThat(vehicle.getOwnerName()).isEqualTo("Bob");
        assertThat(vehicle.getType()).isEqualTo(VehicleType.Truck);
    }

    @Test
    void testValidationInvalidLicensePlate() {
        VehicleDto dto = new VehicleDto(null, "ABC@123", "John Doe", "Car");

        Set<ConstraintViolation<VehicleDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getMessage().contains("License plate must contain only letters, numbers, and dashes"));
    }

    @Test
    void testValidationInvalidOwnerName() {
        VehicleDto dto = new VehicleDto(null, "ABC-123", "John123", "Car");

        Set<ConstraintViolation<VehicleDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getMessage().contains("Owner name must contain only letters and spaces"));
    }

    @Test
    void testValidationInvalidType() {
        VehicleDto dto = new VehicleDto(null, "ABC-123", "John Doe", "Bus");

        Set<ConstraintViolation<VehicleDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getMessage().contains("Type must be Car or Motorcycle or Truck"));
    }
}
