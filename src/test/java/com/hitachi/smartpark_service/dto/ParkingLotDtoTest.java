package com.hitachi.smartpark_service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.hitachi.smartpark_service.model.ParkingLot;

class ParkingLotDtoTest {

    @Test
    void testSettersAndGetters() {
        ParkingLotDto dto = new ParkingLotDto();
        dto.setId(1L);
        dto.setLotId("LOT-A1");
        dto.setLocation("Makati CBD");
        dto.setCapacity(50);
        dto.setOccupiedSpaces(10);
        dto.setCostPerMinute(5.0);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getLotId()).isEqualTo("LOT-A1");
        assertThat(dto.getLocation()).isEqualTo("Makati CBD");
        assertThat(dto.getCapacity()).isEqualTo(50);
        assertThat(dto.getOccupiedSpaces()).isEqualTo(10);
        assertThat(dto.getCostPerMinute()).isEqualTo(5.0);
    }

    @Test
    void testConstructor() {
        ParkingLotDto dto = new ParkingLotDto(2L, "LOT-B2", "BGC", 100, 20, 10.0);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getLotId()).isEqualTo("LOT-B2");
        assertThat(dto.getLocation()).isEqualTo("BGC");
        assertThat(dto.getCapacity()).isEqualTo(100);
        assertThat(dto.getOccupiedSpaces()).isEqualTo(20);
        assertThat(dto.getCostPerMinute()).isEqualTo(10.0);
    }

    @Test
    void testFromEntity() {
        ParkingLot lot = new ParkingLot();
        lot.setId(3L);
        lot.setLotId("LOT-C3");
        lot.setLocation("Ortigas");
        lot.setCapacity(200);
        lot.setOccupiedSpaces(50);
        lot.setCostPerMinute(15.0);

        ParkingLotDto dto = ParkingLotDto.fromEntity(lot);

        assertThat(dto).isNotNull();
        assertThat(dto.getLotId()).isEqualTo("LOT-C3");
        assertThat(dto.getLocation()).isEqualTo("Ortigas");
        assertThat(dto.getCapacity()).isEqualTo(200);
        assertThat(dto.getOccupiedSpaces()).isEqualTo(50);
        assertThat(dto.getCostPerMinute()).isEqualTo(15.0);
    }

    @Test
    void testToEntity() {
        ParkingLotDto dto = new ParkingLotDto(4L, "LOT-D4", "Quezon City", 300, 75, 20.0);

        ParkingLot lot = dto.toEntity();

        assertThat(lot.getId()).isEqualTo(4L);
        assertThat(lot.getLotId()).isEqualTo("LOT-D4");
        assertThat(lot.getLocation()).isEqualTo("Quezon City");
        assertThat(lot.getCapacity()).isEqualTo(300);
        assertThat(lot.getOccupiedSpaces()).isEqualTo(75);
        assertThat(lot.getCostPerMinute()).isEqualTo(20.0);
    }

    @Test
    void testFromEntityNull() {
        assertThat(ParkingLotDto.fromEntity(null)).isNull();
    }
}
