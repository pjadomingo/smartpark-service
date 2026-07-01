package com.hitachi.smartpark_service.service;

import com.hitachi.smartpark_service.dto.VehicleDto;
import com.hitachi.smartpark_service.model.Vehicle;
import com.hitachi.smartpark_service.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    private VehicleRepository repository;
    private VehicleService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(VehicleRepository.class);
        service = new VehicleService();
        service.repository = repository;
    }

    @Test
    void testCreateSuccess() {
        VehicleDto dto = new VehicleDto(null, "ABC-123", "John Doe", "Car");
        Vehicle entity = dto.toEntity();
        entity.setId(1L);

        when(repository.existsByLicensePlate("ABC-123")).thenReturn(false);
        when(repository.save(any(Vehicle.class))).thenReturn(entity);

        VehicleDto result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getLicensePlate()).isEqualTo("ABC-123");
        assertThat(result.getStatusTransaction()).isEqualTo("Registered Successfully.");
        verify(repository).save(any(Vehicle.class));
    }

    @Test
    void testCreateDuplicateLicensePlate() {
        VehicleDto dto = new VehicleDto(null, "XYZ-999", "Jane Doe", "Truck");

        when(repository.existsByLicensePlate("XYZ-999")).thenReturn(true);

        VehicleDto result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getStatusTransaction()).isEqualTo("Registered Failed, duplicate license plate.");
        verify(repository, never()).save(any(Vehicle.class));
    }

    @Test
    void testGetByLicensePlateFound() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(2L);
        vehicle.setLicensePlate("CAR-456");
        vehicle.setOwnerName("Maria Santos");

        when(repository.findByLicensePlate("CAR-456")).thenReturn(Optional.of(vehicle));

        Vehicle result = service.getByLicensePlate("CAR-456");

        assertThat(result).isNotNull();
        assertThat(result.getLicensePlate()).isEqualTo("CAR-456");
        assertThat(result.getOwnerName()).isEqualTo("Maria Santos");
    }

    @Test
    void testGetByLicensePlateNotFound() {
        when(repository.findByLicensePlate("MISSING")).thenReturn(Optional.empty());

        Vehicle result = service.getByLicensePlate("MISSING");

        assertThat(result).isNull();
    }
}
