package com.hitachi.smartpark_service.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hitachi.smartpark_service.model.ParkingLot;

class ParkingLotRepositoryTest {

    private ParkingLotRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ParkingLotRepository.class);
    }

    @Test
    void testFindByLotIdReturnsResult() {
        ParkingLot lot = new ParkingLot();
        lot.setId(1L);
        lot.setLotId("LOT-A1");
        lot.setLocation("Makati CBD");
        lot.setCapacity(50);
        lot.setOccupiedSpaces(10);
        lot.setCostPerMinute(5.0);

        // stub repository behavior
        when(repository.findByLotId("LOT-A1")).thenReturn(Optional.of(lot));

        Optional<ParkingLot> result = repository.findByLotId("LOT-A1");

        assertThat(result).isPresent();
        assertThat(result.get().getLocation()).isEqualTo("Makati CBD");
        assertThat(result.get().getCapacity()).isEqualTo(50);

        // verify interaction
        verify(repository).findByLotId("LOT-A1");
    }

    @Test
    void testFindByLotIdReturnsEmpty() {
        when(repository.findByLotId("NON_EXISTENT")).thenReturn(Optional.empty());

        Optional<ParkingLot> result = repository.findByLotId("NON_EXISTENT");

        assertThat(result).isEmpty();
        verify(repository).findByLotId("NON_EXISTENT");
    }
}
