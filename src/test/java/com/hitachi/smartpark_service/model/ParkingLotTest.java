package com.hitachi.smartpark_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ParkingLotTest {

    @Test
    void testSettersAndGetters() {
        ParkingLot lot = new ParkingLot();
        lot.setId(1L);
        lot.setLotId("LOT-A1");
        lot.setLocation("Makati CBD");
        lot.setCapacity(50);
        lot.setOccupiedSpaces(10);
        lot.setCostPerMinute(5.0);

        assertThat(lot.getId()).isEqualTo(1L);
        assertThat(lot.getLotId()).isEqualTo("LOT-A1");
        assertThat(lot.getLocation()).isEqualTo("Makati CBD");
        assertThat(lot.getCapacity()).isEqualTo(50);
        assertThat(lot.getOccupiedSpaces()).isEqualTo(10);
        assertThat(lot.getCostPerMinute()).isEqualTo(5.0);
    }

    @Test
    void testIsFullWhenNotFull() {
        ParkingLot lot = new ParkingLot();
        lot.setCapacity(100);
        lot.setOccupiedSpaces(50);

        assertThat(lot.isFull()).isFalse();
    }

    @Test
    void testIsFullWhenExactlyFull() {
        ParkingLot lot = new ParkingLot();
        lot.setCapacity(100);
        lot.setOccupiedSpaces(100);

        assertThat(lot.isFull()).isTrue();
    }

    @Test
    void testIsFullWhenOverCapacity() {
        ParkingLot lot = new ParkingLot();
        lot.setCapacity(100);
        lot.setOccupiedSpaces(120);

        assertThat(lot.isFull()).isTrue();
    }
}
