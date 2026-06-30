package com.hitachi.smartpark_service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.model.Vehicle;

class ParkedVehicleMapperTest {

    @Test
    void testMapToParkedVehicleValidInputs() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-A1");

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");

        ParkedVehicle parkedVehicle = ParkedVehicleMapper.mapToParkedVehicle(lot, vehicle, true);

        assertThat(parkedVehicle).isNotNull();
        assertThat(parkedVehicle.getLicensePlate()).isEqualTo("ABC-123");
        assertThat(parkedVehicle.getParkingLotId()).isEqualTo("LOT-A1");
        assertThat(parkedVehicle.isParked()).isTrue();
    }

    @Test
    void testMapToParkedVehicleWithNullParkingLotThrowsException() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("XYZ-999");

        assertThrows(IllegalArgumentException.class,
                () -> ParkedVehicleMapper.mapToParkedVehicle(null, vehicle, true),
                "ParkingLot and Vehicle must not be null");
    }

    @Test
    void testMapToParkedVehicleWithNullVehicleThrowsException() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-B2");

        assertThrows(IllegalArgumentException.class,
                () -> ParkedVehicleMapper.mapToParkedVehicle(lot, null, false),
                "ParkingLot and Vehicle must not be null");
    }

    @Test
    void testMapToParkedVehicleIsParkedFalse() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-C3");

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("TEST-123");

        ParkedVehicle parkedVehicle = ParkedVehicleMapper.mapToParkedVehicle(lot, vehicle, false);

        assertThat(parkedVehicle.isParked()).isFalse();
    }
}
