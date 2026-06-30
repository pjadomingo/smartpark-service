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

    @Test
    void testPrePersistSetsCheckInDate() {
        ParkedVehicle pv = new ParkedVehicle();
        pv.setLicensePlate("XYZ-999");
        pv.setParkingLotId("LOT-B2");
        pv.setParked(true);

        pv.onCreate(); // simulate JPA persist

        assertThat(pv.getCheckInDate()).isNotNull();
        assertThat(pv.getCheckInDate()).isCloseTo(new Date(), 1000); // within 1 second
    }

    @Test
    void testPreUpdateRefreshesCheckInDate() throws InterruptedException {
        ParkedVehicle pv = new ParkedVehicle();
        pv.onCreate();
        Date firstDate = pv.getCheckInDate();

        Thread.sleep(10); // ensure time difference
        pv.onUpdate(); // simulate JPA update

        assertThat(pv.getCheckInDate()).isNotNull();
        assertThat(pv.getCheckInDate()).isAfter(firstDate);
    }
}
