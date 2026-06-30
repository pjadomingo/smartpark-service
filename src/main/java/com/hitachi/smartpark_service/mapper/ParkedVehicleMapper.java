package com.hitachi.smartpark_service.mapper;

import java.util.Date;

import com.hitachi.smartpark_service.model.ParkedVehicle;
import com.hitachi.smartpark_service.model.ParkingLot;
import com.hitachi.smartpark_service.model.Vehicle;

public class ParkedVehicleMapper {

    private ParkedVehicleMapper() {
    }

    public static ParkedVehicle mapToParkedVehicle(ParkingLot parkingLot, Vehicle vehicle, boolean isParked) {
        if (parkingLot == null || vehicle == null) {
            throw new IllegalArgumentException("ParkingLot and Vehicle must not be null");
        }

        ParkedVehicle parkedVehicle = new ParkedVehicle();
        parkedVehicle.setLicensePlate(vehicle.getLicensePlate());
        parkedVehicle.setParkingLotId(parkingLot.getLotId());
        parkedVehicle.setParked(isParked); 

        return parkedVehicle;
    }
}

