package com.hitachi.smartpark_service.dto;

import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ParkedVehicleDtoTest {

    @Test
    void testSettersAndGetters() {
        ParkedVehicleDto dto = new ParkedVehicleDto();
        dto.setId(1L);
        dto.setLicensePlate("ABC-123");
        dto.setParkingLotId("LOT-A1");
        dto.setParked(true);
        dto.setParkingCost(50.0);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getLicensePlate()).isEqualTo("ABC-123");
        assertThat(dto.getParkingLotId()).isEqualTo("LOT-A1");
        assertThat(dto.isParked()).isTrue();
        assertThat(dto.getParkingCost()).isEqualTo(50.0);
    }

    @Test
    void testConstructor() {
        ParkedVehicleDto dto = new ParkedVehicleDto(2L, "XYZ-999", "LOT-B2", false, 120.0);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getLicensePlate()).isEqualTo("XYZ-999");
        assertThat(dto.getParkingLotId()).isEqualTo("LOT-B2");
        assertThat(dto.isParked()).isFalse();
        assertThat(dto.getParkingCost()).isEqualTo(120.0);
    }

    @Test
    void testFromEntityCalculatesCost() {
        // Arrange: check-in 10 minutes ago
        Date checkIn = new Date(System.currentTimeMillis() - (10 * 60 * 1000));
        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(3L);
        pv.setLicensePlate("TEST-123");
        pv.setParkingLotId("LOT-C3");
        pv.setParked(true);
        pv.setCheckInDate(checkIn);

        ParkingLot lot = new ParkingLot();
        lot.setCostPerMinute(2.0); // 2 currency units per minute

        // Act
        ParkedVehicleDto dto = ParkedVehicleDto.fromEntity(pv, lot);

        // Assert: 10 minutes * 2.0 = 20.0
        assertThat(dto).isNotNull();
        assertThat(dto.getLicensePlate()).isEqualTo("TEST-123");
        assertThat(dto.getParkingCost()).isEqualTo(20.0);
    }

    @Test
    void testFromEntityNullInputs() {
        assertThat(ParkedVehicleDto.fromEntity(null, null)).isNull();
    }
}
