package com.hitachi.smartpark_service.repository;

import com.hitachi.smartpark_service.enums.VehicleType;
import com.hitachi.smartpark_service.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VehicleRepositoryMockTest {

    private VehicleRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(VehicleRepository.class);
    }

    @Test
    void testFindByLicensePlateReturnsResult() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setLicensePlate("ABC-123");
        vehicle.setOwnerName("John Doe");
        vehicle.setType(VehicleType.Car);

        // stub repository behavior
        when(repository.findByLicensePlate("ABC-123")).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> result = repository.findByLicensePlate("ABC-123");

        assertThat(result).isPresent();
        assertThat(result.get().getOwnerName()).isEqualTo("John Doe");
        assertThat(result.get().getType()).isEqualTo(VehicleType.Car);

        // verify interaction
        verify(repository).findByLicensePlate("ABC-123");
    }

    @Test
    void testFindByLicensePlateReturnsEmptyWhenNotFound() {
        when(repository.findByLicensePlate("NON_EXISTENT")).thenReturn(Optional.empty());

        Optional<Vehicle> result = repository.findByLicensePlate("NON_EXISTENT");

        assertThat(result).isEmpty();
        verify(repository).findByLicensePlate("NON_EXISTENT");
    }

    @Test
    void testUniqueLicensePlateConstraintSimulation() {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("XYZ-999");
        vehicle1.setOwnerName("Jane Smith");
        vehicle1.setType(VehicleType.Truck);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("XYZ-999"); // duplicate license plate
        vehicle2.setOwnerName("Alice");
        vehicle2.setType(VehicleType.Motorcycle);

        // simulate repository returning first vehicle
        when(repository.findByLicensePlate("XYZ-999")).thenReturn(Optional.of(vehicle1));

        Optional<Vehicle> result = repository.findByLicensePlate("XYZ-999");

        assertThat(result).isPresent();
        assertThat(result.get().getOwnerName()).isEqualTo("Jane Smith");

        verify(repository).findByLicensePlate("XYZ-999");
    }
}
