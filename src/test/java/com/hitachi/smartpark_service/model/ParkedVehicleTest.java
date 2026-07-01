package com.hitachi.smartpark_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;

class ParkedVehicleTest {

    @Test
    void testSettersAndGetters() {
        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(1L);
        pv.setLicensePlate("ABC-123");
        pv.setParkingLotId("LOT-A1");
        pv.setParked(true);
        Date now = new Date();
        pv.setCheckInDate(now);

        assertThat(pv.getId()).isEqualTo(1L);
        assertThat(pv.getLicensePlate()).isEqualTo("ABC-123");
        assertThat(pv.getParkingLotId()).isEqualTo("LOT-A1");
        assertThat(pv.isParked()).isTrue();
        assertThat(pv.getCheckInDate()).isEqualTo(now);
    }
}
