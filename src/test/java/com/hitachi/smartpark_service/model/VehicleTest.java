package com.hitachi.smartpark_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.hitachi.smartpark_service.enums.VehicleType;

class VehicleTest {

    @Test
    void testSettersAndGetters() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setLicensePlate("ABC-123");
        vehicle.setOwnerName("John Doe");
        vehicle.setType(VehicleType.Car);

        assertThat(vehicle.getId()).isEqualTo(1L);
        assertThat(vehicle.getLicensePlate()).isEqualTo("ABC-123");
        assertThat(vehicle.getOwnerName()).isEqualTo("John Doe");
        assertThat(vehicle.getType()).isEqualTo(VehicleType.Car);
    }

    @Test
    void testEnumMappingMotorcycle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(VehicleType.Motorcycle);

        assertThat(vehicle.getType()).isEqualTo(VehicleType.Motorcycle);
    }

    @Test
    void testEnumMappingTruck() {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(VehicleType.Truck);

        assertThat(vehicle.getType()).isEqualTo(VehicleType.Truck);
    }

    @Test
    void testUniqueLicensePlateConstraint() {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("XYZ-999");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("XYZ-999");

        // At the entity level, both can be set.
        assertThat(vehicle1.getLicensePlate()).isEqualTo(vehicle2.getLicensePlate());
        // The uniqueness constraint is enforced at the DB layer, not in the entity itself.
    }
}
