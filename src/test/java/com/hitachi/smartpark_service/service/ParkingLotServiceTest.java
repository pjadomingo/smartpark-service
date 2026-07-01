package com.hitachi.smartpark_service.service;

import com.hitachi.smartpark_service.dto.ParkingLotDto;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParkingLotServiceTest {

    private ParkingLotRepository repository;
    private ParkingLotService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ParkingLotRepository.class);
        service = new ParkingLotService();
        service.repository = repository;
    }

    @Test
    void testCreateSuccess() {
        ParkingLotDto dto = new ParkingLotDto();
        dto.setLotId("LOT-A1");
        dto.setLocation("Makati");
        dto.setCapacity(50);
        dto.setOccupiedSpaces(10);
        dto.setCostPerMinute(5.0);

        ParkingLot entity = dto.toEntity();

        when(repository.existsByLotId("LOT-A1")).thenReturn(false);
        when(repository.save(any(ParkingLot.class))).thenReturn(entity);

        ParkingLotDto result = service.create(dto);

        assertThat(result.getStatusTransaction()).isEqualTo("Registered Successfully.");
        verify(repository).save(any(ParkingLot.class));
    }

    @Test
    void testFindByLotIdFound() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-B2");
        lot.setLocation("Taguig");

        when(repository.findByLotId("LOT-B2")).thenReturn(Optional.of(lot));

        ParkingLotDto result = service.findByLotId("LOT-B2");

        assertThat(result.getStatusTransaction()).isEqualTo("Found Parking lot.");
        assertThat(result.getLotId()).isEqualTo("LOT-B2");
    }

    @Test
    void testFindByLotIdNotFound() {
        when(repository.findByLotId("MISSING")).thenReturn(Optional.empty());

        ParkingLotDto result = service.findByLotId("MISSING");

        assertThat(result.getStatusTransaction()).isEqualTo("Not Found Parking Lot.");
        assertThat(result.getLotId()).isEqualTo("MISSING");
    }

    @Test
    void testFindByParkingLotIdFound() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-C1");

        when(repository.findByLotId("LOT-C1")).thenReturn(Optional.of(lot));

        ParkingLot result = service.findByParkingLotId("LOT-C1");

        assertThat(result).isNotNull();
        assertThat(result.getLotId()).isEqualTo("LOT-C1");
    }

    @Test
    void testFindByParkingLotIdNotFound() {
        when(repository.findByLotId("MISSING")).thenReturn(Optional.empty());

        ParkingLot result = service.findByParkingLotId("MISSING");

        assertThat(result).isNull();
    }

    @Test
    void testOccupySpaceSuccess() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-D1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(5);

        when(repository.findByLotId("LOT-D1")).thenReturn(Optional.of(lot));
        when(repository.save(any(ParkingLot.class))).thenReturn(lot);

        boolean result = service.occupySpace("LOT-D1");

        assertThat(result).isTrue();
        assertThat(lot.getOccupiedSpaces()).isEqualTo(6);
        verify(repository).save(lot);
    }

    @Test
    void testOccupySpaceFailsWhenFull() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-FULL");
        lot.setCapacity(5);
        lot.setOccupiedSpaces(5);

        when(repository.findByLotId("LOT-FULL")).thenReturn(Optional.of(lot));

        boolean result = service.occupySpace("LOT-FULL");

        assertThat(result).isFalse();
        verify(repository, never()).save(any(ParkingLot.class));
    }

    @Test
    void testUnOccupySpaceSuccess() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-E1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(3);

        when(repository.findByLotId("LOT-E1")).thenReturn(Optional.of(lot));
        when(repository.save(any(ParkingLot.class))).thenReturn(lot);

        boolean result = service.unOccupySpace("LOT-E1");

        assertThat(result).isTrue();
        assertThat(lot.getOccupiedSpaces()).isEqualTo(2);
        verify(repository).save(lot);
    }

    @Test
    void testUnOccupySpaceFailsWhenZero() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-EMPTY");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(0);

        when(repository.findByLotId("LOT-EMPTY")).thenReturn(Optional.of(lot));

        boolean result = service.unOccupySpace("LOT-EMPTY");

        assertThat(result).isFalse();
        verify(repository, never()).save(any(ParkingLot.class));
    }
}
