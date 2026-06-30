package com.hitachi.smartpark_service.scheduler;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.service.SmartParkService;

class SmartParkSchedulerTest {

    private SmartParkService service;
    private SmartParkScheduler scheduler;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(SmartParkService.class);
        scheduler = new SmartParkScheduler();
        // inject mock service
        scheduler.service = service;
    }

    @Test
    void testAutoCheckOutVehiclesOlderThan15Minutes() {
        // Arrange: vehicle checked in 20 minutes ago
        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(1L);
        pv.setLicensePlate("ABC-123");
        pv.setParkingLotId("LOT-A1");
        pv.setCheckInDate(new Date(System.currentTimeMillis() - (20 * 60 * 1000))); // 20 minutes ago

        Map<Long, ParkedVehicle> checkedIn = new HashMap<>();
        checkedIn.put(pv.getId(), pv);

        when(service.getCheckedInVehicles()).thenReturn(checkedIn);

        // Act
        scheduler.autoCheckOutVehicles();

        // Assert: verify service methods called
        verify(service).checkOutVehicle("ABC-123", "LOT-A1");
        verify(service).removeCheckInVehicles(1L);
    }

    @Test
    void testAutoCheckOutVehiclesYoungerThan15Minutes() {
        // Arrange: vehicle checked in 5 minutes ago
        ParkedVehicle pv = new ParkedVehicle();
        pv.setId(2L);
        pv.setLicensePlate("XYZ-999");
        pv.setParkingLotId("LOT-B2");
        pv.setCheckInDate(new Date(System.currentTimeMillis() - (5 * 60 * 1000))); // 5 minutes ago

        Map<Long, ParkedVehicle> checkedIn = new HashMap<>();
        checkedIn.put(pv.getId(), pv);

        when(service.getCheckedInVehicles()).thenReturn(checkedIn);

        // Act
        scheduler.autoCheckOutVehicles();

        // Assert: verify service methods NOT called
        verify(service, never()).checkOutVehicle(anyString(), anyString());
        verify(service, never()).removeCheckInVehicles(anyLong());
    }
}
