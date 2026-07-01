package com.hitachi.smartpark_service.service;

import com.hitachi.smartpark_service.dto.ParkedVehicleDto;
import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SmartParkServiceTest {

    private ParkingLotService parkingLotService;
    private ParkedVehicleService parkedVehicleService;
    private VehicleService vehicleService;
    private SmartParkService service;

    @BeforeEach
    void setUp() {
        parkingLotService = Mockito.mock(ParkingLotService.class);
        parkedVehicleService = Mockito.mock(ParkedVehicleService.class);
        vehicleService = Mockito.mock(VehicleService.class);

        service = new SmartParkService();
        service.parkingLotService = parkingLotService;
        service.parkedVehicleService = parkedVehicleService;
        service.vehicleService = vehicleService;
    }

    @Test
    void testGetCheckedInVehiclesDelegates() {
        Map<Long, ParkedVehicle> cache = new HashMap<>();
        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(1L);
        cache.put(1L, pv);

        when(parkedVehicleService.getCheckedInVehicles()).thenReturn(cache);

        Map<Long, ParkedVehicle> result = service.getCheckedInVehicles();

        assertThat(result).containsKey(1L);
    }

    @Test
    void testRemoveCheckInVehiclesRemovesFromCache() {
        Map<Long, ParkedVehicle> cache = new HashMap<>();
        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(2L);
        cache.put(2L, pv);

        when(parkedVehicleService.getCheckedInVehicles()).thenReturn(cache);

        service.removeCheckInVehicles(2L);

        assertThat(cache).doesNotContainKey(2L);
    }

    @Test
    void testCheckInVehicleSuccess() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");

        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-A1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(5);

        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(3L);
        pv.setLicensePlate("ABC-123");
        pv.setParkingLotId("LOT-A1");
        pv.setParked(true);

        when(vehicleService.getByLicensePlate("ABC-123")).thenReturn(vehicle);
        when(parkingLotService.findByParkingLotId("LOT-A1")).thenReturn(lot);
        when(parkedVehicleService.isVehicleParked("ABC-123", "LOT-A1")).thenReturn(false);
        when(parkingLotService.occupySpace("LOT-A1")).thenReturn(true);
        when(parkedVehicleService.checkIn(lot, vehicle)).thenReturn(pv);

        ParkedVehicle result = service.checkInVehicle("ABC-123", "LOT-A1");

        assertThat(result).isNotNull();
        assertThat(result.getLicensePlate()).isEqualTo("ABC-123");
        verify(parkedVehicleService).checkIn(lot, vehicle);
    }

    @Test
    void testCheckInVehicleFailsWhenVehicleNotFound() {
        when(vehicleService.getByLicensePlate("MISSING")).thenReturn(null);

        ParkedVehicle result = service.checkInVehicle("MISSING", "LOT-A1");

        assertThat(result).isNull();
        verifyNoInteractions(parkingLotService);
    }

    @Test
    void testCheckOutVehicleSuccess() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("XYZ-999");

        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-B2");

        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(4L);
        pv.setLicensePlate("XYZ-999");
        pv.setParkingLotId("LOT-B2");
        pv.setParked(true);

        ParkedVehicleDto dto = ParkedVehicleDto.fromEntity(pv, lot);

        when(vehicleService.getByLicensePlate("XYZ-999")).thenReturn(vehicle);
        when(parkingLotService.findByParkingLotId("LOT-B2")).thenReturn(lot);
        when(parkedVehicleService.isVehicleParked("XYZ-999", "LOT-B2")).thenReturn(true);
        when(parkingLotService.unOccupySpace("LOT-B2")).thenReturn(true);
        when(parkedVehicleService.checkOut(lot, vehicle)).thenReturn(dto);

        ParkedVehicleDto result = service.checkOutVehicle("XYZ-999", "LOT-B2");

        assertThat(result).isNotNull();
        assertThat(result.getLicensePlate()).isEqualTo("XYZ-999");
        verify(parkedVehicleService).checkOut(lot, vehicle);
    }

    @Test
    void testCheckOutVehicleFailsWhenLotNotFound() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");

        when(vehicleService.getByLicensePlate("ABC-123")).thenReturn(vehicle);
        when(parkingLotService.findByParkingLotId("MISSING")).thenReturn(null);

        ParkedVehicleDto result = service.checkOutVehicle("ABC-123", "MISSING");

        assertThat(result).isNull();
        verifyNoInteractions(parkedVehicleService);
    }
}
