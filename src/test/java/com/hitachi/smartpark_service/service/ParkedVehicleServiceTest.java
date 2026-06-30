package com.hitachi.smartpark_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hitachi.smartpark_service.dto.ParkedVehicleDto;
import com.hitachi.smartpark_service.mapper.ParkedVehicleMapper;
import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.model.Vehicle;
import com.hitachi.smartpark_service.repository.ParkedVehicleRepository;

class ParkedVehicleServiceTest {

    private ParkedVehicleRepository repository;
    private ParkedVehicleService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ParkedVehicleRepository.class);
        service = new ParkedVehicleService();
        service.repository = repository; // inject mock
    }

    @Test
    void testGetParkedVehiclesByLot() {
        ParkedVehicle pv = new ParkedVehicle();
        pv.setLicensePlate("ABC-123");
        pv.setParkingLotId("LOT-A1");
        pv.setParked(true);

        when(repository.findByParkingLotIdAndIsParked("LOT-A1", true))
                .thenReturn(List.of(pv));

        List<ParkedVehicle> result = service.getParkedVehiclesByLot("LOT-A1");

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLicensePlate()).isEqualTo("ABC-123");
    }

    @Test
    void testIsVehicleParkedTrue() {
        when(repository.existsByLicensePlateAndParkingLotIdAndIsParked("XYZ-999", "LOT-B2", true))
                .thenReturn(true);

        boolean result = service.isVehicleParked("XYZ-999", "LOT-B2");

        assertThat(result).isTrue();
    }

    @Test
    void testCheckOutUpdatesRecord() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-D4");

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("TEST-123");

        ParkedVehicle pv = ParkedVehicleMapper.mapToParkedVehicle(lot, vehicle, true);
        pv.setId(2L);

        when(repository.findByLicensePlateAndIsParked("TEST-123", true))
                .thenReturn(Optional.of(pv));
        when(repository.save(any(ParkedVehicle.class))).thenReturn(pv);

        service.getCheckedInVehicles().put(2L, pv);

        ParkedVehicleDto result = service.checkOut(lot, vehicle);

        assertThat(result).isNotNull();
        assertThat(result.getLicensePlate()).isEqualTo("TEST-123");
        assertThat(service.getCheckedInVehicles()).doesNotContainKey(2L);
    }
}
