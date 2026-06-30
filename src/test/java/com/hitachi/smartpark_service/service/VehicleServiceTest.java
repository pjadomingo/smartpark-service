package com.hitachi.smartpark_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hitachi.smartpark_service.dto.VehicleDto;
import com.hitachi.smartpark_service.enums.VehicleType;
import com.hitachi.smartpark_service.model.Vehicle;
import com.hitachi.smartpark_service.repository.VehicleRepository;

class VehicleServiceTest {

    private VehicleRepository repository;
    private VehicleService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(VehicleRepository.class);
        service = new VehicleService();
        service.repository = repository; // inject mock
    }

    @Test
    void testCreateVehicleSuccess() {
        VehicleDto dto = new VehicleDto(1L, "ABC-123", "John Doe", "Car");
        Vehicle entity = dto.toEntity();
        entity.setId(1L);

        when(repository.save(any(Vehicle.class))).thenReturn(entity);

        Vehicle result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getLicensePlate()).isEqualTo("ABC-123");
        assertThat(result.getOwnerName()).isEqualTo("John Doe");
        assertThat(result.getType()).isEqualTo(VehicleType.Car);
        verify(repository).save(any(Vehicle.class));
    }

    @Test
    void testGetByLicensePlateReturnsResult() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("XYZ-999");
        vehicle.setOwnerName("Jane Smith");
        vehicle.setType(VehicleType.Truck);

        when(repository.findByLicensePlate("XYZ-999")).thenReturn(Optional.of(vehicle));

        Vehicle result = service.getByLicensePlate("XYZ-999");

        assertThat(result).isNotNull();
        assertThat(result.getOwnerName()).isEqualTo("Jane Smith");
        assertThat(result.getType()).isEqualTo(VehicleType.Truck);
    }

    @Test
    void testGetByLicensePlateReturnsNullWhenNotFound() {
        when(repository.findByLicensePlate("MISSING")).thenReturn(Optional.empty());

        Vehicle result = service.getByLicensePlate("MISSING");

        assertThat(result).isNull();
    }

    @Test
    void testCreateVehicleHandlesException() {
        VehicleDto dto = new VehicleDto(2L, "ERR-001", "Error Case", "Motorcycle");

        when(repository.save(any(Vehicle.class))).thenThrow(new RuntimeException("DB error"));

        Vehicle result = service.create(dto);

        assertThat(result).isNull();
    }
}
