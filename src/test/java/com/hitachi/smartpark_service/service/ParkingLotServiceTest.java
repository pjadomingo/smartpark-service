package com.hitachi.smartpark_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hitachi.smartpark_service.dto.ParkingLotDto;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.repository.ParkingLotRepository;

class ParkingLotServiceTest {

    private ParkingLotRepository repository;
    private ParkingLotService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ParkingLotRepository.class);
        service = new ParkingLotService();
        service.repository = repository; // inject mock
    }

    @Test
    void testCreateParkingLot() {
        ParkingLotDto dto = new ParkingLotDto();
        dto.setLotId("LOT-A1");
        dto.setLocation("Makati");
        dto.setCapacity(50);
        dto.setOccupiedSpaces(10);
        dto.setCostPerMinute(5.0);

        ParkingLot lot = dto.toEntity();
        lot.setId(1L);

        when(repository.save(any(ParkingLot.class))).thenReturn(lot);

        ParkingLot result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getLotId()).isEqualTo("LOT-A1");
        verify(repository).save(any(ParkingLot.class));
    }

    @Test
    void testFindByLotIdReturnsResult() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-B2");
        lot.setCapacity(100);

        when(repository.findByLotId("LOT-B2")).thenReturn(Optional.of(lot));

        ParkingLot result = service.findByLotId("LOT-B2");

        assertThat(result).isNotNull();
        assertThat(result.getCapacity()).isEqualTo(100);
    }

    @Test
    void testFindByLotIdReturnsNullWhenNotFound() {
        when(repository.findByLotId("NON_EXISTENT")).thenReturn(Optional.empty());

        ParkingLot result = service.findByLotId("NON_EXISTENT");

        assertThat(result).isNull();
    }

    @Test
    void testOccupySpaceSuccess() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-C3");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(5);

        when(repository.findByLotId("LOT-C3")).thenReturn(Optional.of(lot));

        boolean result = service.occupySpace("LOT-C3");

        assertThat(result).isTrue();
        assertThat(lot.getOccupiedSpaces()).isEqualTo(6);
        verify(repository).save(lot);
    }

    @Test
    void testOccupySpaceFailsWhenFull() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-C3");
        lot.setCapacity(5);
        lot.setOccupiedSpaces(5);

        when(repository.findByLotId("LOT-C3")).thenReturn(Optional.of(lot));

        boolean result = service.occupySpace("LOT-C3");

        assertThat(result).isFalse();
        verify(repository, never()).save(any(ParkingLot.class));
    }

    @Test
    void testUnOccupySpaceSuccess() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-D4");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(3);

        when(repository.findByLotId("LOT-D4")).thenReturn(Optional.of(lot));

        boolean result = service.unOccupySpace("LOT-D4");

        assertThat(result).isTrue();
        assertThat(lot.getOccupiedSpaces()).isEqualTo(2);
        verify(repository).save(lot);
    }

    @Test
    void testUnOccupySpaceFailsWhenZero() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-D4");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(0);

        when(repository.findByLotId("LOT-D4")).thenReturn(Optional.of(lot));

        boolean result = service.unOccupySpace("LOT-D4");

        assertThat(result).isFalse();
        verify(repository, never()).save(any(ParkingLot.class));
    }
}
